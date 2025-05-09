package com.onrender.model;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import utils.CacheService;
import utils.Config;
import utils.ResponseContainer;

public class Setup {

    public static RequestSpecification requestSpec;

    private static void setupRequestSpecBuilder() {
        RequestSpecBuilder builder;
        builder = new RequestSpecBuilder();
        builder.setContentType(ContentType.JSON);
        builder.setUrlEncodingEnabled(false);
        builder.setBaseUri(Config.getLocalisedMandatoryPropValue("baseUrl"));
        requestSpec = builder.build();
    }

    @Before(order = 3001)
    public static void setFromEnvVariables(Scenario scenario) {
        String env = System.getProperty("ENV"); //-DENV="@dev"
        if (env != null) {
            switch (env) {
                case "@dev":
                    setDEVProperties(scenario);
                    break;
                case "@test":
                    setTESTProperties(scenario);
                    break;
            }
        }
    }


    @Before(order = 5001, value = "@dev")
    public static void setDEVProperties(Scenario scenario) {
        Config.props.setProperty("environment", "dev");
        setEnvironment();
        setupRequestSpecBuilder();
    }

    @Before(order = 5001, value = "@test")
    public static void setTESTProperties(Scenario scenario) {
        Config.props.setProperty("environment", "test");
        setEnvironment();
        setupRequestSpecBuilder();
    }

    private static void setEnvironment() {
        String environment = Config.props.getProperty("environment");
        Config.props.setProperty("localisedPropertiesFile",
                "environment/" + environment.toUpperCase() + ".properties");
    }

    @After(order = 1)
    public void clearCache() {
        ResponseContainer.clearCacheInstance();
        CacheService.clearCacheInstance();
    }

}
