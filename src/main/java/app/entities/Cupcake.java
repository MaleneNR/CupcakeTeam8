package app.entities;

public class Cupcake {
    private String topping;
    private String bottom;
    private int price;


    public Cupcake(String topping, String bottom, int price) {
        this.topping = topping;
        this.bottom = bottom;
        this.price = price;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
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
