package scooter.test.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scooter.model.*;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest {


    public CreateOrderTest(Order order) {
        super.order = order;
    }

    @Parameterized.Parameters(name = "Тест данные:{0}")
    public static Object[][] getOrderData() {
        return new Object[][]{
                {OrderGenerator.getDefault()}, // no colors
                {OrderGenerator.getBlack()},
                {OrderGenerator.getGray()},
                {OrderGenerator.getBlackGray()}
        };
    }


    @Test
    @DisplayName("Order can be created")
    @Description("Order can be created and return track number not Null")
    public void orderCanBeCreatedTest() {
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not CREATED", SC_CREATED, statusCode);

        track = response.extract().path("track");
        assertThat(" creating Order don't return track number", track, notNullValue());

    }
}
