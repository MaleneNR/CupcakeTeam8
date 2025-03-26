package app;

import app.config.ThymeleafConfig;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.postgresql.jdbc2.optional.ConnectionPool;

public class Main {



        private static final String USER = "postgres";
        private static final String PASSWORD = "postgres";
        private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
        private static final String DB = "Cupcake";

    //private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);




    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        app.get("/", ctx ->  ctx.render("index.html"));

        //TODO ændre html til homepage test
       

    }


}
