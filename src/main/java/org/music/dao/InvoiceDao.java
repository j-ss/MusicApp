package org.music.dao;

import org.music.entity.Invoice;
import org.music.entity.LineItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class InvoiceDao {
  private static ConnectionPool pool=ConnectionPool.getInstance();
  public static void save(Invoice invoice){
    Connection connection=pool.getConnection();
    PreparedStatement statement=null;
    try {
      statement=connection.prepareStatement("INSERT INTO Invoice (UserID, InvoiceDate, TotalAmount, IsProcessed) "
          + "VALUES (?, NOW(), ?, 'n')");
      statement.setLong(1,invoice.getUser().getUserId());
      statement.setDouble(2,invoice.getInvoiceTotal());
      statement.executeUpdate();

      //Get the InvoiceID from the last INSERT statement.
      String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
      Statement identityStatement = connection.createStatement();
      ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
      identityResultSet.next();
      long invoiceID = identityResultSet.getLong("IDENTITY");
      identityResultSet.close();
      identityStatement.close();

      List<LineItem> lineItemList=invoice.getLineitems();
      for(LineItem item:lineItemList) {
        LineItemDao.insert(invoiceID,item);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }finally{
      try {
        statement.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

}
