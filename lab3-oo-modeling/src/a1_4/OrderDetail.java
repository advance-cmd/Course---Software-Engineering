package a1_4;

import java.util.Objects;

/** 订单详情：某个商品及其购买数量。 */
public class OrderDetail {
    private final int quantity;
    private final Product product;

    /** 包内可见：只能由 Order.addDetail 创建。 */
    OrderDetail(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("数量必须大于 0");
        }
        this.product = Objects.requireNonNull(product, "订单详情必须对应一个商品");
        this.quantity = quantity;
    }

    /** 小计金额 = 该商品在此数量下的价格。 */
    public double calculateSubTotal() {
        return product.getPriceForQuantity(quantity);
    }

    /** 小计重量 = 单件重量 × 数量。 */
    public double calculateWeight() {
        return product.getWeight() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
