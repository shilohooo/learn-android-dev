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
import org.shiloh.android.service.DelayBindService;

import java.util.Date;

/**
 * 服务的绑定与解绑 - 延迟绑定
 *
 * @author shiloh
 * @date 2024/9/17 13:16
 */
public class DelayBindActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = "DelayBindActivity";

    private Intent intent;

    private DelayBindService delayBindService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            delayBindService = ((DelayBindService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            delayBindService = null;
            Log.i(LOG_TAG, "onServiceDisconnected");
        }
    };

    private static TextView textView;
    private static String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delay_bind);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = this.findViewById(R.id.tv_delay_bind_msg);
        msg = "";

        this.findViewById(R.id.btn_delay_start).setOnClickListener(this);
        this.findViewById(R.id.btn_delay_bind).setOnClickListener(this);
        this.findViewById(R.id.btn_delay_unbind).setOnClickListener(this);
        this.findViewById(R.id.btn_delay_stop).setOnClickListener(this);

        this.intent = new Intent(this, DelayBindService.class);
    }

    public static void showText(String text) {
        if (textView == null) {
            return;
        }


        final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
        msg = String.format("%s%s %s%n", msg, time, text);
        textView.setText(msg);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // 启动服务
        if (v.getId() == R.id.btn_delay_start) {
            this.startService(this.intent);
            return;
        }
        // 绑定服务
        if (v.getId() == R.id.btn_delay_bind) {
            final boolean flag = this.bindService(this.intent, this.serviceConnection,
                    Context.BIND_AUTO_CREATE);
            Log.i(LOG_TAG, "bind flag = " + flag);
            return;
        }
        // 解绑服务
        if (v.getId() == R.id.btn_delay_unbind && this.delayBindService != null) {
            this.unbindService(this.serviceConnection);
            return;
        }
        // 停止服务
        if (v.getId() == R.id.btn_delay_stop) {
            this.stopService(this.intent);
        }
    }
}