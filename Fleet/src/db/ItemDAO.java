package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Item;

public class ItemDAO {

    public void addItem(String name, double price) {
        String sql = "INSERT INTO items(name, price) VALUES (?, ?)";
        try (PreparedStatement ps =
                     DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Item> searchItems(String keyword) {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE name LIKE ?";
        try (PreparedStatement ps =
                     DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Item> getAll() {
        return searchItems("");
    }

    public void updateItem(int id, String name, double price) {
        String sql = "UPDATE items SET name = ?, price = ? WHERE id = ?";
        try (PreparedStatement ps =
                     DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM items WHERE id = ?";
        try (PreparedStatement ps =
                     DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
