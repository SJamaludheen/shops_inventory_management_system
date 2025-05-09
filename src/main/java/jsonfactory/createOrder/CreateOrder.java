package jsonfactory.createOrder;

import com.google.gson.annotations.Expose;

public class CreateOrder {

    @Expose
    private String orderType;
    @Expose
    private String productId;
    @Expose
    private Long quantity;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}