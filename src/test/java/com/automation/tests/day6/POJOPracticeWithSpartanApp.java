package com.automation.tests.day6;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Retrieve existing username, and update name and verify if update is successfully")
    public void updateSpartanTest(){

        int userToUpdate = 306;
        String name = "Cucumber306";


        //HTTP PUT request to update existing record, for example existing Spartan
        //PUT - requires to provide ALL parameters in body

        Spartan spartan = new Spartan(name,"Male",123456781012L);

        //get spartan from web service
        Spartan spartanToUpdate = given().
                                        auth().basic("admin","admin").
                                        accept(ContentType.JSON).
                                  when().
                                        get("/spartans/{id}",userToUpdate).as(Spartan.class);

        //update property that you need without affecting other properties
        System.out.println("Before update:"+spartanToUpdate);
        spartanToUpdate.setName(name);
        System.out.println("After update: " + spartanToUpdate);

        Response response = given().
                                    auth().basic("admin","admin").
                                    contentType(ContentType.JSON).
                                    body(spartan).                        //pass the object into the body of request
                            when().
                                    put("/spartans/{id}",userToUpdate).prettyPeek();


        response.then().statusCode(204);


        System.out.println("#########################################################");
        //to get user with id 306, the one we just updated
        given().
                auth().basic("admin","admin").
        when().
                get("/spartans/{id}",userToUpdate).prettyPeek().
        then().
                statusCode(200).body("name",is(name));



    }
}
