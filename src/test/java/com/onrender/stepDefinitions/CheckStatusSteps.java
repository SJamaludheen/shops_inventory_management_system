package com.onrender.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.getStatus;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;

public class CheckStatusSteps {
    private static final String SCHEMA = schemaFile(CheckStatusSteps.class.getSimpleName());
    private ValidatableResponse checkStatusResponse;

    @Given("^the user checks for application status$")
    public void aRequestToRetrieveTheItemIsMade() {
        checkStatusResponse = given()
                .spec(requestSpec)
                .when()
                .get(getStatus())
                .then()
                .statusCode(200);
        checkStatusResponse.body(matchesJsonSchema(SCHEMA));
    }


    @Then("^the application status returned is (.*)$")
    public void theApplicationStatusReturnedIsValidated(String status) {
        Assert.assertEquals(status, checkStatusResponse.extract().path("status"));
    }

    @Then("^the DB status returned is (.*)$")
    public void theStatusReturnedIsValidated(String status) {
        Assert.assertEquals(status, checkStatusResponse.extract().path("dbStatus"));
    }
}