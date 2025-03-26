package app.entities;

public class Bottom {
    private String bottom;
    private int price;

    public Bottom(String topping, int price) {
        this.bottom = topping;
        this.price = price;
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
        return "Bottom{" +
                "bottom='" + bottom + '\'' +
                ", price=" + price +
                '}';
    }
}
