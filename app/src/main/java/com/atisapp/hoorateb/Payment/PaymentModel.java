package com.atisapp.hoorateb.Payment;

import com.atisapp.hoorateb.MainFragment.ui.basket.ItemOrderModel;

import java.util.List;

public class PaymentModel {

    private int id;
    private String paymentId;
    private String paymentNumber;
    private String paymentStatus;
    private boolean paymentPaid;
    private int    paymentPrice;
    private String paymentDate;
    private List<ItemOrderModel> orderList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean getPaymentPaid() {
        return paymentPaid;
    }

    public void setPaymentPaid(boolean paymentPaid) {
        this.paymentPaid = paymentPaid;
    }

    public int getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(int paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public List<ItemOrderModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ItemOrderModel> orderList) {
        this.orderList = orderList;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }
}
