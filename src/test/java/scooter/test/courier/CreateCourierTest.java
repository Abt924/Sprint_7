package scooter.test.courier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;

import scooter.model.*;


public class CreateCourierTest extends BaseApiTest {

    @Before
    public void setUp(){
        super.setUp();
        courier = CourierGenerator.getRandom();
    }

    @Test
    @DisplayName("Courier can be created")
    @Description("Random Courier can be created and successful login should return courier id ")
    public void courierCanBeCreatedTest() {
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not CREATED", SC_CREATED, statusCode);

        boolean isCreated = response.extract().path("ok");
        assertTrue("created false ", isCreated);

        ValidatableResponse loginResponse = courierClient.login(courier);

        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is not OK", SC_OK, loginStatusCode);
        Integer courierId = loginResponse.extract().path("id");
        assertThat("courier login should return id", courierId, notNullValue());

        couriersIdsToDelete.add(courierId);
    }

    @Test
    @DisplayName("Creating a Duplicate Courier not allowed")
    @Description("Creating a Duplicate Courier not allowed, should return CONFLICT")
    public void createDublicateCourierNotAllowedTest() {
        //create
        courierClient.create(courier);
        //login
        ValidatableResponse loginResponse = courierClient.login(courier);
        Integer courierId = loginResponse.extract().path("id");
        couriersIdsToDelete.add(courierId);

        //create dublicate
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not CONFLICT", SC_CONFLICT, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Этот логин уже используется", message);


    }

    @Test
    @DisplayName("Dublicate login Not Allowed")
    @Description("Creating a  courier with Dublicate Login not allowed, it should return CONFLICT")
    public void createDublicateLoginNotAllowedTest() {
        //create
        courierClient.create(courier);
        //login
        ValidatableResponse loginResponse = courierClient.login(courier);
        Integer courierId = loginResponse.extract().path("id");
        couriersIdsToDelete.add(courierId);

        //dublicate login in credentials
        Courier dublicateLoginCourier = CourierGenerator.getDublicateLogin(courier);

        ValidatableResponse response = courierClient.create(dublicateLoginCourier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not CONFLICT", SC_CONFLICT, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Этот логин уже используется", message);

    }

    @Test
    @DisplayName("Creating courier with Empty Login not allowed")
    @Description("Creating  courier with Empty Login not allowed , BAD_REQUEST")
    public void createEmptyLoginNotAllowedTest() {
       Courier emptyLoginCourier = CourierGenerator.getEmptyLogin();

        ValidatableResponse response = courierClient.create(emptyLoginCourier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Недостаточно данных для создания учетной записи", message);

    }

    @Test
    @DisplayName("Creating courier with Empty Password not allowed")
    @Description("Creating  courier with Empty Password not allowed , BAD_REQUEST")
    public void createEmptyPasswordNotAllowedTest() {
        Courier emptyPasswordCourier = CourierGenerator.getEmptyPassword();

        ValidatableResponse response = courierClient.create(emptyPasswordCourier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Creating courier Without login Filed not allowed")
    @Description("Creating courier Without login Filed not allowed, should return BAD_REQUEST")
    public void createNoLoginFieldNotAllowedTest() {
        String json = CourierGenerator.getClientJsonWithoutLoginField();
        ValidatableResponse response = courierClient.create(json);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Creating courier Without password Filed not allowed")
    @Description("Creating courier Without password Filed not allowed, should return BAD_REQUEST")
    public void createNoPasswordFieldNotAllowedTest() {
        String json = CourierGenerator.getClientJsonWithoutPasswordField();
        ValidatableResponse response = courierClient.create(json);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Недостаточно данных для создания учетной записи", message);
    }

}
