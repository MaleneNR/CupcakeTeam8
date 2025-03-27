package app.entities;

public class Cupcake {
    private Topping topping;
    private Bottom bottom;
    private int price;
    private int quantity;

    public Cupcake(Topping topping, Bottom bottom, int price, int quantity) {
        this.topping = topping;
        this.bottom = bottom;
        this.price = price;
        this.quantity = quantity;
    }

    public Cupcake(Topping topping, Bottom bottom, int price) {
        this.topping = topping;
        this.bottom = bottom;
        this.price = price;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public void setBottom(Bottom bottom) {
        this.bottom = bottom;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cupcake{" +
                "topping='" + topping + '\'' +
                ", bottom='" + bottom + '\'' +
                ", price=" + price +
                '}';
    }
}
