package com.onrender.stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import static utils.AuthenticationToken.getAuthenticationToken;
import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;
import static utils.Product.getProductId;
import static utils.RandomGen.randomString;

public class DeleteProductSteps {
    private static final String SCHEMA = schemaFile(DeleteProductSteps.class.getSimpleName());
    private ValidatableResponse deleteProductResponse;

    @When("^the user deletes the product$")
    public void theUserDeletesTheProduct() {
        deleteProductResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .when()
                .delete(deleteProduct(getProductId()))
                .then()
                .statusCode(200)
                .body(matchesJsonSchema(SCHEMA));
        deleteProductResponse.body(matchesJsonSchema(SCHEMA));
    }

    @Then("^the user sees the message (.*)$")
    public void theUserCanSeeTheMessage(String expectedMessage) {
        String actualMessage = deleteProductResponse.extract().response().body().path("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Then("^a (\\d+) response code is returned from the DELETE endpoint$")
    public void aResponseCodeIsReturnedFromTheGETEndpoint(int statusCode) {
        deleteProductResponse.statusCode(statusCode);
    }

    @When("the user tries to delete the product with no authorization")
    public void theUserTriesToDeleteTheProductWithNoAuthorization() {
        deleteProductResponse = given()
                .spec(requestSpec)
                .when()
                .delete(deleteProduct(getProductId()))
                .then();
    }

    @When("the user tries to delete an invalid product")
    public void theUserTriesToDeleteAnInvalidProduct() {
        deleteProductResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .when()
                .delete(deleteProduct(randomString(6)))
                .then();
    }

    @When("^the user deletes given product (.*)$")
    public void theUserDeletesGivenProduct(String productId) {
        deleteProductResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .when()
                .delete(deleteProduct(productId))
                .then();
    }
}