package org.shiloh.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ImmediateBindService extends Service {
    public static final String LOG_TAG = ImmediateBindService.class.getSimpleName();
    private final IBinder binder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "绑定服务：" + this.binder);
        return this.binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "解绑服务：" + this.binder);
        // 返回 true 表示允许多次绑定
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(LOG_TAG, "onRebind");
    }

    /**
     * 服务绑定组件
     *
     * @author shiloh
     * @date 2024/9/17 13:02
     */
    public class LocalBinder extends Binder {
        public ImmediateBindService getService() {
            return ImmediateBindService.this;
        }
    }
}