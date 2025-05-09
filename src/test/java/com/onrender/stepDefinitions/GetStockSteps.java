package com.onrender.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import static utils.AuthenticationToken.getAuthenticationToken;
import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.getStock;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;
import static utils.Product.getProductId;
import static utils.RandomGen.randomString;

public class GetStockSteps {
    private static final String SCHEMA = schemaFile(GetStockSteps.class.getSimpleName());
    private ValidatableResponse getStockResponse;
    private static int initialStock;

    public void theUserChecksTheStockLevelOfAProduct(String productId) {
        getStockResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .when()
                .get(getStock(productId))
                .then();
    }

    @When("^the user checks the initial stock of the product$")
    public void theUserChecksTheInitialStockLevelOfTheProduct() {
        theUserChecksTheStockLevelOfAProduct(getProductId());
        getStockResponse.statusCode(200);
        getStockResponse.body(matchesJsonSchema(SCHEMA));
        initialStock = getStockResponse.extract().response().body().path("currentStock");
    }

    @Then("^the user sees the stock level of the product has now (increased|decreased) by (\\d+) unit$")
    public void theUserSeesTheStockLevelHasIncreased(String stockUpdate, int quantity) {
        theUserChecksTheStockLevelOfAProduct(getProductId());
        getStockResponse.statusCode(200);
        getStockResponse.body(matchesJsonSchema(SCHEMA));
        int expectedStock = 0;
        switch (stockUpdate) {
            case "increased":
                expectedStock = initialStock + quantity;
                break;
            case "decreased":
                expectedStock = initialStock - quantity;
                break;
            default:
                Assert.fail("Invalid stock update value. Expected value : 'increased' or 'decreased'");
        }
        int actualStock = getStockResponse.extract().response().body().path("currentStock");
        Assert.assertEquals(expectedStock, actualStock);
    }

    @When("^the user checks the stock of a product with no authorization$")
    public void theUserChecksTheStockOfAProductWithNoAuthorization() {
        getStockResponse = given()
                .spec(requestSpec)
                .when()
                .get(getStock(randomString(36)))
                .then();
    }

    @Then("^a (\\d+) response code is returned from the GET endpoint$")
    public void aResponseCodeIsReturnedFromTheGETEndpoint(int statusCode) {
        getStockResponse.statusCode(statusCode);
    }

    @And("^the message returned is (.*)$")
    public void theMessageReturnedIsValidated(String expectedMessage) {
        String actualMessage = getStockResponse.extract().response().body().path("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @When("the user tries to fetch stock of an invalid product")
    public void theUserTriesToFetchStockOfAnInvalidProduct() {
        theUserChecksTheStockLevelOfAProduct(randomString(10));
    }
}