package app.controllers;

import app.entities.Basket;
import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AdminController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.post("/deleteOrder", ctx -> deleteOrder(ctx, connectionPool));
    }


    private static void deleteOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderid"));
        OrderMapper.deleteOrderDetailsAndOrder(orderId, connectionPool);
        List<Order> orderList = OrderMapper.getAllOrders(connectionPool);
        ctx.attribute("orderList", orderList);
        ctx.render("admin.html");
    }

    private static void sendEmail(){

    }

}
