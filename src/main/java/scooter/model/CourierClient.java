package scooter.model;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class CourierClient extends RestClient {
    public static String LOGIN_COURIER_PATH = "/api/v1/courier/login";
    public static String COURIER_PATH = "/api/v1/courier";

    @Step("create Courier {courier} ")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(spec)
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("create Courier from json {json}  ")
    public ValidatableResponse create(String json) {
        return given()
                .spec(spec)
                .body(json)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    //login
    @Step("login Courier {courier}  ")
    public ValidatableResponse login(Courier courier) {
        CourierCredentials credentials = CourierCredentials.from(courier);
        return given()
                .spec(spec)
                .body(credentials)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then();
    }

    @Step("login Courier with credentials {json}  ")
    public ValidatableResponse login(String json) {
        return given()
                .spec(spec)
                .body(json)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then();
    }


    //delete
    @Step("delete Courier id = {id}  ")
    public ValidatableResponse delete(Integer id) {
        String json = "{ \"id\" : \"" + id + "\" }";
        return given()
                .spec(spec)
                .basePath(COURIER_PATH)
                .body(json)
                .when()
                .delete("/{id}", id)
                .then();
    }

    //delete
    @Step("delete Courier id={id} without id in json ")
    public ValidatableResponse deleteWithoutIdJson(Integer id) {

        return given()
                .spec(spec)
                .basePath(COURIER_PATH)
                .when()
                .delete("/{id}", id)
                .then();
    }

    //get Courier Orders
    @Step("get Courier Orders ")
    public ValidatableResponse getCountOfCourierOrders(int courierId){
        return given()
                .spec(spec)
                .basePath(COURIER_PATH)
                .when()
                .get("/{courierId}/ordersCount", courierId)
                .then();
    }

}
