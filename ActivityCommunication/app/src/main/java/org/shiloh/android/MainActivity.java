package org.shiloh.android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 隐式 Intent：拨打电话和发送短信示例
 *
 * @author shiloh
 * @date 2024/8/17 22:38
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CHINA_UNION_PHONE_NUMBER = "10010";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.findViewById(R.id.btn_call).setOnClickListener(this);
        this.findViewById(R.id.btn_sms).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        final Intent intent = new Intent();
        if (R.id.btn_call == v.getId()) {
            // 拨号
            // ACTION_CALL 权限判断 https://blog.csdn.net/weixin_42910064/article/details/89219002
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // 没权限的话要请求一下拨号权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
            intent.setAction(Intent.ACTION_CALL);
            final Uri uri = Uri.parse(String.format("tel:%s", CHINA_UNION_PHONE_NUMBER));
            intent.setData(uri);
            this.startActivity(intent);
            return;
        }

        if (R.id.btn_sms == v.getId()) {
            // 发短信，也判断一下权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                // 没权限的话要请求一下拨号权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            intent.setAction(Intent.ACTION_SENDTO);
            final Uri uri = Uri.parse(String.format("smsto:%s", CHINA_UNION_PHONE_NUMBER));
            intent.setData(uri);
            this.startActivity(intent);
        }
    }
}