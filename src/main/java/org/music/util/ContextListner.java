package org.music.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListner implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext servletContext = sce.getServletContext();
    // get the absolute paths for swithing regular and secure connections
    String contextPath = servletContext.getContextPath();
    String absolutePath = "http://localhost:8080" + contextPath;
    String absolutePathSecure = "https://localhost:8443" + contextPath;
    servletContext.setAttribute("absolutePath", absolutePath);
    servletContext.setAttribute("absolutePathSecure", absolutePathSecure);

    // initialize the customer service email address that's used throughout the web site
    String custServEmail = servletContext.getInitParameter("serviceemail");
    servletContext.setAttribute("custServEmail", custServEmail);

    // initialize the current year that's used in the copyright notice
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
    servletContext.setAttribute("currentYear", currentYear);

    // initialize the array of years that's used for the credit card year
    ArrayList<String> years = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      int year = currentYear + i;
      String yearString = Integer.toString(year);
      years.add(yearString);
    }
    servletContext.setAttribute("years", years);

  }
}
