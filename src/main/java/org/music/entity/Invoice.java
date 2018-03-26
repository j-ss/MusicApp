package org.music.entity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Invoice {

  private User user;
  private List<LineItem> lineitems;
  private Date invoiceDate;
  private int invoiceNumber;

  public Invoice() {
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<LineItem> getLineitems() {
    return lineitems;
  }

  public void setLineitems(List<LineItem> lineitems) {
    this.lineitems = lineitems;
  }

  public Date getInvoiceDateDefaultFormat() {
    SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    String date=format.format(invoiceDate);
    try {
      return format.parse(date);
    }catch (ParseException e){
      e.printStackTrace();
    }
    return invoiceDate;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public int getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(int invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  //This method retunr invoice total
  public double getInvoiceTotal(){
    double sum=0;
    for(LineItem item:lineitems){
      sum+=item.getTotal();
    }
    return sum;
  }

  //This method return invoice total in local currency format
  public String getInvoiceTotalCurrencyFormat(){
    NumberFormat numberFormat=NumberFormat.getCurrencyInstance();
    return numberFormat.format(getInvoiceTotal());
  }
}
