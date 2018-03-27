package org.music.dao;

import org.music.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDao {

  private static ConnectionPool pool=ConnectionPool.getInstance();

  public static Product getProduct(String code){
    Connection connection=pool.getConnection();
    PreparedStatement statement=null;
    ResultSet resultSet=null;
    try {
       statement=connection.prepareStatement("select * from Product where code=?");
       statement.setString(1,code);
       resultSet=statement.executeQuery();
      if (resultSet.next()) {
        Product p = new Product();
        p.setProductId(resultSet.getLong("productID"));
        p.setCode(resultSet.getString("code"));
        p.setDescription(resultSet.getString("description"));
        p.setPrice(resultSet.getDouble("price"));
        return p;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }finally {

      try {
        resultSet.close();
        statement.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static void save(Product product) {
    Connection connection = pool.getConnection();
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      statement = connection.prepareStatement("insert into Product(productId,code,description,price)"+"VALUE(?,?,?,?)");
      statement.setLong(1, product.getProductId());
      statement.setString(2,product.getCode());
      statement.setString(3,product.getDescription());
      statement.setDouble(4,product.getPrice());
      statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {

      try {
        statement.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
