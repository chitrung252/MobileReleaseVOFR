package fpt.com.virtualoutfitroom.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    @SerializedName("product_id")
    private int id;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("description")
    private String description;

    @SerializedName("product_price")
    private Double productPrice;

    @SerializedName("product_images")
    private List<ProductImage> productImageList;

    @SerializedName("product_sizes")
    private List<ProductSize> productSizeList;

    @SerializedName("actived")
    private boolean actived;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("master_category_id")
    private int masterCategoryId;

    public int getMasterCategoryId() {
        return masterCategoryId;
    }

    public void setMasterCategoryId(int masterCategoryId) {
        this.masterCategoryId = masterCategoryId;
    }

    public Product() {
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public List<ProductSize> getProductSizeList() {
        return productSizeList;
    }

    public void setProductSizeList(List<ProductSize> productSizeList) {
        this.productSizeList = productSizeList;
    }

    public List<ProductImage> getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
