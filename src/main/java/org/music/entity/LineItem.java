package org.music.entity;

import java.text.NumberFormat;

public class LineItem {

  private Product product;
  private int quantity;

  public LineItem() {
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getTotal(){
    return product.getPrice()*quantity;
  }

  public String getTotalCurrencyFormat(){
    NumberFormat numberFormat=NumberFormat.getCurrencyInstance();
    return numberFormat.format(getTotal());
  }
}
