package pt.jcarvalho.ssh;

import pt.jcarvalho.ssh.util.Pluggable;

/**
 * Authenticator mechanism. Implementations of this class will throw an
 * {@link UnsupportedOperationException} if the authenticator does not support
 * the selected method.
 */
@Pluggable
public interface Authenticator {

    public boolean authenticateByPassword(String username, String password) throws UnsupportedOperationException;

    public boolean authenticateByPublicKey(String username, byte[] publicKey) throws UnsupportedOperationException;

    public boolean authenticateByKerberos(String username, byte[] token) throws UnsupportedOperationException;

}
