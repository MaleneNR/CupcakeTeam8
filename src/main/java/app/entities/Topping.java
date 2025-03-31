package app.entities;

public class Topping {
    private int toppingId;
    private String topping;
    private int price;

    public Topping(int toppingId, String topping, int price) {
        this.toppingId = toppingId;
        this.topping = topping;
        this.price = price;
    }

    public Topping(String topping, int price) {
        this.topping = topping;
        this.price = price;
    }

    public int getToppingId() {
        return toppingId;
    }

    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return topping;
    }
}
