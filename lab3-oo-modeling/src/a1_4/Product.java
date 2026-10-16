package a1_4;

/** 商品项。 */
public class Product {
    private final String title;
    /** 单件重量（kg）。 */
    private final double weight;
    private final String description;
    /** 单价（元）。题目没有给出，为实现 getPriceForQuantity 而增加。 */
    private final double price;

    public Product(String title, double weight, String description, double price) {
        this.title = title;
        this.weight = weight;
        this.description = description;
        this.price = price;
    }

    public double getPriceForQuantity(int quantity) {
        return price * quantity;
    }

    public double getWeight() {
        return weight;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
