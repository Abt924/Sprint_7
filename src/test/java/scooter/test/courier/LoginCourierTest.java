package scooter.test.courier;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import io.qameta.allure.junit4.DisplayName; // импорт DisplayName
import io.qameta.allure.Description; // импорт Description

import scooter.model.Courier;
import scooter.model.CourierGenerator;

public class LoginCourierTest extends BaseApiTest {

    @Before
    public void setUp() {
        super.setUp();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);
    }

    @Test
    @DisplayName("courier Can login")
    @Description("registered courier can login")
    public void courierCanLoginTest(){
        ValidatableResponse response = courierClient.login(courier);

        int loginStatusCode = response.extract().statusCode();
        assertEquals("Status code is not OK", SC_OK, loginStatusCode);

        Integer courierId = response.extract().path("id");
        assertThat("courier login should return id", courierId, notNullValue());

        couriersIdsToDelete.add(courierId);
    }


    @Test
    @DisplayName("login Without login not allowed")
    @Description("login Without login field not allowed ")
    public void loginWithoutLoginNotAllowedTest(){
        ValidatableResponse response = courierClient.login(courier);
        Integer courierId = response.extract().path("id");
        couriersIdsToDelete.add(courierId);


        String noLoginJson = CourierGenerator.getCredentialsJsonWithoutLoginField(courier);
        response = courierClient.login(noLoginJson);

        int statusCode = response.extract().statusCode();
        assertEquals("Login "+ noLoginJson + "\nStatus code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Недостаточно данных для входа", message);

    }

    @Test
    @DisplayName("login Without password not allowed")
    @Description("login Without password field not allowed ")
    public void loginWithoutPasswordNotAllowedTest(){
        ValidatableResponse response = courierClient.login(courier);
        Integer courierId = response.extract().path("id");
        couriersIdsToDelete.add(courierId);


        String noPasswordJson = CourierGenerator.getCredentialsJsonWithoutPasswordField(courier);
        response = courierClient.login(noPasswordJson);

        int statusCode = response.extract().statusCode();
        assertEquals("Credentials json "+ noPasswordJson + "\nStatus code is not BAD_REQUEST", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Недостаточно данных для входа", message);

    }

    @Test
    @DisplayName("login not registered Courier forbidden")
    @Description("login not registered Courier forbidden ")
    public void notRegisteredCourierLoginTest(){
        ValidatableResponse response = courierClient.login(courier);
        Integer courierId = response.extract().path("id");
        couriersIdsToDelete.add(courierId);

        Courier notRegisteredCourier = CourierGenerator.getRandom();
        response = courierClient.login(notRegisteredCourier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not NOT_FOUND", SC_NOT_FOUND, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Учетная запись не найдена", message);

    }

    @Test
    @DisplayName("wrong login Courier login forbidden")
    @Description("wrong login Courier login forbidden")
    public void wrongLoginCourierLoginTest(){
        ValidatableResponse response = courierClient.login(courier);
        Integer courierId = response.extract().path("id");
        couriersIdsToDelete.add(courierId);

        Courier wrongLoginCourier = CourierGenerator.getWrongLogin(courier);
        response = courierClient.login(wrongLoginCourier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not NOT_FOUND", SC_NOT_FOUND, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Учетная запись не найдена", message);

    }

    @Test
    @DisplayName("wrong password Courier login forbidden")
    @Description("wrong password Courier login forbidden")
    public void wrongPasswordCourierLoginTest(){
        ValidatableResponse response = courierClient.login(courier);
        Integer courierId = response.extract().path("id");
        couriersIdsToDelete.add(courierId);

        Courier wrongPasswordCourier = CourierGenerator.getWrongPassword(courier);
        response = courierClient.login(wrongPasswordCourier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is not NOT_FOUND", SC_NOT_FOUND, statusCode);

        String message = response.extract().path("message");
        assertEquals("Error message is incorrect", "Учетная запись не найдена", message);

    }


}
