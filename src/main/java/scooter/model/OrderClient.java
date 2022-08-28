package scooter.model;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    public static String ORDER_PATH = "/api/v1/orders";
    public static String CANCEL_ORDER_PATH = "/api/v1/orders/cancel/";
    public static String TRACK_ORDER_PATH = "/api/v1/orders/track";
    public static String ACCEPT_ORDER_PATH = "/api/v1/orders/accept";

    //create
    @Step("create Order {order} ")
    public ValidatableResponse create(Order order) {
        return given().
                spec(spec).
                body(order).
                when().
                post(ORDER_PATH).
                then();
    }

    //accept order
    @Step("Accept Order {orderId} by courier {courierId}  ")
    public ValidatableResponse accept(int orderId, int courierId) {
        return given().
                spec(spec).
                basePath(ACCEPT_ORDER_PATH).
                queryParam("courierId", "" + courierId)
                .when()
                .put("/{orderId}", orderId)
                .then();
    }

    @Step("Accept Order by courier {courierId} without orderId in path ")
    public ValidatableResponse acceptWithoutTrack(int courierId) {

        return given()
                .spec(spec)
                .basePath(ACCEPT_ORDER_PATH)
                .queryParam("courierId", "" + courierId)
                .when()
                .put("/")
                .then();
    }

    @Step("Accept Order {orderId} by courier without courierId in query params ")
    public ValidatableResponse acceptWithoutCourierId(int orderId) {

        return given()
                .spec(spec)
                .basePath(ACCEPT_ORDER_PATH)
                .queryParam("courierId", "")
                .when().put("/{orderId}", orderId)
                .then();
    }


    //get all orders
    @Step("get all Orders")
    public ValidatableResponse getAll() {

        return given()
                .spec(spec)
                .when().
                get(ORDER_PATH).
                then();
    }

    //get order by track
    @Step("get Order by track number {track}  ")
    public ValidatableResponse get(int track) {

        return given()
                .spec(spec)
                .queryParam("t", "" + track)
                .when()
                .get(TRACK_ORDER_PATH)
                .then();
    }

    @Step("get Order by track with empty query params ")
    public ValidatableResponse getWithoutTrack() {
        return given()
                .spec(spec)
                .queryParam("t", "")
                .when()
                .get(TRACK_ORDER_PATH)
                .then();
    }


    //cancel order
    @Step("cancel Order with track number {track} ")
    public ValidatableResponse cancel(int track) {
        String json = "{ \"track\" : " + track + " }";
        return given()
                .spec(spec)
                .body(json)
                .when()
                .put(CANCEL_ORDER_PATH + track)
                .then();
    }


}
