package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {


    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
          /*  List<Order> orderList = new ArrayList<>();
            String sql = "select * from orders order by date";

            try (
                    Connection connection = connectionPool.getConnection();
                    Statement statement = connection.createStatement();

            )
            {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next())
                {
                    int id = rs.getInt("task_id");
                    String name = rs.getString("task_title");
                    Boolean done = rs.getBoolean("done");
                    orderList.add(new Order(id, name, done, email));
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl!!!!", e.getMessage());
            }
            return orderList;*/
        return null;
        } //TODO: Der skal hentes alle ordre ind, og for hver ordre vil vi gerne kunne se cupcakes, som nok skal hentes i order_details-tabellen.


    public static Order addOrder(){
        return null;
    }

    public static void delete(int orderId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "delete from order where order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i opdatering af en ordre");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ved sletning af en ordre", e.getMessage());
        }
    }

    public static Order getOrderById(){
        return null;
        //TODO: Skal nok både hente fra orders og fra order_details for at man har alle oplysning på ordren
    }

    //Update-side kan laves, hvis tid
    public static void update(int orderId, int amount, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "update order_details set quantity = ? where order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, amount);
            ps.setInt(2, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i opdatering af en task");
            }
        }
        catch (SQLException | DatabaseException e)
        {
            throw new DatabaseException("Fejl i opdatering af en task", e.getMessage());
        }
    }

}
