package org.shiloh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
 * 收发应用广播示例 - 收发有序广播
 *
 * @author shiloh
 * @date 2024/9/11 20:42
 */
public class OrderBroadcastActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ACTION_ORDER_BROADCAST = "org.shiloh.ACTION_ORDER_BROADCAST";
    public static final String LOG_TAG = "OrderBroadcast";

    private TextView textView;
    private CheckBox checkBox;
    private OrderReceiverA receiverA;
    private OrderReceiverB receiverB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_broadcast);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.textView = this.findViewById(R.id.tv_order_broadcast_result);
        this.checkBox = this.findViewById(R.id.cb_stop_order_broadcast);
        this.findViewById(R.id.btn_send_order_broadcast).setOnClickListener(this);
    }

    /**
     * 发送有序广播
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_send_order_broadcast != v.getId()) {
            return;
        }

        this.textView.setText(null);
        final Intent intent = new Intent(ACTION_ORDER_BROADCAST);
        // 第二个参数 为 null，表示接收器不需要权限就可以接收
        this.sendOrderedBroadcast(intent, null);
    }

    // region receiver register

    @Override
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    protected void onStart() {
        super.onStart();
        // 注册有序广播接收器，并设置接收器的优先级，优先级越高的接收器越先接收广播
        // 如果多个接收器的优先级相同，则先注册的接收器将先接收到广播
        final IntentFilter intentFilterA = new IntentFilter(ACTION_ORDER_BROADCAST);
        // 设置优先级
        intentFilterA.setPriority(1);
        this.receiverA = new OrderReceiverA();
        this.registerReceiver(this.receiverA, intentFilterA, RECEIVER_EXPORTED);

        final IntentFilter intentFilterB = new IntentFilter(ACTION_ORDER_BROADCAST);
        // 接收器 B 虽然是后面注册的，但是它的优先级比接收器 A 高，因此会先接收到广播
        intentFilterB.setPriority(2);
        this.receiverB = new OrderReceiverB();
        this.registerReceiver(this.receiverB, intentFilterB, RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 注销广播接收器，避免资源浪费
        this.unregisterReceiver(this.receiverA);
        this.unregisterReceiver(this.receiverB);
    }

    // endregion

    /**
     * 有序广播接收器 A
     *
     * @author shiloh
     * @date 2024/9/11 20:54
     */
    public class OrderReceiverA extends BroadcastReceiver {
        /**
         * 广播接收回调
         *
         * @author shiloh
         * @date 2024/9/11 20:44
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(LOG_TAG, "接收器 A 接收到广播，action 为：" + intent.getAction());
            if (!ACTION_ORDER_BROADCAST.equals(intent.getAction())) {
                return;
            }

            final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
            final String desc = String.format("%s%s 接收器 A 收到一个有序广播%n", textView.getText(),
                    time);
            textView.setText(desc);

            if (!checkBox.isChecked()) {
                return;
            }

            // 中断广播
            this.abortBroadcast();
        }
    }

    /**
     * 有序广播接收器 B
     *
     * @author shiloh
     * @date 2024/9/11 20:54
     */
    public class OrderReceiverB extends BroadcastReceiver {
        /**
         * 广播接收回调
         *
         * @author shiloh
         * @date 2024/9/11 20:44
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(LOG_TAG, "接收器 B 接收到广播，action 为：" + intent.getAction());
            if (!ACTION_ORDER_BROADCAST.equals(intent.getAction())) {
                return;
            }

            final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
            final String desc = String.format("%s%s 接收器 B 收到一个有序广播%n", textView.getText(),
                    time);
            textView.setText(desc);

            if (!checkBox.isChecked()) {
                return;
            }

            // 中断广播
            this.abortBroadcast();
        }
    }
}