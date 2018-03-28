package org.music.dao;

import org.music.entity.LineItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LineItemDao {
  //This method adds one lineItem to the LineItems table.
  public static long insert(long invoiceID, LineItem lineItem) {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;

    String query = "INSERT INTO LineItem(InvoiceID, ProductID, Quantity) "
        + "VALUES (?, ?, ?)";
    try {
      ps = connection.prepareStatement(query);
      ps.setLong(1, invoiceID);
      ps.setLong(2, lineItem.getProduct().getProductId());
      ps.setInt(3, lineItem.getQuantity());
      return ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println(e);
      return 0;
    } finally {
      try {
        connection.close();
        ps.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

}
