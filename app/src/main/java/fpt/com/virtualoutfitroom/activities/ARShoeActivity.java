package fpt.com.virtualoutfitroom.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fpt.com.virtualoutfitroom.R;
import fpt.com.virtualoutfitroom.model.Product;
import fpt.com.virtualoutfitroom.model.ProductImage;
import fpt.com.virtualoutfitroom.utils.SpinnerManagement;

public class ARShoeActivity extends AppCompatActivity {
    private static final String TAG = R.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private ArFragment arFragment;
    private ModelRenderable shoeRenderable;
    private KProgressHUD hub;
    private ModelRenderable legRenderable;
    private Product mProduct;
    private FloatingActionButton btnTakePhoto;
    private String URLSFB = "http://107.150.52.213/api-votf/image/20191104181106376304.sfb";
    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_arshoe);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.shoe_fragment);
        btnTakePhoto = (FloatingActionButton)findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(v -> takePhoto());
        getSpinner();
        initialData();
        ModelRenderable.builder()
                .setSource(this, R.raw.leg_asset)
                .build()
                .thenAccept(renderable -> {
                    legRenderable = renderable;
                    legRenderable.setRenderPriority(Renderable.RENDER_PRIORITY_LAST);
                    legRenderable.setShadowCaster(false);
                    legRenderable.setShadowCaster(false);
                })
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the invisible leg", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, Uri.parse(URLSFB))
                .build()
                .thenAccept(renderable -> {
                    hub.dismiss();
                    shoeRenderable = renderable;
                    shoeRenderable.setRenderPriority(Renderable.RENDER_PRIORITY_LAST);
                    shoeRenderable.setShadowCaster(false);
                    shoeRenderable.setShadowCaster(false);
                })
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load shoe model", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (shoeRenderable == null) {
                        return;
                    }
                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable shoe and add it to the anchor.
                    TransformableNode shoeNode = new TransformableNode(arFragment.getTransformationSystem());
                    shoeNode.setParent(anchorNode);
                    shoeNode.setRenderable(shoeRenderable);
                    shoeNode.select();

                    // Create the transformable leg and add it to the shoe.
                    TransformableNode legNode = new TransformableNode(arFragment.getTransformationSystem());
                    legNode.setParent(shoeNode);
                    legNode.setRenderable(legRenderable);
                });
    }

    public void getSpinner(){
        hub = SpinnerManagement.getSpinner(this);
    }

    public void initialData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        mProduct = (Product) bundle.getSerializable("PRODUCT");
        int i = 0;
        for (ProductImage image : mProduct.getProductImageList()) {
            if (image.getImageType().equals("sfb")) {
                URLSFB = mProduct.getProductImageList().get(i).getImageUrl();
                break;
            }
            i++;
        }
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
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
                Uri scannerUri = Uri.fromFile(photoFile);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, scannerUri));
                snackbar.setAction("Open in photo", v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(photoURI, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivity(intent);
                });
                snackbar.setActionTextColor(Color.parseColor("#3F51B5"));
                snackbar.show();
            } else {
                Toast toast = Toast.makeText(this, "Failed to copy pixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }
}
