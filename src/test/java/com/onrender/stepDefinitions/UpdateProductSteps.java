package com.onrender.stepDefinitions;

import com.onrender.requests.RequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import jsonfactory.updateProduct.UpdateProduct;
import org.junit.Assert;

import java.io.IOException;

import static utils.AuthenticationToken.getAuthenticationToken;
import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.updateProduct;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;
import static utils.Product.*;
import static utils.RandomGen.randomNumberString;
import static utils.RandomGen.randomString;

public class UpdateProductSteps {
    private final RequestBuilder requestBuilder = new RequestBuilder();
    private static final String SCHEMA = schemaFile(UpdateProductSteps.class.getSimpleName());
    private UpdateProduct updateProductRequest = new UpdateProduct();
    private ValidatableResponse updateProductResponse;

    @When("^the user can update the product as (.*) of product type (.*)$")
    public void theUserCanUpdateTheProduct(String productName, String productType) throws IOException {
        updateProductRequest = requestBuilder.updateProductRequest();
        setRequestData(productName, productType);
        updateProductResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .body(requestBuilder.objToJsonString(updateProductRequest))
                .when()
                .put(updateProduct(getProductId()))
                .then()
                .statusCode(200);
        updateProductResponse.body(matchesJsonSchema(SCHEMA));
    }

    public void setRequestData(String productName, String productType) {
        String name = productName + randomNumberString(1,50);
        updateProductRequest.setName(name);
        updateProductRequest.setProductType(productType);
        setProductName(name);
        setProductType(productType);
        setProductQuantity(updateProductRequest.getQuantity());
        setProductPrice(updateProductRequest.getPrice());
    }

    @When("^the user tries to update an invalid product$")
    public void theUserTriesToUpdateAnInvalidProduct() throws IOException {
        updateProductRequest = requestBuilder.updateProductRequest();
        updateProductResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .body(requestBuilder.objToJsonString(updateProductRequest))
                .when()
                .put(updateProduct(randomString(6)))
                .then();
    }

    @Then("^a (\\d+) response code is returned from the PUT endpoint$")
    public void aResponseCodeIsReturnedFromTheGETEndpoint(int statusCode) {
        updateProductResponse.statusCode(statusCode);
    }

    @And("^the error message displayed is (.*)$")
    public void theMessageReturnedIsValidated(String expectedMessage) {
        String actualMessage = updateProductResponse.extract().response().body().path("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @When("^the user tries to update the product with no authorization$")
    public void theUserCanUpdateTheProduct() throws IOException {
        updateProductRequest = requestBuilder.updateProductRequest();
        updateProductResponse = given()
                .spec(requestSpec)
                .body(requestBuilder.objToJsonString(updateProductRequest))
                .when()
                .put(updateProduct(getProductId()))
                .then();
    }
}