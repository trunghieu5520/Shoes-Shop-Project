package dao;

import context.DBContext;
import entity.Cart;
import entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {

    Connection conn = null;

    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT  * FROM product";
        try {
            conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("brandId"),
                        rs.getInt("price"));
                list.add(p);
            }
            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public List<Product> getProductByBrand(String id) {
        List<Product> list = new ArrayList<>();
        String query = "select * from product where brandid like ?";
        try {
            conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("brandId"),
                        rs.getInt("price"));
                list.add(p);
            }
            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public Cart getCartByUserIDAndProductID(int userID, int productID) {
        Cart result = null;

        try {
            String query = "SELECT * FROM Cart WHERE userid = ? AND productid = ?";

            conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, productID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int amount = rs.getInt("amount");
                double total = rs.getDouble("totalprice");
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                int pid = rs.getInt("productid");
                Product product = getProduct(pid);
                String image = product.getImage();
                String name = product.getName();
                Cart cart = new Cart(id, userID, pid, price, amount, price, image, name);
                result = cart;
            }

        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void insertToCart(int userid, int productid, int amount, double price) {
        List<Product> list = new ArrayList<>();
        Cart cart = getCartByUserIDAndProductID(userid, productid);
        if (cart == null) {
            String query = "insert into cart(userid,productid, amount,price,totalprice) values(?,?,?,?,?)";
            try {
                conn = new DBContext().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, userid);
                ps.setInt(2, productid);
                ps.setInt(3, amount);
                ps.setDouble(4, price);
                ps.setDouble(5, amount * price);
                ps.executeUpdate();
            } catch (Exception e) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            String query = "UPDATE dbo.Cart SET amount = amount+1, totalprice = (amount+1)*price WHERE userid = ? AND productid=?";
            try {
                conn = new DBContext().getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, userid);
                ps.setInt(2, productid);
                ps.executeUpdate();
            } catch (Exception e) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void decreaseProductCart(int userID, int productID) {
        Cart cart = getCartByUserIDAndProductID(userID, productID);
        if (cart != null) {
            if (cart.getAmount() > 1) {
                String query = "UPDATE dbo.Cart SET amount = amount-1, totalprice = (amount-1)*price WHERE userid = ? AND productid=?";
                try {
                    conn = new DBContext().getConnection();
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, userID);
                    ps.setInt(2, productID);
                    ps.executeUpdate();
                } catch (Exception e) {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                String query = "DELETE FROM Cart WHERE userid = ? AND productid=?";
                try {
                    conn = new DBContext().getConnection();
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, userID);
                    ps.setInt(2, productID);
                    ps.executeUpdate();
                } catch (Exception e) {
                    Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    public void removeProductFromCartByUserIDAndProductID(int userID, int productID) {
        String query = "DELETE FROM Cart WHERE userid = ? AND productid=?";
        try {
            conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, productID);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<Cart> getListCartByUserID(int userID) {
        List<Cart> list = new ArrayList<>();

        try {
            String query = "SELECT * FROM dbo.Cart WHERE userid = ?";

            conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int amount = rs.getInt("amount");
                double total = rs.getDouble("totalprice");
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                int pid = rs.getInt("productid");
                Product product = getProduct(pid);
                String image = product.getImage();
                String name = product.getName();
                Cart cart = new Cart(id, userID, pid, price, amount, total, image, name);
                list.add(cart);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public Product getProduct(int productID) {
        String query = "select * from product where id = ?";
        List<Product> list = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String title = rs.getString(2);
                String des = rs.getString(3);
                int brandID = rs.getInt(4);
                int id = rs.getInt(5);
                String image = rs.getString(6);
                double price = rs.getDouble(7);
                return new Product(id, name, title, des, image, brandID, price);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
