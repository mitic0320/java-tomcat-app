package com.mycompany.service;

import java.util.ArrayList;
import java.util.List;

public class UserService {
  private MailService mailService;

  public void setMailService(MailService mailService) {
    this.mailService = mailService;
  }

  private List<User> users = new ArrayList<>(List.of(
      new User(1, "Bob", "bob@163.com", "123"),
      new User(2, "Alice", "alice@outlook.com", "19"),
      new User(3, "Tom", "tom@qq.com", "28"),
      new User(4, "John", "john@gmail.com", "39")));

  public User login(String email, String password) {
    for (User user : users) {
      if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
        mailService.sendLoginMail(user);
        return user;
      }
    }
    throw new RuntimeException("login failed.");
  }

  public User getUser(long id) {
    return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
  }

  public User register(String email, String password, String name) {
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

}
