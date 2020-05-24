package com.automation.tests.day6;

import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOPracticeWithSpartanApp {



    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }

    /**
     *   "gender": "Male",
     *   "name": "Erfan",
     *   "phone": "8888888888"
     */
    @Test
    public void addSpartanTest(){

        Map<String,String> spartan = new HashMap<>();
        spartan.put("gender","Male");
        spartan.put("name","AppleBee");
        spartan.put("phone","123456781012");

        RequestSpecification requestSpecification = given().
                auth().basic("admin","admin").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan);



        Response response = given().
                                    auth().basic("admin","admin").
                                    contentType(ContentType.JSON).
                                    accept(ContentType.JSON).
                                    body(spartan).
                            when().
                                    post("/spartans").prettyPeek();  //post request, we add data to server


        response.then().statusCode(201);
        response.then().body("success",is("A Spartan is Born!"));

    }

    @Test
    public void updateSpartanTest(){

        int userToUpdate = 306;




    }
}
