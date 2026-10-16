package a1_4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 订单：属于 1 个客户，由 1 到多条订单详情组成，可以有 0 到多笔支付。 */
public class Order {
    private final LocalDate createDate;
    private OrderStatus status = OrderStatus.CREATE;
    private final Customer customer;
    private final List<OrderDetail> details = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();

    /** 包内可见：订单只能通过 Customer.placeOrder 创建。 */
    Order(Customer customer, LocalDate createDate) {
        this.customer = customer;
        this.createDate = createDate;
    }

    /** 组合：订单详情由订单创建，随订单一起删除。 */
    public OrderDetail addDetail(Product product, int quantity) {
        OrderDetail detail = new OrderDetail(product, quantity);
        details.add(detail);
        return detail;
    }

    /** 一个订单可以分多笔、用不同方式支付。 */
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderDetail> getDetails() {
        return Collections.unmodifiableList(details);
    }

    public List<Payment> getPayments() {
        return Collections.unmodifiableList(payments);
    }
}
