package fpt.com.virtualoutfitroom.model;

import com.google.ar.core.AugmentedFace;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.io.Serializable;
import java.util.HashMap;

public class ProductRender implements Serializable {
    private Product product;
    private ModelRenderable modelRenderable;

    public ProductRender() {
    }

    public ProductRender(Product product, ModelRenderable modelRenderable) {
        this.product = product;
        this.modelRenderable = modelRenderable;
    }

    public ModelRenderable getModelRenderable() {
        return modelRenderable;
    }

    public void setModelRenderable(ModelRenderable modelRenderable) {
        this.modelRenderable = modelRenderable;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
