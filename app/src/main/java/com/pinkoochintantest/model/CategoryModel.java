package com.pinkoochintantest.model;


public class CategoryModel {

    private String id;
    private String name;
    private String description;
    private String categoryImageUrl;


    public CategoryModel(String id, String name, String description, String categoryImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryImageUrl = categoryImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

}