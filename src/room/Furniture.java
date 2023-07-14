package room;

public class Furniture {
    private final String furnitureName;
    private int quantity;

    public Furniture(String furnitureName, int quantity) {
        this.furnitureName = furnitureName;
        this.quantity = quantity;
    }

    public String getFurnitureName() {
        return furnitureName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
