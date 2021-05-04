package com.atisapp.hoorateb.MainFragment.ui.basket;

import java.util.List;

public class TotalOrderModel {
    private int id;
    private String orderId;
    private int totalPrice;
    private int totalCount;
    private String status;
    private String userId;
    private List<ItemOrderModel> itemsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ItemOrderModel> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<ItemOrderModel> itemsList) {
        this.itemsList = itemsList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
