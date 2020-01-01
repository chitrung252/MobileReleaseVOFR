/*
 * Copyright 2019 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fpt.com.virtualoutfitroom.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.AugmentedFaceNode;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.dialog.BottomSheetCateAr;
import fpt.com.virtualoutfitroom.fragments.ArProductFragment;
import fpt.com.virtualoutfitroom.fragments.FaceArFragment;
import fpt.com.virtualoutfitroom.model.Category;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductRender;
import fpt.com.virtualoutfitroom.presenter.category.CategoryPresenter;
import fpt.com.virtualoutfitroom.utils.GetAbsolutePathFile;
import fpt.com.virtualoutfitroom.utils.RefineImage;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;
import fpt.com.virtualoutfitroom.utils.UrlHelper;
import fpt.com.virtualoutfitroom.views.CategoryView;

/**
 * This is an example activity that uses the Sceneform UX package to make common Augmented Faces
 * tasks easier.
 */
public class AugmentedFacesActivity extends BaseActivity implements View.OnClickListener, CategoryView, ArProductFragment.FragmentListener, BottomSheetCateAr.FragmentCateListener {
    private static final String TAG = AugmentedFacesActivity.class.getSimpleName();

    private static final double MIN_OPENGL_VERSION = 3.0;

    private FaceArFragment arFragment;
    private ModelRenderable renderable;
    private final HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();
    private HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMapOld = new HashMap<>();
    private Product mProduct;
    private Button btnTakePhoto;
    private ImageView mImgBack;
    private TextView mTxtProductName;
    private KProgressHUD hud;
    private ImageView mImgArrow;
    private ImageView mImgAddToCart;
    boolean isUp;
    boolean isUpPro;
    private LinearLayout mLnlFunction;
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    private CategoryPresenter mCategoryPrsenter;
    private List<Category> mListCate;
    private boolean isCheckAll = true;
    private ImageView mImgCate;
    private TextView mTxtCateName;
    private LinearLayout mLnlProduct;
    private LinearLayout mLnlDynamic;
    private LinearLayout mLnlEditProChoose;
    private LinearLayout mLnlEditPro;
    private List<Product> mListProduct;
    private BottomSheetCateAr bottomSheet;
    private List<ProductRender> productRenders;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_face_mesh);
        initialView();
        getSpinner();
        initialData();
        initialDataProView();
        // Load the face regions renderable.
        // This is a skinned model that renders 3D objects mapped to the regions of the augmented face.
    }

    public void refreshModel() {
        if (renderable != null) {
            renderable = null;
        }
        if (faceNodeMap != null) {
            faceNodeMap.clear();
        }
    }

    public void destroyView() {
        Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter = faceNodeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
            AugmentedFaceNode faceNode = entry.getValue();
            faceNode.setParent(null);
            iter.remove();
        }
    }

    public void generateModel(Product product) {
        refreshModel();
        String url = RefineImage.getUrlImage(product.getProductImageList(), "sfb");
        String mGalleryPath = GetAbsolutePathFile.getRootDirPath(this);
        //using file
//        final File file = new File(mGalleryPath, UrlHelper.getFileNameFromUrl(URLSFB));
        String fileUrl = mGalleryPath + "/" + UrlHelper.getFileNameFromUrl(url);
        ModelRenderable.builder()
                .setSource(this, Uri.parse(fileUrl))
                .build()
                .thenAccept(
                        modelRenderable -> {
                            renderable = modelRenderable;

                            modelRenderable.setShadowCaster(false);
                            modelRenderable.setShadowReceiver(false);
                            hud.dismiss();
                        });
        ArSceneView sceneView = arFragment.getArSceneView();
        // This is important to make sure that the camera stream renders first so that
        // the face mesh occlusion works correctly.
        sceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        Scene scene = sceneView.getScene();
        scene.addOnUpdateListener(
                (FrameTime frameTime) -> {
                    if (renderable == null) {
                        return;
                    }
                    Collection<AugmentedFace> faceList =
                            sceneView.getSession().getAllTrackables(AugmentedFace.class);

                    // Make new AugmentedFaceNodes for any new faces.
                    for (AugmentedFace face : faceList) {
                        for (ProductRender item : productRenders) {
                            AugmentedFaceNode faceNode = new AugmentedFaceNode(face);
                            faceNode.setParent(scene);
                            faceNode.setFaceRegionsRenderable(item.getModelRenderable());
                            faceNodeMap.put(face, faceNode);
                        }

                    }

                    // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
                    Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter = faceNodeMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
                        AugmentedFace face = entry.getKey();
                        if (face.getTrackingState() == TrackingState.STOPPED) {
                            AugmentedFaceNode faceNode = entry.getValue();
                            faceNode.setParent(null);
                            iter.remove();
                        }
                    }
                });
    }

    public void getSpinner() {
        hud = SpinnerManagement.getSpinner(this);
    }

    public void initialView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mTxtProductName = findViewById(R.id.txt_product_name);
        mImgBack = findViewById(R.id.img_back_to_previous);
        mImgBack.setOnClickListener(this);
        btnTakePhoto = (Button) findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(v -> takePhoto());
        arFragment = (FaceArFragment) getSupportFragmentManager().findFragmentById(R.id.face_fragment);
        mImgArrow = findViewById(R.id.img_arrow);
        mImgArrow.setOnClickListener(this);
        mImgAddToCart = findViewById(R.id.img_add_to_cart);
        mImgAddToCart.setOnClickListener(this);
        mLnlFunction = findViewById(R.id.lnl_function);
        mImgCate = findViewById(R.id.img_category);
        mImgCate.setOnClickListener(this);
        mTxtCateName = findViewById(R.id.txt_category_name);
        mViewPager = findViewById(R.id.view_pager_category);
        mSmartTabLayout = findViewById(R.id.smart_tab_category);
        mLnlProduct = findViewById(R.id.lnl_list_product);
        mLnlProduct.setVisibility(View.GONE);
        mLnlDynamic = findViewById(R.id.lnl_dynamic_text);
        mListProduct = new ArrayList<>();
        mLnlEditProChoose = findViewById(R.id.lnl_edit_product_choose);
        mLnlEditProChoose.setOnClickListener(this);
        mLnlEditPro = findViewById(R.id.lnl_edit_product);
        mLnlEditPro.setVisibility(View.GONE);
        productRenders = new ArrayList<>();
    }

    public void checkListTVProduct(Product product) {
        int i = 0;
        for (Product item : mListProduct) {
            if (item.getMasterCategoryId() == product.getMasterCategoryId()) {
                mListProduct.set(i, product);
                genrateImageView(mListProduct);
                return;
            }
            i++;
        }
        mListProduct.add(product);
        genrateImageView(mListProduct);
    }

    public void genrateImageView(List<Product> mListProduct) {
        mLnlDynamic.removeAllViews();
        for (int i = 0; i < mListProduct.size(); i++) {
            String img = RefineImage.getUrlImage(mListProduct.get(i).getProductImageList(), "img");
            ImageView iv = new ImageView(this); // Prepare textview object programmatically
            if (img != null) {
                Picasso.get().load(img).into(iv);
            } else {
                iv.setImageResource(R.mipmap.glasses);
            }
            iv.setId(i + 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(10, 0, 10, 0);
            iv.setLayoutParams(params);
            mLnlDynamic.addView(iv); // Add to your ViewGroup using this method
        }
    }

    public void initialData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mProduct = (Product) bundle.getSerializable("PRODUCT");
        checkListTVProduct(mProduct);
        generateModel(mProduct);
        isUp = false;
        mCategoryPrsenter = new CategoryPresenter(this, this);
        mCategoryPrsenter.getListCategory();
    }

    private void initialDataProView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    }

    private void initialBottomSheet() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("LISTCATE", (Serializable) mListCate);
        bottomSheet = new BottomSheetCateAr();
        bottomSheet.setArguments(bundle);
    }

    private void showDialogBottom() {
        bottomSheet.show(getSupportFragmentManager(), "Bottom Sheet");
    }

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (ArCoreApk.getInstance().checkAvailability(activity)
                == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
            Log.e(TAG, "Augmented Faces requires ARCore.");
            Toast.makeText(activity, "Augmented Faces requires ARCore", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    private String generateFilename() {
        String date = new SimpleDateFormat("yyyyMMddHHMMmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "vofr/" + date + "_photo.png";
    }

    private void saveBitmapToDisk(Bitmap bitmap, String filename) throws IOException {
        File out = new File(filename);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }

        try (
                FileOutputStream outputStream = new FileOutputStream(filename);
                ByteArrayOutputStream outputData = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData);
            outputData.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException ex) {
            throw new IOException("Failed to save bitmap to disk", ex);
        }
    }

    private void takePhoto() {
        final String filename = generateFilename();
        ArSceneView view = arFragment.getArSceneView();

        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");

        handlerThread.start();
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename);
                } catch (IOException ex) {
                    Toast toast = Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Photo saved", Snackbar.LENGTH_LONG);
                File photoFile = new File(filename);
                Uri photoURI = FileProvider.getUriForFile(this, this.getPackageName() + ".fileprovider", photoFile);

                snackbar.setAction("Open in photo", v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(photoURI, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivity(intent);
                });
                snackbar.setActionTextColor(Color.parseColor("#3F51B5"));
                snackbar.show();
                Uri scannerUri = Uri.fromFile(photoFile);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, scannerUri));
            } else {
                Toast toast = Toast.makeText(this, "Failed to copy pixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_to_previous:
                finish();
                break;
            case R.id.img_arrow:
                changeLayout();
                break;
            case R.id.img_add_to_cart:
                addToCart();
                break;
            case R.id.img_category:
                showDialogBottom();
                break;
            case R.id.lnl_edit_product_choose:
                destroyView();
//                showPopUpEditProduct();
                break;
        }
    }

    private void showPopUpEditProduct() {
        if (isUpPro) {
            mLnlEditPro.setVisibility(View.GONE);
        } else {
            mLnlEditPro.setVisibility(View.VISIBLE);
        }
        isUpPro = !isUpPro;
    }

    private void changeLayout() {
        if (isUp) {
            slideDown();
            mLnlProduct.setVisibility(View.GONE);
        } else {
            slideUp();
            mImgArrow.setClickable(false);
            mLnlProduct.setVisibility(View.VISIBLE);
            mLnlProduct.setAlpha(0);
            mLnlProduct.animate().setDuration(500).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLnlProduct.setVisibility(View.VISIBLE);
                    mImgArrow.setClickable(true);
                }
            });
        }
        isUp = !isUp;
    }

    private void addToCart() {

    }

    private void slideDown() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(mLnlFunction, "translationY", 0f);
        animation.setDuration(200);
        animation.start();
        mImgArrow.setImageResource(R.drawable.ic_up_arrow);
    }

    private void slideUp() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(mLnlFunction, "translationY", -300f);
        animation.setDuration(200);
        animation.start();
        mImgArrow.setImageResource(R.drawable.ic_down_arrow);
    }

    private void getCurrentCategory(List<Category> list, int curCateId) {
        for (Category item : list) {
            if (item.getCategoryId() == curCateId) {
                Picasso.get().load(item.getCategoryImg()).into(mImgCate);
                mTxtCateName.setText(item.getCategoryName());
            }
        }
    }

    private void getCategoryData(List<Category> categoryList) {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(getApplicationContext());
        for (int i = 0; i < categoryList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("SUBCATEGORY", categoryList.get(i));
            creator.add(categoryList.get(i).getCategoryName(), ArProductFragment.class, bundle);
        }
        //FragmentPagerItemAdapter mApdapter = null;

//        mAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());
//        FragmentPagerItems dynamicFragment = new FragmentPagerItems(this);
//        for (int i = 0; i < categoryList.size(); i++) {
//            Bundle args = new Bundle();
//            args.putSerializable( "SUBCATEGORY", categoryList.get(i) );
//            dynamicFragment.add( FragmentPagerItem.of(String.valueOf( categoryList.get(i).getCategoryName() ), ArProductFragment.class,args));
//        }
        //mAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), dynamicFragment);

        FragmentStatePagerItemAdapter mAdapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), creator.create()) {
            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }
        };

        mViewPager.setOffscreenPageLimit(categoryList.size());
        mViewPager.setAdapter(mAdapter);
        //set viewPager for SmartTabLayout
        mSmartTabLayout.setViewPager(mViewPager);
        mSmartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showListCategory(List<Category> categoryList) {
        if (categoryList != null) {
            for (Category item : categoryList) {
                if (item.getCategoryId() == 4) {
                    categoryList.remove(item);
                    break;
                }
            }
            if (isCheckAll) {
                mListCate = categoryList;
                initialBottomSheet();
                mCategoryPrsenter.getListSubCategory(mProduct.getMasterCategoryId());
                isCheckAll = false;
                getCurrentCategory(categoryList, mProduct.getMasterCategoryId());
            } else {
                getCategoryData(categoryList);
                isCheckAll = true;
            }
        }
    }

    public void checkExistModel3D(Product product) {
        ProductRender proTemp = null;
        for (ProductRender proRender : productRenders) {
            if (proRender.getProduct().getMasterCategoryId() == product.getMasterCategoryId()) {
                Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter = faceNodeMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
                    AugmentedFaceNode faceNode = entry.getValue();
                    faceNode.setParent(null);
                    iter.remove();
                }
                proTemp = proRender;
            }
        }
        productRenders.remove(proTemp);
    }

    private boolean checkExistProductView(Product product) {
        for (Product item : mListProduct) {
            if (item.getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }

    public void renderModel3D(Product product) {
        if (productRenders != null) {
            checkExistModel3D(product);
            generateModel(product);
        }
    }

    @Override
    public void showError(String message) {

    }

    //chu y ham nay
    @Override
    public void sendData(Product product) {
        if (!checkExistProductView(product)) {
            getSpinner();
            renderModel3D(product);
        }
        checkListTVProduct(product);
    }

    @Override
    public void sendDataCate(List<Category> listCate, Category category) {
        getCurrentCategory(listCate, category.getCategoryId());
        mCategoryPrsenter.getListSubCategory(category.getCategoryId());
        isCheckAll = false;
    }
}
