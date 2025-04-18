package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.OrderMapper;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;



public class UserController{

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
            app.post("/login", ctx -> login(ctx, connectionPool));
            app.get("/loginPage", ctx -> ctx.render("login.html"));
            app.get("/logout", ctx -> logout(ctx));
            app.get("/createuser", ctx -> ctx.render("createuser.html"));
            app.post("/createuser", ctx -> createUser(ctx, connectionPool));
            app.post("/showBasket", ctx -> ctx.render("basket.html"));
            app.get("/loginorcreateuser", ctx -> ctx.render("loginOrCreateUser.html"));

    }

    private static void createUser(Context ctx, ConnectionPool connectionPool){
        String username = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");



        //Validerer password
        if(password1.equals(password2)){
            try{
                UserMapper.createUser(username,password1,connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: "+ username+ ". Du skal nu logge på");
                ctx.render("login.html");}
            catch (DatabaseException e) {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createuser.html");
        }
    }

    private static void login(Context ctx, ConnectionPool connectionPool){
        String username= ctx.formParam("email");
        String password = ctx.formParam("password");
        try {
            User user = UserMapper.login(username, password, connectionPool);
            //Når user bliver oprettet, bliver der også oprettet en basket.

            List<Topping> toppingsList = CupcakeMapper.getAllToppings(connectionPool);//henter dropdown list via db
            ctx.attribute("toppingsList", toppingsList);

            List<Bottom> bottomList = CupcakeMapper.getAllBottoms(connectionPool);
            ctx.attribute("bottomList", bottomList);

            ctx.sessionAttribute("currentUser", user);

            List<Cupcake> cupcakes = new ArrayList<>();
            Basket basket = new Basket(cupcakes, username);
            ctx.sessionAttribute("currentBasket", basket);

            if(user.getRole() == 1){
                loginAdmin(ctx,connectionPool);
            } else{

                ctx.render("index.html");
            }

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }

    }

    private static void logout(Context ctx){
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }


    private static void loginAdmin(Context ctx, ConnectionPool connectionPool){
        try {
            List<Order> orderList = OrderMapper.getAllOrders(connectionPool);
            ctx.attribute("orderList", orderList);
            ctx.render("admin.html");

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }

    }

}
