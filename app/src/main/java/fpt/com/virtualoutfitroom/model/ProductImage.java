package fpt.com.virtualoutfitroom.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductImage implements Serializable {
    @SerializedName("ImageID")
    private int imageId;
    @SerializedName("ImageUrl")
    private String imageUrl;

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
