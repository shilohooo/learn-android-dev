package org.shiloh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.CombinedVibration;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.shiloh.appbroadcast.R;

import java.util.Arrays;
import java.util.Date;

/**
 * 定时管理器示例
 *
 * @author shiloh
 * @date 2024/9/16 11:27
 */
public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = AlarmActivity.class.getSimpleName();
    private CheckBox checkBox;
    private TextView textView;
    private Integer delaySeconds;

    // region 闹钟延迟间隔选项

    private static final int[] DELAY_OPTIONS = {5, 10, 15, 20, 25, 30};
    private static final String[] DELAY_DESC_OPTIONS = Arrays.stream(DELAY_OPTIONS).
            mapToObj(i -> i + "秒")
            .toArray(String[]::new);

    // endregion

    // region broadcast
    public static final String ACTION_ALARM = "org.shiloh.ACTION_ALARM";
    private AlarmReceiver alarmReceiver;
    private String alarmMsg;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.checkBox = this.findViewById(R.id.cb_repeat_alarm);
        this.textView = this.findViewById(R.id.tv_alarm);
        this.findViewById(R.id.btn_set_alarm).setOnClickListener(this);
        this.initDelayOptions();
    }

    /**
     * 设置闹钟延迟间隔的选项列表
     *
     * @author shiloh
     * @date 2024/9/16 11:45
     */
    private void initDelayOptions() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, DELAY_DESC_OPTIONS
        );
        final Spinner spinner = this.findViewById(R.id.sp_delay);
        spinner.setPrompt("请选择闹钟延迟间隔");
        spinner.setAdapter(arrayAdapter);
        // 设置选中监听器
        spinner.setOnItemSelectedListener(new DelaySecondSelectedListener());
        spinner.setSelection(0);
    }

    // region register broadcast receiver

    @Override
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    protected void onStart() {
        super.onStart();
        this.alarmReceiver = new AlarmReceiver();
        final IntentFilter intentFilter = new IntentFilter(ACTION_ALARM);
        this.registerReceiver(this.alarmReceiver, intentFilter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(this.alarmReceiver);
    }

    // endregion

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_set_alarm != v.getId()) {
            return;
        }

        Log.i(LOG_TAG, "set alarm");
        /// 设置闹钟
        this.setAlarm();
        final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
        this.alarmMsg = String.format("%s - 设置闹钟", time);
        this.textView.setText(this.alarmMsg);
    }

    /**
     * 设置闹钟
     *
     * @author shiloh
     * @date 2024/9/16 11:53
     */
    private void setAlarm() {
        final Intent intent = new Intent(ACTION_ALARM);
        // 创建一个用于广播的延迟意图
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        // 从系统服务中获取闹钟管理器
        final AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        // 设置允许在设备空闲时发送广播，该方法是在 Android 6.0 之后新增的
        Log.i(LOG_TAG, String.format("设置闹钟，间隔：%d秒", delaySeconds));
        // 设置一次性闹钟，延迟若干秒后，携带延迟意图发送闹钟广播
        // （但Android6.0之后，set方法在暗屏时不保证发送广播，必须调用setAndAllowWhileIdle方法）
        // alarmManager.set(
        //         AlarmManager.RTC_WAKEUP, this.delaySeconds, pendingIntent
        // );
        // 下一次执行时间
        final long delayTime = System.currentTimeMillis() + this.delaySeconds;
        // 第一个参数为定时器类型，第二个参数为期望执行时间，第三个参数为待执行的延迟意图，即想做什么
        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, delayTime, pendingIntent
        );
        // 设置重复闹钟，每隔一定间隔就发送闹钟广播（但从Android4.4开始，setRepeating方法不保证按时发送广播）
        // alarmManager.setRepeating(
        //         AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), this.delaySeconds, pendingIntent
        // );
    }

    /**
     * 闹钟延迟间隔选中监听器
     *
     * @author shiloh
     * @date 2024/9/16 11:49
     */
    public class DelaySecondSelectedListener implements AdapterView.OnItemSelectedListener {
        /**
         * <p>Callback method to be invoked when an item in this view has been
         * selected. This callback is invoked only when the newly selected
         * position is different from the previously selected position or if
         * there was no selected item.</p>
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to access the
         * data associated with the selected item.
         *
         * @param parent   The AdapterView where the selection happened
         * @param view     The view within the AdapterView that was clicked
         * @param position The position of the view in the adapter
         * @param id       The row id of the item that is selected
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i(LOG_TAG, String.format("闹钟延迟间隔选中回调，position：%d", position));
            delaySeconds = DELAY_OPTIONS[position] * 1000;
            Log.i(LOG_TAG, String.format("闹钟延迟间隔为：%d秒", delaySeconds));
        }

        /**
         * Callback method to be invoked when the selection disappears from this
         * view. The selection can disappear for instance when touch is activated
         * or when the adapter becomes empty.
         *
         * @param parent The AdapterView that now contains no selected item.
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // do noting
        }
    }

    /**
     * 闹钟广播接收器
     *
     * @author shiloh
     * @date 2024/9/16 11:56
     */
    public class AlarmReceiver extends BroadcastReceiver {

        /**
         * 闹钟广播接收处理
         *
         * @author shiloh
         * @date 2024/9/16 11:56
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !ACTION_ALARM.equals(intent.getAction())) {
                return;
            }

            final String time = DateFormatUtils.format(new Date(), "HH:mm:ss");
            alarmMsg = String.format("%s%n%s - 闹钟时间到达", alarmMsg, time);
            textView.setText(alarmMsg);
            Log.i(LOG_TAG, String.format("SDK VERSION = %d", Build.VERSION.SDK_INT));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final VibrationEffect vibrationEffect = VibrationEffect.createOneShot(
                        500L,
                        VibrationEffect.DEFAULT_AMPLITUDE
                );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                    final VibratorManager vibratorManager = (VibratorManager) context.getSystemService(VIBRATOR_MANAGER_SERVICE);
                    // 触发手机振动
                    vibratorManager.vibrate(CombinedVibration.createParallel(vibrationEffect));
                } else {
                    final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(vibrationEffect);
                }
            }
            // 检查是否需要重复发送闹钟广播
            if (!checkBox.isChecked()) {
                return;
            }

            setAlarm();
        }
    }
}