package app.entities;

public class Bottom {
    private int bottomId;
    private String bottom;
    private int price;

    public Bottom(int bottomId, String topping, int price) {
        this.bottomId = bottomId;
        this.bottom = topping;
        this.price = price;
    }

    public Bottom(String bottom, int price) {
        this.bottom = bottom;
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
                "bottomId='" + bottomId + '\'' +
                "bottom='" + bottom + '\'' +
                ", price=" + price +
                '}';
    }
}
