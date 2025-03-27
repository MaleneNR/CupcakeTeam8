package app.persistence;

import app.entities.Bottom;
import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CupcakeMapper {
    public static List<Topping> getAllToppings(ConnectionPool connectionPool) throws DatabaseException {
        List<Topping> toppingList = new ArrayList<>();
        String sql = "select * from toppings";
        try (
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
        )
        {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                int toppingId = rs.getInt("topping_id");
                int price = rs.getInt("price");
                String topping = rs.getString("topping");
                toppingList.add(new Topping(toppingId,topping,price));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ifbm. at hente toppings fra db!", e.getMessage());
        }
        return toppingList;
    }

    public static List<Bottom> getAllBottoms(ConnectionPool connectionPool) throws DatabaseException {
        List<Bottom> bottomList = new ArrayList<>();
        String sql = "select * from bottoms";
        try (
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
        ) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int bottomId = rs.getInt("bottom_id");
                String bottom = rs.getString("bottom");
                int price = rs.getInt("price");
                bottomList.add(new Bottom(bottomId, bottom, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ifbm. at hente bottoms fra db!", e.getMessage());
        }
            return bottomList;
    }

    public static Topping getToppingById(int toppingId, ConnectionPool connectionPool) throws DatabaseException{
        Topping topping = null;
        String sql = "select * from toppings where topping_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, toppingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int topping_id = rs.getInt("topping_id");
                String toppingName = rs.getString("topping");
                int price = rs.getInt("price");
                topping = new Topping(topping_id, toppingName, price);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ved hentning af task med id = " + toppingId, e.getMessage());
        }
        return topping;

    }

    public static Bottom getBottomById(int bottomId, ConnectionPool connectionPool) throws DatabaseException{
        Bottom bottom = null;
        String sql = "select * from bottoms where bottom_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, bottomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int bottom_id = rs.getInt("bottom_id");
                String bottomName = rs.getString("bottom");
                int price = rs.getInt("price");
                bottom = new Bottom(bottomId, bottomName, price);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ved hentning af task med id = " + bottomId, e.getMessage());
        }
        return bottom;
    }
}
