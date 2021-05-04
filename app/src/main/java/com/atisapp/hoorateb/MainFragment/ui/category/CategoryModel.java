package com.atisapp.hoorateb.MainFragment.ui.category;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;

import java.util.List;

public class CategoryModel {

    private int id;
    private String categoryId;
    private String parentId;
    private String name;
    private String image;
    private String type;
    private List<ProductModel> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ProductModel> getChildren() {
        return children;
    }

    public void setChildren(List<ProductModel> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
