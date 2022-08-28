package scooter.test.courier;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import scooter.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class BaseApiTest {
    protected CourierClient courierClient;
    protected Courier courier;
    protected Integer courierId;
    protected List<Integer> couriersIdsToDelete;

    protected OrderClient orderClient;
    protected Order order;
    protected int orderId;
    protected int track;
    protected List<Integer> orderTrackList;
    protected List<Integer> orderIdList;


    public BaseApiTest() {
//        setUp();
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();

        couriersIdsToDelete = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (Integer courierId : couriersIdsToDelete) {
            courierClient.delete(courierId);
        }
    }

    //get not existing Courier
    public int getNotExistingCourier() throws Exception {
        int courierId = RandomUtils.nextInt();

        while (isCourierExist(courierId)) {
            courierId = RandomUtils.nextInt();
        }

        return courierId;
    }

    private boolean isCourierExist(int courierId) throws Exception {
        ValidatableResponse response = courierClient.getCountOfCourierOrders(courierId);
        int statusCode = response.extract().statusCode();

        if (statusCode == SC_OK) {
            return true;
        } else if (statusCode == SC_NOT_FOUND) {
            return false;
        } else {
            throw new Exception("Can't define client existence ");
        }
    }

    protected int getRandomExistingTrack() {
        int i = RandomUtils.nextInt(0, orderTrackList.size());
        return orderTrackList.get(i);
    }

    protected int getNotExistingTrack() {
        int trackNotExist = RandomUtils.nextInt();
        while (orderTrackList.contains(trackNotExist)) {
            trackNotExist = RandomUtils.nextInt();
        }
        return trackNotExist;
    }

    protected int getNotExistingOrderId() {
        int notExistId = RandomUtils.nextInt();
        while (orderIdList.contains(notExistId)) {
            notExistId = RandomUtils.nextInt();
        }
        return notExistId;
    }

    protected boolean inDelivery(int track) {
        ValidatableResponse response = orderClient.get(track);
        return response.extract().body().path("order.inDelivery");
    }

    protected int getOrderIdByTrack(int track) {
        ValidatableResponse response = orderClient.get(track);
        return response.extract().body().path("order.id");
    }

    protected int getTrackRandomOrderNotInDelivery() {
        int i = RandomUtils.nextInt(0, orderTrackList.size());
        while (inDelivery(orderTrackList.get(i))) {
            i = RandomUtils.nextInt(0, orderTrackList.size());
        }
        return orderTrackList.get(i);
    }
}
