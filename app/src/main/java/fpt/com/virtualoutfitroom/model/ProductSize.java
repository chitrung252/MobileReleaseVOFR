package fpt.com.virtualoutfitroom.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSize implements Serializable {
    @SerializedName("SizeID")
    private int sizeId;
    @SerializedName("SizeName")
    private String sizeName;
    @SerializedName("ProductID")
    private int productId;

    public ProductSize() {
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
