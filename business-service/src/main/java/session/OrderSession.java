package session;

import model.Ordered;

import java.util.ArrayList;
import java.util.List;

public class OrderSession {
    private Long quantity;

    private Long shippingFee;

    private Double vat;

    private List<Ordered> orders;

    public OrderSession() {
        quantity = 0L;
        shippingFee = 5L;
        vat = 12.5;
        orders = new ArrayList<>();
    }

    public void add(Ordered Ordered) {
        quantity++;
        orders.add(Ordered);
    }


    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public List<Ordered> getOrders() {
        return orders;
    }

    public void setOrders(List<Ordered> orders) {
        this.quantity = 0L;
        this.orders = orders;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Long shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Long getTotalPrice() {

        Long total = 0L;

        for (Ordered order : orders) {
            total += order.getQuantity() * order.getToy().getPrice();
        }

        return total;
    }

    public Double getVatFee() {

        Double price = getTotalPrice() * vat / 100;

        return (double) Math.round(price * 10) / 10;
    }

    public Double getFinalTotal() {
        return getTotalPrice() + shippingFee + getVatFee();
    }

    public void setRemoveId() {

        Long temp = 0L;

        for (Ordered order : orders) {
            order.setRemoveId(temp);
            temp++;
        }

    }

    public void removeOrder(Long removeId) {

        for (Ordered order : orders) {
            if (order.getRemoveId() == removeId) {
                orders.remove(order);
                quantity--;
                break;
            }
        }

    }

    public boolean containOrdered(Ordered ordered) {

        for (Ordered o : orders) {
            if (o.getToy().getId() == ordered.getToy().getId()) {
                return true;
            }
        }

        return false;
    }
}
