package org.shiloh.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.android.service.MyNormalService;

/**
 * Service(服务)生命周期
 *
 * @author shiloh
 * @date 2024/9/17 12:27
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;

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
        this.findViewById(R.id.btn_start).setOnClickListener(this);
        this.findViewById(R.id.btn_stop).setOnClickListener(this);
        // 绑定服务
        this.intent = new Intent(this, MyNormalService.class);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_start == v.getId()) {
            // 启动服务
            this.startService(this.intent);
            Toast.makeText(this, "服务启动成功", Toast.LENGTH_SHORT).show();
            return;
        }

        if (R.id.btn_stop == v.getId()) {
            // 停止服务
            this.stopService(this.intent);
            Toast.makeText(this, "服务停止成功", Toast.LENGTH_SHORT).show();
        }
    }
}