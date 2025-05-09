package utils;

public class Product {

    private Product() {

    }

    private static String productId;
    private static String productName;
    private static Float productPrice;
    private static String productType;
    private static int productQuantity;

    public static void setProductId(String productId) {
        Product.productId = productId;
    }

    public static String getProductId() {
        return productId;
    }

    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        Product.productName = productName;
    }

    public static Float getProductPrice() {
        return productPrice;
    }

    public static void setProductPrice(Float productPrice) {
        Product.productPrice = productPrice;
    }

    public static String getProductType() {
        return productType;
    }

    public static void setProductType(String productType) {
        Product.productType = productType;
    }

    public static int getProductQuantity() {
        return productQuantity;
    }

    public static void setProductQuantity(int productQuantity) {
        Product.productQuantity = productQuantity;
    }
}