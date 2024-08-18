package org.shiloh.android.choosephoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.android.R;

import java.io.InputStream;

/**
 * 照片选择示例
 *
 * @author shiloh
 * @date 2024/8/18 22:39
 */
public class ChoosePhotoActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = ChoosePhotoActivity.class.getSimpleName();
    public static final int CHOOSE_PHOTO_REQUEST_CODE = 3;
    public static final String IMAGE_TYPE = "image/*";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_photo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.imageView = this.findViewById(R.id.iv_photo);
        this.findViewById(R.id.btn_choose_photo).setOnClickListener(this);
        // 通过启动器来选择照片
        final ActivityResultLauncher<String> launcher = this.registerForActivityResult(
                new ActivityResultContracts.GetContent(), uri -> {
                    Log.i(LOG_TAG, String.format("照片选择回调：%s", uri));
                    if (uri == null) {
                        return;
                    }

                    final Bitmap bitmap = getAutoZoomImage(this, uri);
                    this.imageView.setImageBitmap(bitmap);
                });
        this.findViewById(R.id.btn_choose_photo_by_register).setOnClickListener(v -> launcher.launch(IMAGE_TYPE));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_choose_photo == v.getId()) {
            // 打开相册
            final Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
            this.startActivityForResult(intent, CHOOSE_PHOTO_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (CHOOSE_PHOTO_REQUEST_CODE != requestCode || resultCode != Activity.RESULT_OK || intent == null) {
            return;
        }

        if (intent.getData() == null) {
            return;
        }

        Log.i(LOG_TAG, String.format("当前选择的照片数据：%s", intent.getData()));
        this.imageView.setImageBitmap(getAutoZoomImage(this, intent.getData()));
    }

    /**
     * 获得自动缩小后的位图对象
     *
     * @param ctx 当前上下文
     * @param uri 照片 URI
     * @author shiloh
     * @date 2024/8/18 23:13
     */
    public static Bitmap getAutoZoomImage(Context ctx, Uri uri) {
        Log.i(LOG_TAG, "getAutoZoomImage uri=" + uri.toString());
        Bitmap zoomBitmap = null;
        // 打开指定uri获得输入流对象
        try (InputStream is = ctx.getContentResolver().openInputStream(uri)) {
            // 从输入流解码得到原始的位图对象
            final Bitmap originBitmap = BitmapFactory.decodeStream(is);
            final int ratio = originBitmap.getWidth() / 2000 + 1;
            // 获得比例缩放之后的位图对象
            zoomBitmap = getScaleBitmap(originBitmap, 1.0 / ratio);
        } catch (Exception e) {
            Log.e(LOG_TAG, "获取自动缩小后的位图对象失败", e);
        }
        return zoomBitmap;
    }

    /**
     * 获得比例缩放之后的位图对象
     *
     * @param bitmap     位图对象
     * @param scaleRatio 缩放比例
     * @author shiloh
     * @date 2024/8/18 23:14
     */
    public static Bitmap getScaleBitmap(Bitmap bitmap, double scaleRatio) {
        final int newWidth = (int) (bitmap.getWidth() * scaleRatio);
        final int newHeight = (int) (bitmap.getHeight() * scaleRatio);
        // 创建并返回缩放后的位图对象
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
    }
}