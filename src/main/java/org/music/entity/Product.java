package org.music.entity;

import java.text.NumberFormat;

public class Product {

  private String code;
  private String description;
  private double price;

  public Product() {
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  //This method return currencyformat in your loacl currency format
  public String getPriceCurrencyFormat(){
    NumberFormat numberFormat=NumberFormat.getCurrencyInstance();
    return numberFormat.format(getPrice());
  }

  //This method return artist name
  public String getArtistName(){
    return description.substring(0,description.indexOf('-'));
  }

  //This method return albumn name
  public String getAlbumName(){
    return description.substring(0,description.indexOf('-')+3);
  }

  //This method return image url
  public String getImageURL(){
    return "/images/"+code+"_cover.jpg";
  }

  //This method return product type
  public String getProductType(){
    return "Audio";
  }
}
