package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
          List<Order> orderList = new ArrayList<>();
            String sql = "select * from orders";

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
                    orderList.add(new Order(id,getAllCupcakesPerOrder(id, connectionPool),email,date));
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Fejl!!!!", e.getMessage());
            }
            return orderList;
        } //TODO: Der skal hentes alle ordre ind, og for hver ordre vil vi gerne kunne se cupcakes, som nok skal hentes i order_details-tabellen.

    public static List<Cupcake> getAllCupcakesPerOrder(int orderId, ConnectionPool connectionPool) throws DatabaseException{
        List<Cupcake> orderDetails = new ArrayList<>();
        String sql = "select * from order_details where order_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                int toppingId = rs.getInt("topping_id");
                int bottomId = rs.getInt("bottom_id");
                int price = rs.getInt("total_price");
                int amount = rs.getInt("quantity");

                Topping topping = CupcakeMapper.getToppingById(toppingId,connectionPool);
                Bottom bottom = CupcakeMapper.getBottomById(bottomId,connectionPool);

                Cupcake cupcake = new Cupcake(topping,bottom,price,amount);
                orderDetails.add(cupcake);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl!!!!", e.getMessage());
        }
        return orderDetails;
    }

    public static Boolean addOrder(Basket basket, ConnectionPool connectionPool) throws DatabaseException {
        int rowsAffected = 0;
        Boolean orderAdded = false;
        Order order = null;

        List<Cupcake> cupcakesInOrder = basket.getBasket();
        String customerEmail = basket.getUserEmail();
        LocalDate dateOfToday = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());

        String sql = "INSERT INTO orders (user_email, date) values (?,?) RETURNING order_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, customerEmail);
            //Dags dato i (YYYY-MM-DD)-format
            ps.setDate(2, Date.valueOf(dateOfToday));

            rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int orderId = rs.getInt("order_id");
                        //Basket konverteres til en ordre:)
                        order = new Order(orderId, cupcakesInOrder, customerEmail, dateOfToday);
                        orderAdded = true;
                    }
                }
            } else {
                throw new DatabaseException("Fejl ved indsætning af en ordre");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved indsætning af en ordre", e.getMessage());
        }

        boolean orderDetailsAdded = false;
        //Hvis ordren er tilføjet til order-tabellen, så tilføjer vi nu også orderdetajlerne til db
        if (orderAdded == true) {
            orderDetailsAdded = addOrderDetails(order, connectionPool);
        }

        if (orderDetailsAdded == false) {
            //Hvis ikke at orderdetails blev opdateret (returnerer false), så sletter vi orderen fra order-tabellen igen
            boolean delected = delete(order.getOrderId(), connectionPool);


            if (delected == true) {
                //Hvis den er slettet fra order-tabellen vil delected være true, og derfor sætter vi orderAdded tilbage til false, da den er slettet i db
                orderAdded = false;
            }
        }
        return orderAdded;
    }


    private static Boolean addOrderDetails(Order order,ConnectionPool connectionPool) throws DatabaseException{
        Boolean allOrderDetailsAdded = false;
        int rowsAffected = 0;
        List<Cupcake> cupcakes = order.getCupcakes();
        //For hver cupcake i listen fra parameteren basket inserter vi en row i vores database
        for(Cupcake c : cupcakes) {
            Topping topping = c.getTopping();
            Bottom bottom = c.getBottom();
            int quantity = c.getQuantity();
            int price = (bottom.getPrice() + topping.getPrice()) * quantity;

            String sql = "INSERT INTO order_details (order_id, topping_id, bottom_id, total_price, quantity) values (?,?,?,?,?)";

            try (
                    Connection connection = connectionPool.getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql)
            ) {
                ps.setInt(1, order.getOrderId());
                ps.setInt(2, topping.getToppingId());
                ps.setInt(3, bottom.getBottomId());
                ps.setInt(4, price);
                ps.setInt(5, quantity);
                rowsAffected += ps.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DatabaseException("Fejl i indsætning af en ordredetail");
                }
            } catch (SQLException e) {
                throw new DatabaseException("Fejl ved indsætning af en ordredetail", e.getMessage());
            }
        }
        //Tjekker om rowsaffected har samme værdi som cupcakes.size()
        if (rowsAffected == cupcakes.size()){
            allOrderDetailsAdded = true;
        }
        return allOrderDetailsAdded;
    }

    public static boolean delete(int orderId, ConnectionPool connectionPool) throws DatabaseException
    {
        boolean deleted = false;
        String sql = "delete from order where order_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1){
                deleted = true;
            }else
            {
                throw new DatabaseException("Fejl i opdatering af en ordre");
            }

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl ved sletning af en ordre", e.getMessage());
        }
        return deleted;
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
