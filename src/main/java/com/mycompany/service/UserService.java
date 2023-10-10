package com.mycompany.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
  private MailService mailService;
  String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
  String JDBC_USER = "root";
  String JDBC_PASSWORD = "password";

  public void setMailService(MailService mailService) {
    this.mailService = mailService;
  }

  public User login(String email, String password) {
    ArrayList<User> users = queryAll();
    for (User user : users) {
      if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
        mailService.sendLoginMail(user);
        return user;
      }
    }
    throw new RuntimeException("login failed.");
  }

  public User getUser(long id) {
    ArrayList<User> users = queryAll();
    return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
  }

  public User register(String email, String password, String name) {
    ArrayList<User> users = queryAll();
    users.forEach((user) -> {
      if (user.getEmail().equals(email)) {
        throw new RuntimeException("email exist.");
      }
    });

    User user = new User(users.stream().mapToLong(u -> u.getId()).max().getAsLong() + 1, email, password, name);

    users.add(user);
    mailService.sendRegistrationMail(user);
    return user;
  }

  public ArrayList<User> queryAll() {
    ArrayList<User> users = new ArrayList<User>();
    try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
      try (PreparedStatement ps = con.prepareStatement("SELECT * FROM user")) {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            users.add(new User(id, name, email, password));
          }
          return users;
        }
      }
    } catch (SQLException e) {
      return new ArrayList<User>();
    }
  }

}
