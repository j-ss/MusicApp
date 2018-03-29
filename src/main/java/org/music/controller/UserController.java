package org.music.controller;

import org.music.dao.UserDao;
import org.music.entity.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/user/*")
public class UserController extends HttpServlet{
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String url=null;
    String pathInfo=req.getPathInfo();
    if(pathInfo.equals("/subscribeToEmail")){
      url=subscribeToEmail(req,resp);
    }
    req.getServletContext().getRequestDispatcher(url).forward(req,resp);

  }

  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws IOException, ServletException {

    String requestURI = request.getRequestURI();
    String url = "";
    if (requestURI.endsWith("/deleteCookies")) {
      url = deleteCookies(request, response);
    }
    getServletContext()
        .getRequestDispatcher(url)
        .forward(request, response);
  }

  private String subscribeToEmail(HttpServletRequest req, HttpServletResponse resp) {
    String message="";
    String url=null;
    String email=req.getParameter("email");
    String firstName=req.getParameter("firstName").trim();
    String lastName=req.getParameter("lastName").trim();
    if(firstName.equals("")||lastName.equals("")){
      url="/email/index.jsp";
    }
    User user=new User();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    if(UserDao.emailExists(email)){
      message="This email already exists";
      req.setAttribute("message",message);
    }else{
      UserDao.save(user);
      url="/email/thanks.jsp";
      req.setAttribute("message",message);
    }
    req.setAttribute("user",user);
    return url;
  }


  private String deleteCookies(HttpServletRequest request,
                               HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      cookie.setMaxAge(0);  //delete the cookie
      cookie.setPath("/");  //entire application
      response.addCookie(cookie);
    }
    return "/delete_cookies.jsp";
  }
}
