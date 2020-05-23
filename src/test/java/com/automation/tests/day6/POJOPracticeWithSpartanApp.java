package com.automation.tests.day6;

import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class POJOPracticeWithSpartanApp {



    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }
}
