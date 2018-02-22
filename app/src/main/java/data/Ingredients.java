package data;

/**
 * Created by zaher on 2018-02-22.
 */

class Ingredients {
    String material;
    float quantity;
    String measurement;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public float getQuantity() {
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

    public Ingredients(String material, float quantity, String measurement) {
        this.material = material;
        this.quantity = quantity;
        this.measurement = measurement;
    }
}
