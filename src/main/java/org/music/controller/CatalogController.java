package org.music.controller;
/**
 *
 */

import org.music.dao.ProductDao;
import org.music.dao.UserDao;
import org.music.entity.Download;
import org.music.entity.Product;
import org.music.entity.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/catalog/product/*",loadOnStartup = 1)
public class CatalogController extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

    String requestURI = request.getRequestURI();
    String url;
    if(requestURI.endsWith("/listen")){
      url=listen(request,response);
    }else {
      url = showProduct(request, response);
    }
    getServletContext()
        .getRequestDispatcher(url)
        .forward(request, response);
  }

  public String showProduct(HttpServletRequest request,HttpServletResponse response){
    String productCode=request.getPathInfo();
    if(productCode!=null){
      productCode=productCode.substring(1);
      Product product= ProductDao.getProduct(productCode);
      HttpSession session=request.getSession();
      session.setAttribute("product",product);

    }
    return "/catalog/"+productCode+"/index.jsp";
  }

  public String listen(HttpServletRequest request,HttpServletResponse response){

    HttpSession session=request.getSession();
    Product p=(Product)session.getAttribute("product");
    User user=(User)session.getAttribute("user");

    if(user==null){

      Cookie[] cookies=request.getCookies();
      for(Cookie cookie:cookies){

        if(cookie.getName().equals("email")){
          user= UserDao.getUser(cookie.getValue());

          if(user==null){
            return "/catalog/register.jsp";
          }else {
            session.setAttribute("user", user);
            return "/catalog/" + p.getCode() + "/sound.jsp";
          }

        }else{
          return "/catalog/register.jsp";
        }
      }
    }
      return "/catalog/" + p.getCode() + "/sound.jsp";

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String requestURI=req.getRequestURI();
    String url="/catalog";
    if(requestURI.endsWith("/register")){
      url=registerUser(req,resp);
    }
    req.getRequestDispatcher(url).forward(req,resp);
  }

  public String registerUser(HttpServletRequest request,HttpServletResponse response){
    String email=request.getParameter("email");
    String firstName=request.getParameter("firstName");
    String lastName=request.getParameter("lastName");
    User user=null;
    if(UserDao.emailExists(email)){
      user=UserDao.getUser(email);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setEmail(email);
      UserDao.update(user);
    }else{
      user=new User();
      user.setEmail(email);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      UserDao.save(user);
    }
    HttpSession session=request.getSession();
    session.setAttribute("user",user);
    session.setMaxInactiveInterval(60*60*24);
    Cookie cookie=new Cookie("email",email);
    cookie.setPath("/");
    cookie.setMaxAge(60*60*24);
    response.addCookie(cookie);
    Product product=(Product) session.getAttribute("product");
    return "/catalog/"+product.getCode()+"/sound.jsp";
  }
}