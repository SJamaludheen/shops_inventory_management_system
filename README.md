# RESTAssured-Cucumber-Java test automation framework

It is a maven project, meaning all the dependencies are in the pom.xml file, allowing anyone to easily clone the project on their local.

Dependencies:
•	Java

•	Maven

Technologies and tools:

•	RestAssured (for API tests)

•	Java 11 (higher version might work, but it hasn’t been tested)

•	GSON - to convert Java objects to JSON and vice versa (serialisation and deserialisation).

•	Cucumber - runs the steps of the Gherkin scenarios

•	Cucumber reporting plugin - a plugin to generate a comprehensive visual HTML test report

•	IntelliJ (IDE).

As this is a maven project, the project set up is structured into main and test folders :

•	Under main folder, classes used for building urls (httpfactory package), for holding json objects (jsonfactory package), and for Java utilities (utils package) are added 

•	Under test folder, there are two sub folders - java and resources. In java subfolder, we have RequestBuilder, ResponseBuilder, StepDefinitions for feature files. 
    RequestBuilder and ResponseBuilder are explained further below. StepDefinitions are standard Cucumber classes. In resources subfolder, we have cucumber feature 
    files, environment specific properties files, json files used in requests and response json schema definition files - explained further below on their usages.

# Setup and execution

## Setup

1.	Make sure you have installed Java SE 11 and Maven on your machine
	
2.	Clone the repository
	
3.	Open your IDE (IntelliJ)
	
4.	Open the project using pom.xml file (IntelliJ)
	
5.	Wait until all dependencies are downloaded and installed

6.	Install ‘Cucumber for Java’ and ‘Gherkin’ plugins for IntelliJ to view the feature files better and be able to navigate to the step definition from the feature file.

## Execution

In order to run tests from the Command Line, you need to download Maven and set it up properly. 
The command to run tests from the Command Line (Terminal) is: `mvn clean verify` or `mvn clean install`

## Execution flow and feature tagging

Execution starts from Setup class where the tags are checked. Scenarios are to be tagged as either ‘@dev’ or ‘@test’ if running a single scenario.
If running as a whole project, the property variable is to be set as ‘-DENV=@dev’. If '@dev' tag is detected then environment is set to DEV and 
if '@test' then environment is set to TEST.
Environment specific properties files are stored in src>test>resources>environment.
These files have all the URIs for the endpoints used in the tests. As seen below, specific properties file is set in Setup.java based on tag.

By default, all tests from features folder for dev environment is run. When running tests from the command line it's possible to override this setting by using the cucumber.options java system property.

Individual test scenarios can also be run by giving a specific tag and running it from the TestRunner class by updating the tag value.

The tags used for each feature can be found above individual test scenarios in their feature files.

## Config

The baseUrl and endpoints URIs are stored in the properties files.

The urls are built in URLFactory class. This class has methods to build URLs for the endpoints. We pass these methods as an argument to RestAssured .get(), .post(), .delete() methods.

There is a Config class which has methods to read the properties from the properties file. Once the environment is set and the path to the environment properties file is set in Setup.java, during the execution if test requires 
an endpoint uri to be read from properties files then method ‘getLocalisedMandatoryPropValue(String property)’ is called. Also in the Config.java class there is a method called 'schemaFile(String testCase)’ which takes 
the name of the test running as parameter and returns the schemafile specific to the test in return.

schema files are defined under src>test>resources>schema. These files define the required fields to look for in a json. Also, the  expected data type (ex: integer, string, boolean, etc.) of the properties in the json response received by the test.

## JSON payload handling in the framework

All the libraries used in the framework are listed as dependencies in pom.xml of the project. GSON is one of them.
As part of the request we will be sending payloads to some of the endpoints. We use GSON to load the request payloads stored under folder src>test>resources>requestpayloads into java object instances. The conversion happens in src>test>java>requests>RequestBuilder.
The main top level object (e.g.: src>main>java>createObject>CreateObject.java) is initialised during the test and the request payload is stored in to the main java object.
To convert a json in to java objects, create a directory under src>main>jsonfactory and add POJOs (generated from JSON).


## RequestBuilder class
In this class, using GSON we read the request json files under requestpayloads folder. We create methods for each endpoint and this method has the logic to read the request json file and convert it to its corresponding endpoint java object, using GSON.

We call these methods from step definition classes. Once the json is created as java object, any value changes can be done easily by calling the object inside the main java object.

Once we make changes to the java object then we convert the java object into string and send it to the endpoint.

## RESTAssured - RequestSpecification and ResponseSpecification

### RequestSpecification:

When the scenario is executed then setFromEnvVariables() method is initiated in Setup class. Then SetDEVProperties(scenario) method is called which in turn calls setupRequestSpecBuilder() - this method builds the request specification by setting contenttype, baseurl, etc.
Once set, it is used in the calls made to the endpoints all throughout the tests.

### ResponseSpecification:
responseBuilder class holds all the ResponseSpecifications. When there are multiple verifications to be made in the json response received, then we can build a specification of all the assert matchers using builder.

This method can be then passed as an argument to the RestAssured built in Spec() method. The spec takes the response and makes sure the response is in line with the custom ResponseSpecification method passed as an argument.

## Reporting

This framework uses Cucumber reporting plugin to create a html file with the test results in target folder.  Once the tests are executed, the Cucumber html report can be found in the folder target > cucumber-reports. This can be opened in a browser to see all the test scenarios executed.

## Mac OS X Setup instructions

* Download and install Java
* From the command line run `brew install maven`
* Follow the execution instructions above to run the tests



