package org.music.controller;

import org.music.dao.InvoiceDao;
import org.music.dao.ProductDao;
import org.music.dao.UserDao;
import org.music.entity.Cart;
import org.music.entity.Invoice;
import org.music.entity.LineItem;
import org.music.entity.Product;
import org.music.entity.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/order/*")
public class OrderController extends HttpServlet {

  private static final String defaultURL = "/cart/cart.jsp";

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String action=req.getPathInfo();
    System.out.println(action);
    String url="";
    if(action.equals("/addItem")){
      url=addItem(req,resp);
    }else if(action.equals("/update")){
      url=update(req,resp);
    }else if(action.equals("/remove")){
      url=remove(req,resp);
    }else if(action.equals("/checkUser")){
      url=checkUser(req,resp);
    }else if(action.equals("/displayInvoice")){
      url=displayInvoice(req,resp);
    }else if(action.equals("/processUser")){
      url=processUser(req,resp);
    }else if(action.equals("/editUser")){
      url="/cart/user.jsp";
    }else if(action.equals("/displayCreditCard")){
      url="/cart/creditCard.jsp";
    }else if(action.equals("/completeOrder")){
      url=completeOrder(req,resp);
    }
    req.getRequestDispatcher(url).forward(req,resp);
  }

  public String addItem(HttpServletRequest request,HttpServletResponse response){
    String productCode=request.getParameter("productCode");
    HttpSession session=request.getSession();
    Cart cart=(Cart)session.getAttribute("cart");
    if(cart==null){
      cart=new Cart();
    }
    Product product= ProductDao.getProduct(productCode);
    LineItem lineItem=new LineItem();
    lineItem.setProduct(product);
    lineItem.setQuantity(1);
    cart.addItem(lineItem);

    session.setAttribute("cart",cart);
    return defaultURL;
  }

  public String remove(HttpServletRequest request,HttpServletResponse response){

    HttpSession session=request.getSession();
    Cart cart = (Cart) session.getAttribute("cart");
    String productCode = request.getParameter("productCode");
    Product product = ProductDao.getProduct(productCode);
    if (product != null && cart != null) {
      LineItem lineItem = new LineItem();
      lineItem.setProduct(product);
      cart.removeItem(lineItem);
    }
    return defaultURL;
  }

  public String update(HttpServletRequest request,HttpServletResponse response){
    String productCode=request.getParameter("productCode");
    String quantityString=request.getParameter("quantity");
    HttpSession session=request.getSession();
    Cart cart=(Cart)session.getAttribute("cart");
    int quantity;
    try {
      quantity = Integer.parseInt(quantityString);
      if (quantity < 0)
        quantity = 1;
    } catch (NumberFormatException ex) {
      quantity = 1;
    }

    List<LineItem> lineItemList=cart.getItems();
    for(LineItem lineItem:lineItemList){
      if(lineItem.getProduct().getCode().equals(productCode)){
        if(quantity>0) {
          lineItem.setQuantity(quantity);
          return defaultURL;
        }else{
          cart.removeItem(lineItem);
          return defaultURL;
        }
      }
    }
    return defaultURL;
  }

  public String checkUser(HttpServletRequest request,HttpServletResponse response){

    HttpSession session=request.getSession();
    String url="/cart/user.jsp";
    User user=(User)session.getAttribute("user");
    if(user==null){

      Cookie[] cookies=request.getCookies();
      String email=null;
      for(Cookie cookie:cookies){
        if(cookie.getName().equals("email")){
          email=cookie.getValue();
          break;
        }
      }
      if(email==null){
        user=new User();
      }else{
        System.out.println(email);
        user = UserDao.getUser(email);
        if(user==null) {
          user = new User();
          url = "/cart/user.jsp";
        }
        else {
          if (!user.getAddress1().equals("") || !(user.getAddress1() == null)) {

            url = "/order/displayInvoice";
          }
        }

      }
      session.setAttribute("user",user);
    }else{
      url="/order/displayInvoice";
    }

    return url;
  }

  public String processUser(HttpServletRequest request,HttpServletResponse response){
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String companyName = request.getParameter("companyName");
    String email = request.getParameter("email");
    String address1 = request.getParameter("address1");
    String address2 = request.getParameter("address2");
    String city = request.getParameter("city");
    String state = request.getParameter("state");
    String zip = request.getParameter("zip");
    String country = request.getParameter("country");

    HttpSession session=request.getSession();
    User user=(User)session.getAttribute("user");
    System.out.println(user);
    if(UserDao.emailExists(email)){
      System.out.println("exixt "+email);
      user=UserDao.getUser(email);
      System.out.println(user.getEmail());
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setEmail(email);
      user.setCompanyName(companyName);
      user.setAddress1(address1);
      user.setAddress2(address2);
      user.setCity(city);
      user.setState(state);
      user.setZip(zip);
      user.setCountry(country);
      UserDao.update(user);
    }else{
      System.out.println("save");
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setEmail(email);
      user.setCompanyName(companyName);
      user.setAddress1(address1);
      user.setAddress2(address2);
      user.setCity(city);
      user.setState(state);
      user.setZip(zip);
      user.setCountry(country);
      UserDao.save(user);
    }

    session.setAttribute("user",user);
    return "/order/displayInvoice";
  }

  public String displayInvoice(HttpServletRequest request,HttpServletResponse response){

    HttpSession session=request.getSession();
    User user=(User)session.getAttribute("user");
    System.out.println(user);
    Cart cart=(Cart)session.getAttribute("cart");
    System.out.println(cart);
    //LocalDate date=LocalDate.now();
    Date date=new Date();
    Invoice invoice=new Invoice();
    invoice.setUser(user);
    invoice.setInvoiceDate(date);
    invoice.setLineitems(cart.getItems());
    session.setAttribute("invoice",invoice);
    return "/cart/invoice.jsp";
  }

  public String completeOrder(HttpServletRequest request,HttpServletResponse response){
    HttpSession session=request.getSession();
    Invoice invoice=(Invoice)session.getAttribute("invoice");
    User user=invoice.getUser();
    String creditCardType=request.getParameter("creditCardType");
    String cardNumber=request.getParameter("creditCardNumber");
    String creditCardExpirationMonth=request.getParameter("creditCardExpirationMonth");
    String creditCardExpirationYear=request.getParameter("creditCardExpirationYear");
    user.setCreditCardType(creditCardType);
    user.setCreditCardNumber(cardNumber);
    user.setCreditCardExpirationDate(creditCardExpirationMonth+"/"+creditCardExpirationYear);
    if (UserDao.emailExists(user.getEmail())) {
      UserDao.update(user);
    } else { // otherwise, write a new record for the user
      UserDao.save(user);
    }
    invoice.setUser(user);
    InvoiceDao.save(invoice);
    // set the emailCookie in the user's browser.
    Cookie cookie = new Cookie("email",
        user.getEmail());
    cookie.setMaxAge(60*24*365*2*60);
    cookie.setPath("/");
    response.addCookie(cookie);

    // remove all items from the user's cart
    session.setAttribute("cart", null);

    return "/cart/complete.jsp";
  }
}
