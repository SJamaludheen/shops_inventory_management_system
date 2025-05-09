package com.onrender.stepDefinitions;

import com.onrender.requests.RequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import jsonfactory.addProduct.AddProduct;
import org.junit.Assert;

import java.io.IOException;

import static utils.AuthenticationToken.getAuthenticationToken;
import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.addProduct;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;
import static utils.Product.*;
import static utils.RandomGen.randomNumberString;

public class AddProductSteps {
    private final RequestBuilder requestBuilder = new RequestBuilder();
    private static final String SCHEMA = schemaFile(AddProductSteps.class.getSimpleName());
    private AddProduct addProductRequest = new AddProduct();
    private ValidatableResponse addProductResponse;
    String productName, productType;

    public void userAddsProduct() {
        addProductResponse = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + getAuthenticationToken())
                .body(requestBuilder.objToJsonString(addProductRequest))
                .when()
                .post(addProduct())
                .then();
    }
    @When("^the user adds a new product (.*) of product type (.*)$")
    public void theUserAddsANewProduct(String productName, String productType) throws IOException {
        addProductRequest = requestBuilder.addProductRequest();
        setRequestData(productName, productType);
        userAddsProduct();
        addProductResponse.statusCode(201);
        addProductResponse.body(matchesJsonSchema(SCHEMA));
        retrieveProductDetails();
    }

    public void setRequestData(String productName, String productType) {
        String name = productName + " " + randomNumberString(1,1000);
        addProductRequest.setName(name);
        addProductRequest.setProductType(productType);
        setProductName(name);
        setProductType(productType);
        this.productName = name;
        this.productType = productType;
    }

    public void retrieveProductDetails() {
        setProductId(addProductResponse.extract().response().body().path("productId"));
        setProductPrice(addProductRequest.getPrice());
        setProductQuantity(addProductRequest.getQuantity());
    }

    @When("^the user adds a new product with no authorization$")
    public void theUserAddsANewProductWithNoAuthorization() throws IOException {
        addProductRequest = requestBuilder.addProductRequest();
        addProductResponse = given()
                .spec(requestSpec)
                .body(requestBuilder.objToJsonString(addProductRequest))
                .when()
                .post(addProduct())
                .then();
    }

    @Then("^a (\\d+) response code is returned from the POST endpoint$")
    public void aResponseCodeIsReturnedFromTheGETEndpoint(int statusCode) {
        addProductResponse.statusCode(statusCode);
    }

    @When("^the user adds a new product with invalid price$")
    public void theUserAddsANewProductWithInvalidPrice() throws IOException {
        addProductRequest = requestBuilder.addProductRequest();
        addProductRequest.setPrice(-10.00F);
        userAddsProduct();
    }

    @And("^the message displayed is (.*)$")
    public void theMessageReturnedIsValidated(String expectedMessage) {
        String actualMessage = addProductResponse.extract().response().body().path("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @When("^the user adds a new product with missing (.*)$")
    public void theUserAddsANewProductWithMissingValue(String fieldValue) throws IOException {
        addProductRequest = requestBuilder.addProductRequest();
        switch (fieldValue) {
            case "name":
                addProductRequest.setName(null);
                break;
            case "price":
                String name = "Chess " + randomNumberString(30,100);
                addProductRequest.setName(name);
                addProductRequest.setPrice(null);
                break;
            default:
                Assert.fail("Invalid field value provided.");
        }
        userAddsProduct();
    }

    @When("^the user adds the product (.*) of product type (.*) again$")
    public void theUserAddsTheSameProductOfSameProductTypeAgain(String productName, String productType) throws IOException {
        addProductRequest = requestBuilder.addProductRequest();
        addProductRequest.setName(this.productName);
        addProductRequest.setProductType(this.productType);
        userAddsProduct();
    }
}