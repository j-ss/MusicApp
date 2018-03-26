package org.music.entity;

import java.util.List;
import javax.sound.sampled.Line;

public class Cart {

  private List<LineItem> items;

  public Cart() {
  }

  public List<LineItem> getItems() {
    return items;
  }

  public void setItems(List<LineItem> items) {
    this.items = items;
  }

  public void addItem(LineItem item){

  }

  public void removeItem(LineItem item){

  }
}
