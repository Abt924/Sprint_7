package scooter.test.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.junit.Before;
import org.junit.Test;
import scooter.model.*;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceptOrderTest extends BaseApiTest {

    @Before
    public void setUp() {
        super.setUp();
        // get all track numbers, order ids
        ValidatableResponse response = orderClient.getAll();
        orderTrackList = response.extract().body().path("orders.track");
        orderIdList = response.extract().body().path("orders.id");

        //create new courier, get id
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(courier);
        courierId = responseLogin.extract().body().path("id");
        couriersIdsToDelete.add(courierId);
    }

    @Test
    @DisplayName("Accept successful")
    @Description("new courier , existing order should be accepted")
    public void acceptOrderSuccessfulTest() {

        track = getTrackRandomOrderNotInDelivery();
        orderId = getOrderIdByTrack(track);

        ValidatableResponse response = orderClient.accept(orderId, courierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not OK", SC_OK, statusCode);

        boolean isAccepted = response.extract().body().path("ok");
        assertTrue("Order not accepted  ", isAccepted);

        boolean inDelivery = inDelivery(track);
        assertTrue("Order Accepted but not in Delivery", inDelivery);

    }

    @Test
    @DisplayName("Accept inDelivery forbidden ")
    @Description("Accept inDelivery forbidden")
    public void acceptOrderInDeliveryForbiddenTest() throws Exception {
        track = getTrackRandomOrderNotInDelivery();
        orderId = getOrderIdByTrack(track);
        // accept to Delivery
        orderClient.accept(orderId, courierId);
        // try to accept twice
        ValidatableResponse response = orderClient.accept(orderId, courierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not CONFLICT", SC_CONFLICT, statusCode);

        String message = response.extract().body().path("message");
        assertEquals("Message incorrect  ", "Этот заказ уже в работе", message);

    }

    @Test
    @DisplayName("accept by Not existing courier ")
    @Description(" ")
    public void acceptByNotExistingCourier() throws Exception {
        track = getTrackRandomOrderNotInDelivery();
        orderId = getOrderIdByTrack(track);
        // get courier
        Integer notExistCourierId = getNotExistingCourier();

        // try to accept
        ValidatableResponse response = orderClient.accept(orderId, notExistCourierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not NOT_FOUND", SC_NOT_FOUND, statusCode);

        String message = response.extract().body().path("message");
        assertEquals("Message incorrect  ", "Курьера с таким id не существует", message);

    }

    @Test
    @DisplayName("accept Not existing order ")
    @Description(" ")
    public void acceptNotExistingOrder() {

        orderId = getNotExistingOrderId();

        // try to accept
        ValidatableResponse response = orderClient.accept(orderId, courierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not NOT_FOUND", SC_NOT_FOUND, statusCode);

        String message = response.extract().body().path("message");
        assertEquals("Message incorrect  ", "Заказа с таким id не существует", message);

    }

    @Test
    @DisplayName("accept without courier ")
    @Description(" ")
    public void acceptWithoutCourierTest() {
        track = getTrackRandomOrderNotInDelivery();
        orderId = getOrderIdByTrack(track);
        // try to accept
        ValidatableResponse response = orderClient.acceptWithoutCourierId(orderId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().body().path("message");
        assertEquals("Message incorrect  ", "Недостаточно данных для поиска", message);

    }

    @Test
    @DisplayName("accept without order ")
    @Description(" ")
    public void acceptWithoutOrderTest() {
        ValidatableResponse response = orderClient.acceptWithoutTrack(courierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().body().path("message");
        assertEquals("Message incorrect  ", "Недостаточно данных для поиска", message);

    }

}