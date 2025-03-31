package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.List;

public class OrderController {

    //TODO skal hentes fra order_details tabellen

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.post("/add_order", ctx -> addOrder(ctx, connectionPool));
        app.post("/order_here", ctx -> order(ctx, connectionPool));
        app.post("/deleteCupcake", ctx -> delete(ctx));
    }

    private static void addOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
            Bottom bottom = CupcakeMapper.getBottomById(Integer.parseInt(ctx.formParam("bottom")), connectionPool);
            Topping topping = CupcakeMapper.getToppingById(Integer.parseInt(ctx.formParam("topping")), connectionPool);
            int amount = Integer.parseInt(ctx.formParam("amount"));

            if (bottom == null || topping == null || amount == 0) { //TODO tjek om amount == 0 overhovedet hjælper.
                ctx.status(400).result("Missing required fields.");
                return;
            }

            Cupcake cupcake = new Cupcake(topping, bottom, bottom.getPrice()+ topping.getPrice(), amount);
            Basket currentBasket = ctx.sessionAttribute("currentBasket");
            currentBasket.getBasket().add(cupcake);
            ctx.sessionAttribute("currentBasket", currentBasket);

            index(ctx,connectionPool);

    }

    public static void index(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        //Toppings og bottoms sendes med igen, så man igen kan vælge
        List<Topping> toppingsList = CupcakeMapper.getAllToppings(connectionPool);//henter list fra db
        ctx.attribute("toppingsList", toppingsList);

        List<Bottom> bottomList = CupcakeMapper.getAllBottoms(connectionPool);
        ctx.attribute("bottomList", bottomList);


        //Går tilbage til index siden, efter der er tilføjet til kurv.
        ctx.render("index.html");
    }

    private static void delete(Context ctx){
        Basket currentBasket = ctx.sessionAttribute("currentBasket");
        currentBasket.getBasket().size();
        int cupcakeIndex = Integer.parseInt(ctx.formParam("cupcakeIndex"));

        if (currentBasket == null || currentBasket.getBasket().isEmpty()) {
            ctx.status(400).result("Kurven er tom eller eksisterer ikke.");
            return;
        }

        if (cupcakeIndex < 0 || cupcakeIndex >= currentBasket.getBasket().size()) {
            ctx.status(400).result("Ugyldigt cupcake-indeks.");
            return;
        }

        currentBasket.getBasket().remove(cupcakeIndex);
        ctx.sessionAttribute("currentBasket", currentBasket);
        ctx.render("basket.html");
    }



    private static void order(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        OrderMapper.addOrder(ctx.sessionAttribute("currentBasket"), connectionPool);
        Basket currentBasket = ctx.sessionAttribute("currentBasket");
        User currentUser = ctx.sessionAttribute("currentUser");
        for (Cupcake cupcake : currentBasket.getBasket()) {
            currentUser.setBalance(currentUser.getBalance() - (cupcake.getPrice() * cupcake.getQuantity()));
        }

        ctx.sessionAttribute("currentUser", currentUser); //Ved ikke om objectet skal opdateres i session igen, efter vi har opdateret dens balance attribute.

        currentBasket.getBasket().clear();                      //Fjerner alle items i kurven, efter der er blevet bestilt.
        ctx.sessionAttribute("currentBasket", currentBasket);   //Her opdateres currentBasket i ctx sessionen.
        ctx.render("index.html");                      //Går tilbage til index siden, så der kan bestilles igen.


    }

}
