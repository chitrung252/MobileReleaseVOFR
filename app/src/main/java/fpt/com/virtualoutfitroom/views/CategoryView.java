package fpt.com.virtualoutfitroom.views;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Category;

public interface  CategoryView extends BaseView {
    void showListCategory(List<Category> categoryList);
}
