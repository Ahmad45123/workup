package com.workup.users.commands.utils;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class PasswordHasher {
  public static String hashPassword(String password) {
    return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
  }

  public static boolean checkPassword(String inputPassword, String hashedPassword) {
    return hashPassword(inputPassword).equals(hashedPassword);
  }
}
