package com.mycompany;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycompany.service.User;
import com.mycompany.service.UserService;

public class Main {
  public static void main(String[] args) throws Exception {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
    UserService userService = context.getBean(UserService.class);
    User user = userService.login("bob@163.com", "123");
    System.out.println(user.getName() + " login successfully!");
    context.close();
  }

  static void startServer() throws Exception {
    String contextPath = "/";
    String appBase = ".";
    Tomcat tomcat = new Tomcat();

    tomcat.setPort(8083);
    tomcat.setHostname("localhost");
    tomcat.getHost().setAppBase(appBase);
    tomcat.addWebapp(contextPath, appBase);

    Context ctx = tomcat.addContext("/api", new File(".").getAbsolutePath());
    ctx.addLifecycleListener(new ContextConfig());

    final WebResourceRoot root = new StandardRoot(ctx);
    final URL url = findClassLocation(Main.class);
    root.createWebResourceSet(WebResourceRoot.ResourceSetType.PRE,
        "/WEB-INF/classes", url, "/");
    ctx.setResources(root);

    tomcat.getConnector();
    tomcat.start();
    tomcat.getServer().await();
  }

  // Try to find the URL of the JAR or directory containing {@code clazz}
  private static URL findClassLocation(Class<?> clazz) {
    return clazz.getProtectionDomain().getCodeSource().getLocation();
  }
}
