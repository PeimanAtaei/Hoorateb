package com.atisapp.hoorateb.MainFragment.ui.home;

import com.atisapp.hoorateb.MainFragment.ui.category.CategoryModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationModel;

import java.util.List;

public class HomeMultiViewModel {

    public static final int MAIN_SLIDER = 0;
    public static final int MAIN_HORIZONTAL_LIST = 1;
    public static final int MAIN_VERTICAL_LIST = 2;
    public static final int PRODUCT_DETAIL_HEADER = 3;
    public static final int PRODUCT_DETAIL_SPECIFICATION = 4;
    public static final int CATEGORY_HORIZONTAL_LIST = 5;
    public static final int PRODUCT_DETAIL_EMPTY = 6;
    public static final int PRODUCT_DETAIL_REVIEWS = 7;


    private int viewType;

    public HomeMultiViewModel(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    // Slider --------------------------------------------------------------------------------------

    private List<HomeSliderModel> sliderModelList;

    public HomeMultiViewModel(int viewType, List<HomeSliderModel> sliderModelList) {
        this.viewType = viewType;
        this.sliderModelList = sliderModelList;
    }

    public List<HomeSliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<HomeSliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    // Horizontal List -----------------------------------------------------------------------------

    private String listTitle, type , categoryId;
    private List<ProductModel> productModelList;

    public HomeMultiViewModel(int viewType, String listTitle, String type,String categoryId, List<ProductModel> productModelList) {
        this.viewType = viewType;
        this.listTitle = listTitle;
        this.productModelList = productModelList;
        this.type = type;
        this.categoryId = categoryId;
    }


    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    // Product Detail header -----------------------------------------------------------------------

    public ProductModel productModel;

    public HomeMultiViewModel(int viewType, ProductModel productModel) {
        this.viewType = viewType;
        this.productModel = productModel;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }


    // Product Detail Specification header -----------------------------------------------------------------------


    public String listName;
    public List<ProductSpecificationModel> specificationModels;

    public HomeMultiViewModel(int viewType, String listName, List<ProductSpecificationModel> specificationModels, boolean noThing) {
        this.viewType = viewType;
        this.listName = listName;
        this.specificationModels = specificationModels;
    }

    public List<ProductSpecificationModel> getSpecificationModels() {
        return specificationModels;
    }

    public void setSpecificationModels(List<ProductSpecificationModel> specificationModels) {
        this.specificationModels = specificationModels;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }


    // Product Detail Reviews header -----------------------------------------------------------------------

    public List<ProductSpecificationModel> reviewsModel;
    public int nothing;

    public HomeMultiViewModel(int viewType, List<ProductSpecificationModel> reviewsModel, int nothing) {
        this.viewType = viewType;
        this.reviewsModel = reviewsModel;
    }

    public List<ProductSpecificationModel> getReviews() {
        return reviewsModel;
    }

    public void setReviews(List<ProductSpecificationModel> reviewsModel) {
        this.reviewsModel = reviewsModel;
    }
// CATEGORIES ----------------------------------------------------------------------------------

    public HomeMultiViewModel(int viewType, CategoryModel categoryModel, boolean nothing) {
        this.viewType = viewType;
        this.categoryModel = categoryModel;
    }

    private CategoryModel categoryModel;

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModelList(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    // EMPTY --------------------------------------------------------------------------------------

    private boolean Empty;

    public HomeMultiViewModel(int viewType, boolean setEmpty) {
        this.viewType = viewType;
        this.Empty = setEmpty;
    }

    public boolean isEmpty() {
        return Empty;
    }

    public void setEmpty(boolean empty) {
        Empty = empty;
    }



}