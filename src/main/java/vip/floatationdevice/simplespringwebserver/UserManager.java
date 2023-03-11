package vip.floatationdevice.simplespringwebserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the user data.
 */
public class UserManager
{
    private final static Logger l = LoggerFactory.getLogger(UserManager.class);
    private final static ConcurrentHashMap<String, User> userList = new ConcurrentHashMap<>();

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
        if(hasUser(userId))
        {
            userList.put(userId, new User(userId, password.hashCode(), displayName));
            return true;
        }
        userList.put(userId, new User(userId, password.hashCode(), displayName));
        return false;
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
                hasUser(userId) && userList.get(userId).passHash == password.hashCode();
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
            return false;
        }
    }

    private static class UserListContainer implements Serializable
    {
        public static final long serialVersionUID = Long.MAX_VALUE;
        public ConcurrentHashMap<String, User> userList;
    }
}
