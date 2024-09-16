package org.shiloh.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class MyStaticReceiver extends BroadcastReceiver {
    /**
     * 静态注册接收器、发送、接收广播的 action 三者要一致
     */
    public static final String ACTION_SHOCK = "org.shiloh.action.SHOCK";
    private static final String LOG_TAG = MyStaticReceiver.class.getSimpleName();

    /**
     * 广播接收回调处理：触发手机振动
     *
     * @author shiloh
     * @date 2024/9/16 10:30
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "静态注册的接收器接收到广播，action 为：" + intent.getAction());
        if (!ACTION_SHOCK.equals(intent.getAction())) {
            return;
        }

        final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(
                    // 指定振动时间，单位为：毫秒
                    500L,
                    // 振动强度：默认
                    VibrationEffect.DEFAULT_AMPLITUDE
            ));
        }
    }
}