package com.automation.tests.day3;


import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRatesAPITests {


    @BeforeAll
    public static void setup(){
        //for every single request this is a base URI
        baseURI = "http://api.openrates.io";
    }

    //get latest currency rates
    @Test
    public void getLatestRates(){
        Response response = get("/latest").prettyPeek();

        //verify that GET request to the endpoint was successful
        response.then().assertThat().statusCode(200);

    }
}
