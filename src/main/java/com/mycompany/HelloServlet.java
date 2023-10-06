package com.mycompany;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html");
    String name = req.getParameter("name");
    if (name == null) {
      name = "world";
    }
    PrintWriter pw = res.getWriter();
    pw.write("<H1> Hello, " + name + "333! </H1>");
    pw.flush();
  }
}
