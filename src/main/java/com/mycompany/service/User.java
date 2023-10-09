package com.mycompany.service;

public class User {
  private String name;
  private String email;
  private String password;
  private long id;

  public User(long id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public long getId() {
    return id;
  }
}
