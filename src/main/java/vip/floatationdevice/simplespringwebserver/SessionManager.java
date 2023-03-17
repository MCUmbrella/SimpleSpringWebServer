package vip.floatationdevice.simplespringwebserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager
{
    private final static Logger l = LoggerFactory.getLogger(SessionManager.class);
    private final static ConcurrentHashMap<String, String> sessionList = new ConcurrentHashMap<>();
    private final static SecureRandom r = new SecureRandom();

    private static class SessionListContainer implements Serializable
    {
        public static final long serialVersionUID = 1L;
        public ConcurrentHashMap<String, String> sessionList;
    }

    public static String generateSession(String userId)
    {
        byte[] b = new byte[64];
        r.nextBytes(b);
        String sessionId = bytes2Hex(b);
        sessionList.put(userId, sessionId);
        l.info("Create session for " + userId + ", id=" + sessionId);
        return sessionId;
    }

    public static boolean destroySession(String userId)
    {
        boolean sessionExists = sessionList.remove(userId) != null;
        if(sessionExists)
            l.info("Removed session for " + userId);
        return sessionExists;
    }

    public static boolean hasSession(String userId, String sessionId)
    {
        return sessionList.containsKey(userId) && sessionList.get(userId).equals(sessionId);
    }

    private static String bytes2Hex(byte[] bs)
    {
        StringBuilder sb = new StringBuilder();
        for(byte b : bs)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
