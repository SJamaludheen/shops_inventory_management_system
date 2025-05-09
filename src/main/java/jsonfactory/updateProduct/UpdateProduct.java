package jsonfactory.updateProduct;

import com.google.gson.annotations.Expose;

public class UpdateProduct {

    @Expose
    private String name;
    @Expose
    private Float price;
    @Expose
    private String productType;
    @Expose
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
