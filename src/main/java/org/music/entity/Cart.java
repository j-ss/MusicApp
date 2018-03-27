package org.music.entity;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.Line;

public class Cart {

  private List<LineItem> items;

  public Cart() {
    items=new ArrayList<>();
  }

  public List<LineItem> getItems() {
    return items;
  }

  public int getCount() {
    return items.size();
  }

  public void addItem(LineItem item){

    String productCode=item.getProduct().getCode();
    int quantity=item.getQuantity();
    for(LineItem lineItem:items) {
      if (productCode.equals(lineItem.getProduct().getCode())){
        lineItem.setQuantity(lineItem.getQuantity()+quantity);
        return;
      }
    }
    items.add(item);
  }

  public void removeItem(LineItem item){

    String productCode=item.getProduct().getCode();
    for(LineItem lineItem:items) {
      if (productCode.equals(lineItem.getProduct().getCode())){
        items.remove(lineItem);
        return;
      }
    }
  }
}
