package app.persistence;

import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.Order;
import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderMapper {


    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
          List<Order> orderList = new ArrayList<>();
            String sql = "select * from orders order by date";

            try (
                    Connection connection = connectionPool.getConnection();
                    Statement statement = connection.createStatement();
            )
            {
                ResultSet rsOrder = statement.executeQuery(sql);
                while (rsOrder.next())
                {
                    int id = rsOrder.getInt("order_id");
                    String email = rsOrder.getString("user_email");
                    LocalDate date = rsOrder.getDate("date").toLocalDate();
                    orderList.add(new Order(getAllCupcakesPerOrder(id, connectionPool),email,date));
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl!!!!", e.getMessage());
            }
            return orderList;
        } //TODO: Der skal hentes alle ordre ind, og for hver ordre vil vi gerne kunne se cupcakes, som nok skal hentes i order_details-tabellen.

    public static List<Cupcake> getAllCupcakesPerOrder(int orderId, ConnectionPool connectionPool) throws DatabaseException{
        List<Cupcake> orderDetails = null;
        String sql = "select * from order_details where order_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery(sql);
            while (rs.next())
            {
                int toppingId = rs.getInt("topping_id");
                int bottomId = rs.getInt("bottom_id");
                int price = rs.getInt("price");
                int amount = rs.getInt("quantity");

                Topping topping = CupcakeMapper.getToppingById(toppingId,connectionPool);
                Bottom bottom = CupcakeMapper.getBottomById(bottomId,connectionPool);

                orderDetails.add(new Cupcake(topping,bottom,price,amount));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl!!!!", e.getMessage());
        }
        return orderDetails;
    }


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

    public static Order getOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException{
            Order order = null;
            String sql = "select from order where order_id = ?";

            try (
                    Connection connection = connectionPool.getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql)
            )
            {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery(sql);
                if (rs.next())
                {
                    String email = rs.getString("user_email");
                    LocalDate date = rs.getDate("date").toLocalDate();
                   order = new Order(getAllCupcakesPerOrder(orderId,connectionPool), email,date);
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl i søgning på en ordre ved id" + orderId+ " i getOrderById()", e.getMessage());
            }
            return order;
        }


    //TODO: Update-side kan laves, hvis tid
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
