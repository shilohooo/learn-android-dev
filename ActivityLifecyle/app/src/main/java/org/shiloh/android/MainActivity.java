package org.shiloh.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * Activity 生命周期
 *
 * @author shiloh
 * @date 2024/8/12 21:12
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = "Activity Lifecycle - Main";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private TextView tvLifecycle;
    private String lifecycleMsg;

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_next_page != v.getId()) {
            return;
        }

        this.startActivity(new Intent(this, AboutActivity.class));
    }

    /**
     * 生命周期：创建 Activity 时触发
     *
     * @author shiloh
     * @date 2024/8/12 21:26
     */
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
        this.tvLifecycle = this.findViewById(R.id.tv_lifecycle);
        this.findViewById(R.id.btn_next_page).setOnClickListener(this);
        refreshLifecycleMsg("onCreate");
    }

    /**
     * 生命周期：启动 Activity 时触发
     *
     * @author shiloh
     * @date 2024/8/12 21:26
     */
    @Override
    protected void onStart() {
        super.onStart();
        this.refreshLifecycleMsg("onStart");
    }

    /**
     * 生命周期：恢复 Activity 时触发
     *
     * @author shiloh
     * @date 2024/8/12 21:27
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.refreshLifecycleMsg("onResume");
    }

    /**
     * 生命周期：暂停 Activity 时触发
     *
     * @author shiloh
     * @date 2024/8/12 21:27
     */
    @Override
    protected void onPause() {
        super.onPause();
        this.refreshLifecycleMsg("onPause");
    }

    /**
     * 生命周期：停止 Activity 时触发
     *
     * @author shiloh
     * @date 2024/8/12 21:28
     */
    @Override
    protected void onStop() {
        super.onStop();
        this.refreshLifecycleMsg("onStop");
    }

    /**
     * 生命周期：销毁 Activity 实例时触发
     *
     * @author shiloh
     * @date 2024/8/12 21:28
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.refreshLifecycleMsg("onDestroy");
    }

    /**
     * 生命周期：重启 Activity 时触发，处于 Stop 状态的 Activity 被重新启动，无须经历 onCreate 的重复
     * 创建过程，而且走 onRestart
     *
     * @author shiloh
     * @date 2024/8/12 21:28
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        this.refreshLifecycleMsg("onRestart");
    }

    /**
     * 生命周期：重用已有的 Activity 实例时触发
     *
     * @author shiloh
     * @date 2024/8/12 21:30
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.refreshLifecycleMsg("onNewIntent");
    }

    /**
     * 刷新生命周期相关信息
     *
     * @param desc 生命周期描述信息
     * @author shiloh
     * @date 2024/8/12 21:24
     */
    private void refreshLifecycleMsg(String desc) {
        final String datetimeStr = DateFormatUtils.format(new Date(), DATETIME_PATTERN);
        this.lifecycleMsg = String.format(
                "%s%s %s %s%n", this.lifecycleMsg, datetimeStr, LOG_TAG, desc
        );
        Log.i(LOG_TAG, desc);
        this.tvLifecycle.setText(this.lifecycleMsg);
    }
}