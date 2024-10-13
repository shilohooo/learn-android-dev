package org.shiloh.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.android.service.ImmediateBindService;

/**
 * 服务的绑定与解绑 - 立即绑定
 *
 * @author shiloh
 * @date 2024/9/17 12:58
 */
public class ImmediateBindActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = ImmediateBindActivity.class.getSimpleName();

    private Intent intent;
    private ImmediateBindService immediateBindService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_immediate_bind);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.findViewById(R.id.btn_bind).setOnClickListener(this);
        this.findViewById(R.id.btn_unbind).setOnClickListener(this);
        this.intent = new Intent(this, ImmediateBindService.class);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_bind == v.getId()) {
            // 绑定服务。如果服务未启动，则系统先启动该服务再进行绑定
            final boolean flag = this.bindService(
                    this.intent, this.serviceConnection, Context.BIND_AUTO_CREATE
            );
            Log.i(LOG_TAG, String.format("bindService flag: %s", flag));
            return;
        }

        if (R.id.btn_unbind == v.getId()) {
            // 解绑服务。如果先前服务为立即绑定，则此时解绑之后自动停止服务
            this.unbindService(this.serviceConnection);
        }
    }

    /**
     * 服务连接组件
     */
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 如果服务运行于另外一个进程，则不能直接强制转换类型，否则会报错
            immediateBindService = ((ImmediateBindService.LocalBinder) service).getService();
            Log.i(LOG_TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            immediateBindService = null;
            Log.i(LOG_TAG, "onServiceDisconnected");
        }
    };
}