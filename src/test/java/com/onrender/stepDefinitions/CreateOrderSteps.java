package com.onrender.stepDefinitions;

import com.onrender.requests.RequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import jsonfactory.createOrder.CreateOrder;
import org.junit.Assert;

import java.io.IOException;

import static utils.AuthenticationToken.getAuthenticationToken;
import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.createOrder;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;
import static utils.Product.getProductId;

public class CreateOrderSteps {
    private final RequestBuilder requestBuilder = new RequestBuilder();
    private static final String SCHEMA = schemaFile(CreateOrderSteps.class.getSimpleName());
    private CreateOrder createOrderRequest = new CreateOrder();
    private ValidatableResponse createOrderResponse;

    public void userCreatesOrder() {
        createOrderResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .body(requestBuilder.objToJsonString(createOrderRequest))
                .when()
                .post(createOrder())
                .then();
    }
    @When("^the user (buy|sell)s (\\d+) unit of the product$")
    public void theUserCreatesOrderForTheProduct(String orderType, long quantity) throws IOException {
        createOrderRequest = requestBuilder.createOrderRequest();
        setRequestData(orderType, getProductId(), quantity);
        userCreatesOrder();
        createOrderResponse.statusCode(201);
        createOrderResponse.body(matchesJsonSchema(SCHEMA));
    }

    public void setRequestData(String orderType, String productId, long quantity) {
        createOrderRequest.setOrderType(orderType);
        createOrderRequest.setProductId(productId);
        createOrderRequest.setQuantity(quantity);
    }

    @When("^the user cannot (.*) any unit of the product$")
    public void theUserTriesToCreateOrderForTheProduct(String orderType) throws IOException {
        createOrderRequest = requestBuilder.createOrderRequest();
        setRequestData(orderType, getProductId(), 1);
        userCreatesOrder();
    }

    @Then("^a (\\d+) response code is received from the POST endpoint$")
    public void aResponseCodeIsReceivedFromThePOSTEndpoint(int statusCode) {
        createOrderResponse.statusCode(statusCode);
    }

    @And("^the user sees the error message displayed as (.*)$")
    public void theUserSeesTheErrorMessageDisplayedAs(String expectedMessage) {
        String actualMessage = createOrderResponse.extract().response().body().path("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @When("^the user tries to create order with no authorization$")
    public void theUserTriesToCreateOrderWithNoAuthorization$() throws IOException {
        createOrderRequest = requestBuilder.createOrderRequest();
        createOrderResponse = given()
                .spec(requestSpec)
                .body(requestBuilder.objToJsonString(createOrderRequest))
                .when()
                .post(createOrder())
                .then();
    }

    @When("^the user tries to (buy|sell) (\\d+) unit of (valid|invalid) product$")
    public void theUserCreatesOrderForAnInvalidProduct(String orderType, long quantity, String product) throws IOException {
        createOrderRequest = requestBuilder.createOrderRequest();
        String productId = "";
        switch (product) {
            case "invalid":
                productId = getProductId() + "1";
                break;
            case "valid":
                productId = getProductId();
                break;
            default:
                Assert.fail("Invalid value for type of product. Expected value : 'invalid' or 'valid'");
        }
        setRequestData(orderType, productId, quantity);
        userCreatesOrder();
    }
}