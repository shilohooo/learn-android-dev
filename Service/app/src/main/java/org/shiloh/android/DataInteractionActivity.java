package org.shiloh.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.shiloh.android.service.DataInteractionService;

import java.util.Date;
import java.util.Random;

/**
 * 活动与服务数据交互示例
 *
 * @author shiloh
 * @date 2024/10/13 14:46
 */
public class DataInteractionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = DataInteractionActivity.class.getSimpleName();

    private final Random random = new Random();

    private Intent intent;
    private TextView textView;

    private DataInteractionService.LocalBinder binder;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(LOG_TAG, "onServiceConnected");
            binder = ((DataInteractionService.LocalBinder) service);
            // 通过服务黏合剂（Binder）与服务进行数据交互
            final String res = binder.getNumDesc(random.nextInt(100));
            final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
            textView.setText(String.format("%s - 绑定服务应答：%s", time, res));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(LOG_TAG, "onServiceDisconnected");
            binder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_interaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.textView = this.findViewById(R.id.data_interaction_msg);

        this.findViewById(R.id.btn_start_bind).setOnClickListener(this);
        this.findViewById(R.id.btn_unbind_stop).setOnClickListener(this);

        this.intent = new Intent(this, DataInteractionService.class);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // 启动并绑定服务
        if (v.getId() == R.id.btn_start_bind) {
            final boolean flag = this.bindService(this.intent, serviceConnection,
                    Context.BIND_AUTO_CREATE);
            Log.i(LOG_TAG, "bind flag = " + flag);
            return;
        }

        if (v.getId() != R.id.btn_unbind_stop || this.binder == null) {
            return;
        }

        // 解绑并停止服务
        this.unbindService(this.serviceConnection);
        final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
        this.textView.setText(String.format("%s - 成功解绑服务", time));
    }
}