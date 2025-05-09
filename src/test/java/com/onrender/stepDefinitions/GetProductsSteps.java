package com.onrender.stepDefinitions;

import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import utils.ResponseContainer;

import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.getAllProducts;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;

public class GetProductsSteps {
    private static final String SCHEMA = schemaFile(GetProductsSteps.class.getSimpleName());
    private ValidatableResponse getProductsResponse;

    @When("^the user can retrieve all available products$")
    public void theUserCanRetrieveAllAvailableProducts() {
        getProductsResponse = given()
                .spec(requestSpec)
                .when()
                .get(getAllProducts())
                .then()
                .statusCode(200);
        getProductsResponse.body(matchesJsonSchema(SCHEMA));
    }
}