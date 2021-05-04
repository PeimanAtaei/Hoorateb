package com.atisapp.hoorateb.Payment;

import com.atisapp.hoorateb.MainFragment.ui.basket.ItemOrderModel;
import com.atisapp.hoorateb.MainFragment.ui.category.CategoryModel;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeSliderModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationModel;

import java.util.List;

public class PaymentMultiViewModel {

    public static final int PAYMENT_STEP_VIEW = 0;
    public static final int PAYMENT_TOTAL_ORDER = 1;
    public static final int PAYMENT_PRODUCT_LIST = 2;
    public static final int PAYMENT_PRODUCT_HEADER = 3;


    private int viewType;

    public PaymentMultiViewModel(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    // Step View --------------------------------------------------------------------------------------

    public PaymentMultiViewModel(int viewType, String status) {
        this.viewType = viewType;
        Status = status;
    }

    private String Status;
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    // Product List -----------------------------------------------------------------------------

    private String listTitle;
    private List<ItemOrderModel> productModelList;

    public PaymentMultiViewModel(int viewType, List<ItemOrderModel> productModelList) {
        this.viewType = viewType;
        this.productModelList = productModelList;
    }


    public List<ItemOrderModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ItemOrderModel> productModelList) {
        this.productModelList = productModelList;
    }

    // Total Order -----------------------------------------------------------------------

    public PaymentModel paymentModel;

    public PaymentMultiViewModel(int viewType, PaymentModel paymentModel) {
        this.viewType = viewType;
        this.paymentModel = paymentModel;
    }

    public PaymentModel getPaymentModel() {
        return paymentModel;
    }

    public void setPaymentModel(PaymentModel paymentModel) {
        this.paymentModel = paymentModel;
    }

    // PaymentDetailHeader -------------------------------------------------------------------------

    public String headerTitle;
    public boolean headerBool;

    public PaymentMultiViewModel(int viewType, String headerTitle, boolean headerBool) {
        this.viewType = viewType;
        this.headerTitle = headerTitle;
        this.headerBool = headerBool;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String title) {
        headerTitle = title;
    }


}
