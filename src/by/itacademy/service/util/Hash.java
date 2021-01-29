package by.itacademy.service.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class Hash {
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    public static final int SALT_BYTES = 8;
    public static final int HASH_BYTES = 8;
    public static final int PBKDF2_ITERATIONS = 1000;

    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 0;
    public static final int PBKDF2_INDEX = 1;

    public static String createHash(String password)  {
        String hash = "";
        try {
            hash = createHash(password.toCharArray());
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String createHash(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTES);
        // format iterations:salt:hash
        return toHex(salt) + ":" +  toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    public static boolean validatePassword(String password, String goodHash){
        try {
            return validatePassword(password.toCharArray(), goodHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validatePassword(char[] password, String goodHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Decode the hash into its parameters
        String[] params = goodHash.split(":");
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        // Compute the hash of the provided password, using the same salt,
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt, PBKDF2_ITERATIONS, hash.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }

    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

}
