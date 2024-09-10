package org.shiloh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.shiloh.appbroadcast.R;

import java.util.Date;

/**
 * 示例：收发标准广播
 *
 * @author shiloh
 * @date 2024/9/10 20:14
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ACTION_STANDARD_BROADCAST = "org.shiloh.ACTION_STANDARD_BROADCAST";
    public static final String LOG_TAG = "StandardBroadcast";

    /**
     * 用于显示广播内容的文本视图控件
     */
    private TextView textView;

    /**
     * 广播接收器
     */
    private StandardReceiver standardReceiver;

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
        this.textView = this.findViewById(R.id.tv_standard_broadcast_result);
        this.findViewById(R.id.btn_send_standard_broadcast).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Log.i(LOG_TAG, "点击发送广播");
        if (R.id.btn_send_standard_broadcast != v.getId()) {
            return;
        }

        // 指定 action，发送标准广播
        // action 可以被广播的接收器用来过滤广播，判断接收到的广播是否是自己要处理的
        Log.i(LOG_TAG, "要发送的广播 Action 为：" + ACTION_STANDARD_BROADCAST);
        final Intent intent = new Intent(ACTION_STANDARD_BROADCAST);
        this.sendBroadcast(intent);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    protected void onStart() {
        super.onStart();
        // 注册广播接收器
        this.standardReceiver = new StandardReceiver();
        // 过滤要接收的广播
        final IntentFilter intentFilter = new IntentFilter(ACTION_STANDARD_BROADCAST);
        Log.i(LOG_TAG, "注册广播接收器");
        this.registerReceiver(this.standardReceiver, intentFilter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销广播接收器，避免资源浪费
        this.unregisterReceiver(this.standardReceiver);
    }

    /**
     * 标准广播接收器
     *
     * @author shiloh
     * @date 2024/9/10 20:23
     */
    private class StandardReceiver extends BroadcastReceiver {
        private String desc = "这里查看标准广播的收听信息";

        /**
         * 接收到广播后的回调处理
         *
         * @param context 上下文对象
         * @param intent  广播意图
         * @author shiloh
         * @date 2024/9/10 20:32
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("StandardReceiver", "接收到广播，Action：" + intent.getAction());
            if (!ACTION_STANDARD_BROADCAST.equals(intent.getAction())) {
                return;
            }

            final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
            this.desc = String.format("%s%n%s 收到一个标准广播", desc, time);
            textView.setText(this.desc);
        }
    }
}