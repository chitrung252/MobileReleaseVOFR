package fpt.com.virtualoutfitroom.presenter.category;

import android.content.Context;

import java.util.List;

import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.repository.VofrImpl;
import fpt.com.virtualoutfitroom.repository.VofrRepository;
import fpt.com.virtualoutfitroom.views.CategoryView;
import fpt.com.virtualoutfitroom.views.HomeView;
import fpt.com.virtualoutfitroom.webservice.CallBackData;

public class CategoryPresenter {
    private Context mContext;
    private VofrRepository mRepository;
    private CategoryView mView;

    public CategoryPresenter(Context mContext, CategoryView mView) {
        this.mContext = mContext;
        this.mRepository = new VofrImpl();
        this.mView = mView;
    }
    public void getListCategory(){
        mRepository.getListCategory(mContext, new CallBackData<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                mView.showListCategory(categoryList);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
    public void getListSubCategory(int categoryId){
        mRepository.getListSubCategory(mContext, categoryId, new CallBackData<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categoryList) {
                mView.showListCategory(categoryList);
            }

            @Override
            public void onFail(String message) {

            }
        });

    }
}
