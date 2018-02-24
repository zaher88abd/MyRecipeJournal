package data;

/**
 * Created by zaher on 2018-02-22.
 */

public class Ingredient {
    String material;
    double quantity;
    String measurement;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Ingredient(String material, double quantity, String measurement) {
        this.material = material;
        this.quantity = quantity;
        this.measurement = measurement;
    }
}
