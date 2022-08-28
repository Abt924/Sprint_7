package scooter.test.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import scooter.model.*;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class GetOrderByTrackTest extends BaseApiTest {


    @Before
    public void setUp() {
        super.setUp();
        ValidatableResponse response = orderClient.getAll();
        orderTrackList = response.extract().body().path("orders.track");
    }


    @Test
    @DisplayName("get Order By Track")
    @Description("by exist random track Should return not Null order object with the same track ")
    public void getOrderByTrackTest() {
        track = getRandomExistingTrack();

        ValidatableResponse response = orderClient.get(track);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not OK", SC_OK, statusCode);

        OrderResponseObj orderObj = response.extract().body().as(OrderResponseObj.class);
        assertThat("Should return not Null order ", orderObj, notNullValue());
        assertEquals("Should return not Null order ",
                track,
                orderObj.getOrder().getTrack());

    }

    @Test
    @DisplayName("get order by not exist Track")
    @Description("It should return 404 by not exist track")
    public void getNotExistTest() {
        track = getNotExistingTrack();

        ValidatableResponse response = orderClient.get(track);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not NOT_FOUND", SC_NOT_FOUND, statusCode);

        String message = response.extract().body().path("message").toString();
        assertEquals("message incorrect",
                "Заказ не найден",
                message);

    }

    @Test
    @DisplayName("get order without track number ")
    @Description("It should return 400 ")
    public void getWithoutTrackTest() {

        ValidatableResponse response = orderClient.getWithoutTrack();

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().body().path("message").toString();
        assertEquals("message incorrect",
                "Недостаточно данных для поиска",
                message);

    }


}
