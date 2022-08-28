package scooter.model;

public class OrderResponseObj {
    public OrderExtended order;

    public OrderResponseObj(OrderExtended order) {
        this.order = order;
    }

    public OrderResponseObj() {
    }

    public OrderExtended getOrder() {
        return order;
    }

    public void setOrder(OrderExtended order) {
        this.order = order;
    }
}
