package vip.floatationdevice.simplespringwebserver;

import java.io.Serializable;

/**
 * The object which stores a single user's info.
 */
public class User implements Serializable
{
    public static final long serialVersionUID = 1L;
    /**
     * The ID of the user.
     */
    public String userId;
    /**
     * The hash of the password.
     */
    public byte[] passHash;
    /**
     * The user's display name.
     */
    public String displayName;

    public User(String userId, byte[] passHash, String displayName)
    {
        this.userId = userId;
        this.passHash = passHash;
        this.displayName = displayName == null ? userId : displayName;
    }
}
