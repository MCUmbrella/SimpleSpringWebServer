package vip.floatationdevice.simplespringwebserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the user data.
 */
public class UserManager
{
    private final static Logger l = LoggerFactory.getLogger(UserManager.class);
    private final static ConcurrentHashMap<String, User> userList = new ConcurrentHashMap<>();
    private static final String CPUID = new SystemInfo().getHardware().getProcessor().getProcessorIdentifier().getProcessorID();
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    private static class UserListContainer implements Serializable
    {
        public static final long serialVersionUID = 1L;
        public ConcurrentHashMap<String, User> userList;
    }

    private static byte[] hex2bytes(String s)
    {
        s = s.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray())
            for(char cc : HEX_CHARS)
                if(c == cc) sb.append(c);
        String ss = sb.toString();
        int l = ss.length();
        byte[] b = new byte[l / 2];
        for(int i = 0; i < l; i += 2)
        {
            b[i / 2] = (byte) ((Character.digit(ss.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return b;
    }


    private static String bytes2Hex(byte[] bs)
    {
        StringBuilder sb = new StringBuilder();
        for(byte b : bs)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private static byte[] hashPassword(String password)
    {
        byte[] hash;
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(hex2bytes(CPUID));
            hash = md.digest(password.getBytes());
        }
        catch(NoSuchAlgorithmException e)
        {
            l.error("SHA-512 is not supported, password will be stored in plain text");
            hash = password.getBytes();
        }
        return hash;
    }

    /**
     * Check if a user with the specified ID is in the database.
     * @param userId The ID of the user.
     * @return True if the user with this ID is in the database, false otherwise.
     */
    public static boolean hasUser(String userId)
    {
        for(String s : userList.keySet())
            if(s.equals(userId)) return true;
        return false;
    }

    /**
     * Create new user or update an existing user's password.
     * @return true if updated existing user, false if created new user.
     */
    public static boolean putUser(String userId, String password, String displayName)
    {
        boolean userExists = hasUser(userId);
        userList.put(userId, new User(userId, hashPassword(password), displayName));
        return userExists;
    }

    /**
     * Verify a login info.
     * @param userId The user's ID.
     * @param password The user's password.
     * @return True if the login info is in the database, false otherwise.
     */
    public static boolean verify(String userId, String password)
    {
        return userId != null && password != null &&
                hasUser(userId) && Arrays.equals(userList.get(userId).passHash, hashPassword(password));
    }

    /**
     * Get a user's display name.
     * @param userId The ID of the user.
     * @throws IllegalArgumentException if the user with the specified ID is not found.
     */
    public static String getDisplayName(String userId)
    {
        if(hasUser(userId)) return userList.get(userId).displayName;
        else throw new IllegalArgumentException("The user with ID " + userId + " is not found in the database");
    }

    /**
     * Set a user's display name.
     * @param userId The ID of the user.
     * @param displayName The user's new display name.
     * @return The new display name of the user.
     * @throws IllegalArgumentException if the user with the specified ID is not found.
     */
    public static String setDisplayName(String userId, String displayName)
    {
        if(hasUser(userId))
        {
            userList.get(userId).displayName = displayName;
            return userList.get(userId).displayName;
        }
        else throw new IllegalArgumentException("The user with ID " + userId + " is not found in the database");
    }

    /**
     * Save user data to the hard disk.
     * @return True if success, false otherwise.
     */
    public static boolean save()
    {
        l.info("Saving user database");
        try
        {
            UserListContainer ulc = new UserListContainer();
            ulc.userList = userList;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"));
            oos.writeObject(ulc);
            oos.flush();
            oos.close();
            l.info("Saving user database: SUCCESS - " + userList.size() + " user info saved");
            return true;
        }
        catch(Exception e)
        {
            l.error("Saving user database: FAIL - " + e);
            return false;
        }
    }

    /**
     * Load user data from the hard disk.
     * @return True if success, false otherwise.
     */
    public static boolean load()
    {
        l.info("Loading user database");
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"));
            UserListContainer ulc = (UserListContainer) ois.readObject();
            ois.close();
            userList.clear();
            userList.putAll(ulc.userList);
            l.info("Loading user database: SUCCESS - " + userList.size() + " user info loaded");
            return true;
        }
        catch(Exception e)
        {
            l.error("Loading user database: FAIL - " + e);
            try
            {
                File usersDat = new File("users.dat");
                if(usersDat.exists() && usersDat.isFile())
                    if(usersDat.renameTo(new File("users.dat.OLD")))
                        l.error("Renamed problematic data file to 'users.dat.OLD'");
            }
            catch(Exception ee) {}
            return false;
        }
    }
}
