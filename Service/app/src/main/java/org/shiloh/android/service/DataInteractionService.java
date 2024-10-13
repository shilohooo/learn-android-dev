package org.shiloh.android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Locale;

/**
 * 活动与数据交互服务
 *
 * @author shiloh
 * @date 2024/10/13 14:22
 */
public class DataInteractionService extends Service {
    public static final String LOG_TAG = DataInteractionService.class.getSimpleName();

    private final LocalBinder binder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "绑定服务");
        return this.binder;
    }

    /**
     * Called when all clients have disconnected from a particular interface
     * published by the service.  The default implementation does nothing and
     * returns false.
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return true if you would like to have the service's
     * {@link #onRebind} method later called when new clients bind to it.
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "解绑服务");
        return true;
    }

    public class LocalBinder extends Binder {
        /**
         * 获取服务对象
         *
         * @return 服务对象实例
         * @author shiloh
         * @date 2024/10/13 14:23
         */
        public DataInteractionService getService() {
            return DataInteractionService.this;
        }

        /**
         * 获取数字描述
         *
         * @param num 数字
         * @return 数字描述
         * @author shiloh
         * @date 2024/10/13 14:24
         */
        public String getNumDesc(Integer num) {
            return String.format(Locale.CHINA, "我收到了数字：%d%n", num);
        }
    }
}