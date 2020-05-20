package com.automation.tests.day3;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ORDSTestsDay3 {

    @BeforeAll
    public static void setup(){
        baseURI = "http://54.224.118.38:1000/ords/hr";
    }

    /**
     * given path parameter is "/regions/{id}" (after / slash its the path parameter, for example id is path parameter
     * when user makes get request
     * and region id is equals to 1
     * then assert that status code is 200
     * and assert that region name is Europe
     *
     */

    @Test
    public void verifyFirstRegion(){
        given().
                pathParams("id",1).
        when().
                get("/regions/{id}").prettyPeek().
                then().assertThat().
                statusCode(200).
                body("region_name", is("Europe")).
                body("region_id",is(1)).
                time(lessThan(5L), TimeUnit.SECONDS); //verify that response time is less than 5 seconds

    }

    @Test
    public void verifyEmployee(){
        Response response = given().
                                accept(ContentType.JSON).
                            when().
                                get("/employees");

        JsonPath jsonPath = response.jsonPath();

        //GPath syntax, something like xpath bit different. GPath use Groove syntax
        String nameOfFirstEmployee = jsonPath.getString("items[0].first_name"); //0 to get first item in the list
        String nameOfLastEmployee = jsonPath.getString("items[-1].first_name"); //-1 last item in the list

        System.out.println("First name of first employee: "+nameOfFirstEmployee);
        System.out.println("First name of last employee: "+nameOfLastEmployee);


        Map<String,Object> firsEmployee = jsonPath.get("items[0]");
        System.out.println("firsEmployee = " + firsEmployee);

    }


}
