package com.onrender.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.getProduct;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;
import static utils.Config.schemaFile;
import static utils.Product.*;

public class GetProductSteps {
    private static final String SCHEMA = schemaFile(GetProductSteps.class.getSimpleName());
    private ValidatableResponse getProductResponse;

    public void userRetrievesProduct() {
        getProductResponse = given()
                .spec(requestSpec)
                .when()
                .get(getProduct(getProductId()))
                .then();
    }

    @Then("^the user sees the product details are updated$")
    @Then("^the user sees the newly added product exists$")
    public void theUserRetrievesTheProduct() {
        userRetrievesProduct();
        getProductResponse.statusCode(200);
        getProductResponse.body(matchesJsonSchema(SCHEMA));
    }

    @Then("^the user can see the product doesn't exist$")
    public void theUserCanSeeTheProductDoesNotExist() {
        userRetrievesProduct();
        getProductResponse.statusCode(404);
    }

    @Then("^the user sees the error message displayed is (.*)$")
    public void theUserSeesTheErrorMessage(String expectedMessage) {
        String actualMessage = getProductResponse.extract().response().body().path("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @And("the product details - name, price, productType and quantity match")
    public void theProductDetailsMatch() {
        //Using hamcrest matchers to compare values
        getProductResponse.body("name", equalTo(getProductName()));
        getProductResponse.body("price", equalTo(getProductPrice()));
        getProductResponse.body("productType", equalTo(getProductType()));
        getProductResponse.body("quantity", equalTo(getProductQuantity()));
    }
}