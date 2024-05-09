package com.workup.users.commands.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean checkPassword(String inputPassword, String hashedPassword) {
    return BCrypt.checkpw(inputPassword, hashedPassword);
  }
}
