package a1_4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 客户：一个客户可以有 0 到多个订单。 */
public class Customer {
    private String name;
    private String contact;
    private String deliveryAddress;
    private boolean active;
    private final List<Order> orders = new ArrayList<>();

    public Customer(String name, String contact, String deliveryAddress, boolean active) {
        this.name = name;
        this.contact = contact;
        this.deliveryAddress = deliveryAddress;
        this.active = active;
    }

    /** 下单。新订单的状态为 CREATE；未激活的客户不能下单。 */
    public Order placeOrder(LocalDate createDate) {
        if (!active) {
            throw new IllegalStateException(name + " 未激活，不能下单");
        }
        Order order = new Order(this, createDate);
        orders.add(order);
        return order;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
