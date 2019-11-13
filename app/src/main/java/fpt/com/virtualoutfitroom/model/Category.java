package fpt.com.virtualoutfitroom.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("master_category_id")
    private int masterCategory;
    @SerializedName("category_image")
    private String categoryImg;
    @SerializedName("actived")
    private boolean active;

    public Category(int categoryId, String categoryName, int masterCategory, String categoryImg) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.masterCategory = masterCategory;
        this.categoryImg = categoryImg;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getMasterCategory() {
        return masterCategory;
    }

    public void setMasterCategory(int masterCategory) {
        this.masterCategory = masterCategory;
    }
}
