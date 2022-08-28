package scooter.model;

import java.util.ArrayList;
import java.util.List;

public class OrderGenerator {
    //No color
    public static Order getDefault() {
        String[] color = {};
        Order order = new Order("Fedor",
                "Fedorov",
                "Yakimanka, 31",
                5,
                "22128506",
                5,
                "2022-09-01",
                "No comment",
                color);
        return order;
    }

    //BLACK
    public static Order getBlack() {
        String[] color = {"BLACK"};
        Order order = new Order("Fedor",
                "Fedorov",
                "Yakimanka, 31",
                5,
                "22128506",
                5,
                "2022-09-01",
                "No comment",
                color);
        return order;
    }


    //GRAY
    public static Order getGray() {
        String[] color = {"GRAY"};
        Order order = new Order("Fedor",
                "Fedorov",
                "Yakimanka, 31",
                5,
                "22128506",
                5,
                "2022-09-01",
                "No comment",
                color);
        return order;
    }

    //BLACK+GRAY
    public static Order getBlackGray() {
        String[] color = {"BLACK", "GRAY"};
        Order order = new Order("Fedor",
                "Fedorov",
                "Yakimanka, 31",
                5,
                "22128506",
                5,
                "2022-09-01",
                "No comment",
                color);
        return order;
    }

}
