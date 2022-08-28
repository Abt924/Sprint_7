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

public class DeleteCourierTest extends BaseApiTest {
    private Integer courierId;

    @Before
    public void setUp() {
        super.setUp();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(courier);
        courierId = response.extract().path("id");
        couriersIdsToDelete.add(courierId);
    }


    @Test
    @DisplayName("Successful courier delete ")
    @Description("")
    public void deleteCourierTest() {

        ValidatableResponse response = courierClient.delete(courierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not OK", SC_OK, statusCode);

        boolean isDeleted = response.extract().path("ok");
        assertTrue("deleted false ", isDeleted);

    }

    @Test
    @DisplayName("Try to delete not existing courier  ")
    @Description("It Should return not found")
    public void deleteNotExistingCourierTest() throws Exception {

        CourierGenerator courierGenerator = new CourierGenerator();
        courierId = getNotExistingCourier();

        ValidatableResponse response = courierClient.delete(courierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not NOT FOUND", SC_NOT_FOUND, statusCode);

        String message = response.extract().path("message");
        assertEquals(" message incorrect", "Курьера с таким id нет", message);

    }

    @Test
    @DisplayName("delete courier without id in json ")
    @Description("")
    public void deleteCourierWithoutIdJsonTest() {

        ValidatableResponse response = courierClient.deleteWithoutIdJson(courierId);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals(" message incorrect", "Недостаточно данных для удаления курьера", message);

    }


}