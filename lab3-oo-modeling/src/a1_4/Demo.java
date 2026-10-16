package a1_4;

import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {
        Customer customer = new Customer("张三", "138-0000-0000", "某市某区学府路 1 号", true);
        Product keyboard = new Product("机械键盘", 1.20, "87 键，红轴", 299.00);
        Product mouse = new Product("无线鼠标", 0.15, "2.4G 无线", 89.00);
        Product book = new Product("《软件工程》教材", 0.80, "第 8 版", 59.00);

        System.out.println("=== A1-4 网上购物系统 ===");
        System.out.println("客户：" + customer.getName() + "，电话 " + customer.getContact()
                + "，地址 " + customer.getDeliveryAddress() + "，激活 " + customer.isActive());

        // 订单 1：三种商品，支付宝和微信分两笔付清，最终送达
        Order o1 = customer.placeOrder(LocalDate.of(2026, 10, 14));
        o1.addDetail(keyboard, 1);
        o1.addDetail(mouse, 2);
        o1.addDetail(book, 1);
        o1.addPayment(new AliPay(300.00, "zhangsan@example.com"));
        o1.addPayment(new WeixinPay(236.00, "wxid_zhangsan"));
        updateStatusByPayment(o1);
        o1.setStatus(OrderStatus.SHIPPING);
        o1.setStatus(OrderStatus.DELIVERED);

        // 订单 2：信用卡加在线转账
        Order o2 = customer.placeOrder(LocalDate.of(2026, 10, 15));
        o2.addDetail(keyboard, 2);
        o2.addPayment(new Credit(298.00, "6222-0000-0000-0001", "VISA", LocalDate.of(2028, 12, 31)));
        o2.addPayment(new WireTransfer(300.00, "ICBC-0001", "中国工商银行"));
        updateStatusByPayment(o2);

        // 订单 3：现金（货到付款）
        Order o3 = customer.placeOrder(LocalDate.of(2026, 10, 16));
        o3.addDetail(book, 2);
        o3.addPayment(new Cash(118.00, 150.00));
        updateStatusByPayment(o3);

        // 订单 4：下单后未支付即取消，没有任何支付记录
        Order o4 = customer.placeOrder(LocalDate.of(2026, 10, 16));
        o4.addDetail(mouse, 1);
        o4.setStatus(OrderStatus.CANCEL);

        int no = 1;
        for (Order order : customer.getOrders()) {
            printOrder(no++, order);
        }

        customer.setActive(false);
        try {
            customer.placeOrder(LocalDate.of(2026, 10, 16));
        } catch (IllegalStateException e) {
            System.out.println("未激活客户下单：" + e.getMessage());
        }
    }

    /** 已付金额不少于订单总额时，订单从 CREATE 变为 PAID。 */
    private static void updateStatusByPayment(Order order) {
        if (order.getStatus() == OrderStatus.CREATE && paid(order) >= total(order)) {
            order.setStatus(OrderStatus.PAID);
        }
    }

    private static double total(Order order) {
        return order.getDetails().stream().mapToDouble(OrderDetail::calculateSubTotal).sum();
    }

    private static double weight(Order order) {
        return order.getDetails().stream().mapToDouble(OrderDetail::calculateWeight).sum();
    }

    private static double paid(Order order) {
        return order.getPayments().stream().mapToDouble(Payment::getAmount).sum();
    }

    private static void printOrder(int no, Order order) {
        System.out.printf("订单 %d  创建日期 %s  状态 %s%n", no, order.getCreateDate(), order.getStatus());
        for (OrderDetail d : order.getDetails()) {
            System.out.printf("    %s × %d  小计 %.2f 元  重量 %.2f kg%n", d.getProduct().getTitle(),
                    d.getQuantity(), d.calculateSubTotal(), d.calculateWeight());
        }
        System.out.printf("    合计 %.2f 元，总重 %.2f kg%n", total(order), weight(order));
        if (order.getPayments().isEmpty()) {
            System.out.println("    支付：无");
        }
        for (Payment p : order.getPayments()) {
            System.out.println("    支付：" + p);
        }
    }
}
