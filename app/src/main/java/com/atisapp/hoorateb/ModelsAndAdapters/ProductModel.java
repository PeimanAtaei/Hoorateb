package com.atisapp.hoorateb.ModelsAndAdapters;

import com.atisapp.hoorateb.MainFragment.ui.category.CategoryModel;

import java.util.List;

public class ProductModel {

    private int    id;
    private String productId;
    private String parentId;
    private String title;
    private String description;
    private int    price;
    private List<CategoryModel> categories;
    private String brand;
    private String brandId;
    private int    disCount;
    private float  rate;
    private int    commentCount;
    private int    viewCount;
    private int    productCount;
    private List<ProductSpecificationModel> specificationModelList;
    private List<ProductSpecificationModel> reviewsModelList;
    private List<String> imagesUrl;
    private String cover;
    private boolean inBasket;
    private boolean inWishList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getDisCount() {
        return disCount;
    }

    public void setDisCount(int disCount) {
        this.disCount = disCount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<ProductSpecificationModel> getSpecificationModelList() {
        return specificationModelList;
    }

    public void setSpecificationModelList(List<ProductSpecificationModel> specificationModelList) {
        this.specificationModelList = specificationModelList;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public boolean isInBasket() {
        return inBasket;
    }

    public void setInBasket(boolean inBasket) {
        this.inBasket = inBasket;
    }

    public boolean isInWishList() {
        return inWishList;
    }

    public void setInWishList(boolean inWishList) {
        this.inWishList = inWishList;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }

    public List<ProductSpecificationModel> getReviewsModelList() {
        return reviewsModelList;
    }

    public void setReviewsModelList(List<ProductSpecificationModel> reviewsModelList) {
        this.reviewsModelList = reviewsModelList;
    }
}
