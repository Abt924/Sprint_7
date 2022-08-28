package scooter.model;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestClient {
    public static final String URL = "http://qa-scooter.praktikum-services.ru/";
    public static final RequestSpecification spec = given()
            .baseUri(URL)
            .header("Content-type", "application/json");
}
