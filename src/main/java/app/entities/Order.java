package app.entities;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int orderId;
    private List<Cupcake> order;
    private String email;
    private LocalDate date;

    public Order(int orderId, List<Cupcake> order, String email, LocalDate date) {
        this.orderId = orderId;
        this.order = order;
        this.email = email;
        this.date = date;
    }

    public Order(List<Cupcake> order, String email, LocalDate date) {
        this.order = order;
        this.email = email;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
                "orderId=" + orderId +
                ", order=" + order +
                ", email='" + email + '\'' +
                ", date=" + date +
                '}';
    }
}
