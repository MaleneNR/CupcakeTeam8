package app.entities;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private List<Cupcake> order;
    private String email;
    private LocalDate date;

    public Order(List<Cupcake> order, String email, LocalDate date) {
        this.order = order;
        this.email = email;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Cupcake> getOrder() {
        return order;
    }

    public void setOrder(List<Cupcake> order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order=" + order +
                '}';
    }
}
