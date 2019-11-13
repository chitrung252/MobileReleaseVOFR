package fpt.com.virtualoutfitroom.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductImage implements Serializable {
    @SerializedName("image_id")
    private int imageId;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("image_type")
    private String imageType;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public ProductImage() {
    }

    public ProductImage(int imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
