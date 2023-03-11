package vip.floatationdevice.simplespringwebserver;

import java.io.Serializable;

/**
 * The object which stores a single user's info.
 */
public class User implements Serializable
{
    public static final long serialVersionUID = Long.MAX_VALUE;
    /**
     * The ID of the user.
     */
    public String userId;
    /**
     * The hash of the password.
     */
    public int passHash;
    /**
     * The user's display name.
     */
    public String displayName;

    public User(String userId, int passHash, String displayName)
    {
        this.userId = userId;
        this.passHash = passHash;
        this.displayName = displayName == null ? userId : displayName;
    }
}
