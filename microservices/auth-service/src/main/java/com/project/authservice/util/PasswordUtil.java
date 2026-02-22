package com.project.authservice.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt.
 * Provides methods to securely hash passwords and check password matches.
 */
public class PasswordUtil {

    /**
     * Hashes a plain password using BCrypt.
     * The generated hash is stored in a way that it can be used for later verification.
     *
     * @param plainPassword The plain text password to be hashed.
     * @return The hashed password as a string.
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Checks if a plain password matches a previously hashed password.
     *
     * @param plainPassword The plain text password to be checked.
     * @param hashedPassword The hashed password to check against.
     * @return true if the plain password matches the hashed password, false otherwise.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
