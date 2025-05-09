package httpfactory;

import utils.Config;

public class URLFactory {

    private URLFactory() {

    }

    public static String getStatus() {
        return Config.getLocalisedMandatoryPropValue("get.status");
    }

    public static String userLogin() {
        return Config.getLocalisedMandatoryPropValue("user.login");
    }

    public static String addProduct() {
        return Config.getLocalisedMandatoryPropValue("add.product");
    }

    public static String getAllProducts() {
        return Config.getLocalisedMandatoryPropValue("get.products");
    }

    public static String getProduct(String productId) {
        return Config.getLocalisedMandatoryPropValue("get.product").replace("{productId}", productId);
    }

    public static String updateProduct(String productId) {
        return Config.getLocalisedMandatoryPropValue("update.product").replace("{productId}", productId);
    }

    public static String createOrder() {
        return Config.getLocalisedMandatoryPropValue("create.order");
    }

    public static String getStock(String productId) {
        return Config.getLocalisedMandatoryPropValue("get.stock").replace("{productId}", productId);
    }

    public static String deleteProduct(String productId) {
        return Config.getLocalisedMandatoryPropValue("delete.product").replace("{productId}", productId);
    }
}