package app.controllers;

import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.List;

public class OrderController {

    //TODO skal hentes fra order_details tabellen

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.post("/add_order", ctx -> addOrder(ctx, connectionPool));

    }

    private static void addOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
    //skal tilføje en ordre
        // vi skal have bund, topping og amount som parametre
            //skal proppes i en form for liste så de gemmes
            // toppings = CupcakeMapper.getAllToppings(connectionPool);

            //TODO lav HTML FORM
                //Der gemmer dropdown svar
                //Når der trykkes på knap (tilføj kurv)
                //Så skal der sendes en post, der rendere til en action som peger på denne function addOrder.
            String bottom = ctx.formParam("bottom");
            String topping = ctx.formParam("topping");
            String amount = ctx.formParam("amount");

            if (bottom == null || topping == null || amount == null) {
                ctx.status(400).result("Missing required fields.");
                return;
            }



    }

    private static void updateOrder(){

    }

    private static void editOrder(){

    }

    private static void delete(){

    }

    private static void order(){

    }

}
