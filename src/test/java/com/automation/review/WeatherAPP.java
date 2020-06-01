package com.automation.review;
import io.restassured.response.Response;

import java.util.Scanner;

import static io.restassured.RestAssured.*;


public class WeatherAPP {


    static {
        baseURI = "https://www.metaweather.com/api/location";
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city name: ");
        String city = scanner.nextLine();
        String woeid = getWOEID(city);
        printWeatherInfor(woeid);
    }


    public static String getWOEID(String city){
        Response response = given().queryParam("query",city).get("/search").prettyPeek();
        String woeid = response.jsonPath().getString("woeid");
        System.out.println("WOEID" + woeid);
        return woeid;
    }

    public static void printWeatherInfor(String woeid){
        woeid = woeid.replaceAll("\\D","");  // to delete all non-digits
        Response response = get("{woeid}",woeid).prettyPeek();

    }
}
