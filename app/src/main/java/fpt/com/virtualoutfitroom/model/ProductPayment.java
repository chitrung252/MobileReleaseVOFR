package fpt.com.virtualoutfitroom.model;

public class ProductPayment {
    private String productUrl;
    private String prductName;
    private double priceProduct;
    private int quantiryProduct;

    public ProductPayment(String productUrl, String prductName, double priceProduct, int quantiryProduct) {
        this.productUrl = productUrl;
        this.prductName = prductName;
        this.priceProduct = priceProduct;
        this.quantiryProduct = quantiryProduct;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getPrductName() {
        return prductName;
    }

    public void setPrductName(String prductName) {
        this.prductName = prductName;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getQuantiryProduct() {
        return quantiryProduct;
    }

    public void setQuantiryProduct(int quantiryProduct) {
        this.quantiryProduct = quantiryProduct;
    }
}
