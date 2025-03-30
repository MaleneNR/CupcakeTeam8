package app.entities;


import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Cupcake> basket;
    private String userEmail;

    public Basket(List<Cupcake> basket, String userEmail) {
        this.basket = basket;
        this.userEmail = userEmail;
    }

    public List<Cupcake> getBasket() {
        return basket;
    }

    public void setBasket(List<Cupcake> basket) {
        this.basket = basket;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "basket=" + basket +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
