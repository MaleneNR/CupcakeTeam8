package app.entities;

import java.util.List;

public class Order {
    private List<Cupcake> Order;
    private int totalAmount;

    public Order(List<Cupcake> order, int totalAmount) {
        Order = order;
        this.totalAmount = totalAmount;
    }

    public List<Cupcake> getOrder() {
        return Order;
    }

    public void setOrder(List<Cupcake> order) {
        Order = order;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Order=" + Order +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
