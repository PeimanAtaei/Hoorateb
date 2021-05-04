package com.atisapp.hoorateb.MainFragment.ui.basket;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import java.util.List;

public class BasketMultiViewModel {

    public static final int BASKET_PRODUCT_LIST = 0;
    public static final int BASKET_TOTAL_ORDER = 1;
    public static final int FAVORITE_PRODUCT_LIST = 2;

    private int viewType;
    private TotalOrderModel totalOrderModel;


    public BasketMultiViewModel(int viewType, TotalOrderModel totalOrderModel) {
        this.viewType = viewType;
        this.totalOrderModel = totalOrderModel;
    }


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public TotalOrderModel getTotalOrderModel() {
        return totalOrderModel;
    }

    public void setTotalOrderModel(TotalOrderModel totalOrderModel) {
        this.totalOrderModel = totalOrderModel;
    }


    // Product List Model

    private List<ItemOrderModel> productModelList;

    public BasketMultiViewModel(int viewType, List<ItemOrderModel> productModelList) {
        this.viewType = viewType;
        this.productModelList = productModelList;
    }

    public List<ItemOrderModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ItemOrderModel> productModelList) {
        this.productModelList = productModelList;
    }

    // Favorite List Model

    public BasketMultiViewModel(int viewType, List<ProductModel> favoriteList,boolean nothing) {
        this.viewType = viewType;
        this.favoriteList = favoriteList;
    }

    private List<ProductModel> favoriteList;

    public List<ProductModel> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<ProductModel> favoriteList) {
        this.favoriteList = favoriteList;
    }
}
