package com.automation.tests.day3;


import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ExchangeRatesAPITests {


    @BeforeAll
    public static void setup(){
        //for every single request this is a base URI
        baseURI = "http://api.openrates.io";
    }

    //get latest currency rates
    @Test
    public void getLatestRates(){
        //after ? we specify query parameters. If there are couple of them we
        //http://www.google.com/index.html?q=apple&zip=123123
        //q ->> querry parameter
        //zip --> another query parameter
        //with rest assured, we provide query parameters into given() part
        //given() --> request preparation
        //you can specify query parameters in URL explicitly : http://ai.openrates.io/latest?base=USD
        //rest assured, will just assemble URL for you
        Response response = given().
                                    queryParam("base","USD").
                            when().
                                    get("/latest").prettyPeek();

        //verify that GET request to the endpoint was successful

        Headers headers = response.getHeaders();

        String contentType = headers.getValue("Content-Type");
        System.out.println("Content Type is:"+contentType);

        response.then().assertThat().statusCode(200);

        response.then().assertThat().body("base", is("USD"));
        //is --> same as equal (or we can use equalTo



        //lets verify that response contains today's date

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        response.then().assertThat().body("date",containsString(date));


    }


    public void getHistoryOfRates(){
        Response response = given().queryParam("base","USD").
                            when().
                            get("/2008-01-02").prettyPeek();

        Headers headers = response.getHeaders(); //response header

        response.then().assertThat().
                            statusCode(200).
                            and().
                            body("date",is("2008-01-02"));
    }



}
