package app.entities;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int orderId;
    private List<Cupcake> cupcakes;
    private String email;
    private LocalDate date;

    public Order(int orderId, List<Cupcake> cupcakes, String email, LocalDate date) {
        this.orderId = orderId;
        this.cupcakes = cupcakes;
        this.email = email;
        this.date = date;
    }

    public Order(List<Cupcake> cupcakes, String email, LocalDate date) {
        this.cupcakes = cupcakes;
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

    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }

    public void setCupcakes(List<Cupcake> cupcakes) {
        this.cupcakes = cupcakes;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", order=" + cupcakes +
                ", email='" + email + '\'' +
                ", date=" + date +
                '}';
    }
}
