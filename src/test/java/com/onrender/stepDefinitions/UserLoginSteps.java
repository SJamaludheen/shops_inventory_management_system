package com.onrender.stepDefinitions;

import com.onrender.requests.RequestBuilder;
import io.cucumber.java.en.Given;
import io.restassured.response.ValidatableResponse;
import jsonfactory.userLogin.UserLogin;
import utils.Config;

import java.io.IOException;

import static utils.AuthenticationToken.setAuthenticationToken;
import static com.onrender.model.Setup.requestSpec;
import static httpfactory.URLFactory.userLogin;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static utils.Config.schemaFile;

public class UserLoginSteps {
    private final RequestBuilder requestBuilder = new RequestBuilder();
    private static final String SCHEMA = schemaFile(UserLoginSteps.class.getSimpleName());
    private UserLogin userLoginRequest = new UserLogin();
    private ValidatableResponse loginResponse;

    @Given("^the user is logged in$")
    public void theUserIsLoggedIn() throws IOException {
        userLoginRequest = requestBuilder.userLoginRequest();
        setUserCredentials();
        loginResponse = given()
                .spec(requestSpec)
                .body(requestBuilder.objToJsonString(userLoginRequest))
                .when()
                .post(userLogin())
                .then()
                .statusCode(200);
        loginResponse.body(matchesJsonSchema(SCHEMA));
        setAuthenticationToken(loginResponse.extract().response().body().path("token").toString());
    }

    public void setUserCredentials() {
        userLoginRequest.setUsername(Config.getLocalisedMandatoryPropValue("username"));
        userLoginRequest.setPassword(Config.getLocalisedMandatoryPropValue("password"));
    }
}