package scooter.test.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetOrdersTest extends BaseApiTest {

    @Test
    @DisplayName("get All Orders")
    @Description("get All Orders return not null orders list")
    public void getAllOrdersTest() {

        ValidatableResponse response = orderClient.getAll();

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not OK", SC_OK, statusCode);

        orderTrackList = response.extract().body().path("orders.track");
        assertThat("Order list should be not null", orderTrackList, notNullValue());
        assertTrue("Order list size should be more than 0", orderTrackList.size() > 0);

    }
}
