package org.music.controller;
/**
 *
 */

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
    url=showProduct(request,response);
    getServletContext()
        .getRequestDispatcher(url)
        .forward(request, response);
  }

  public String showProduct(HttpServletRequest request,HttpServletResponse response){
    String productCode=request.getPathInfo();
    if(productCode!=null){
      productCode=productCode.substring(1);

    }
    return "/catalog/"+productCode+"/index.jsp";
  }

}