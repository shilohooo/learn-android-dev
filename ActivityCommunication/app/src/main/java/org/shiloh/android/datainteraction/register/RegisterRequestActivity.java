package org.shiloh.android.datainteraction.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.shiloh.android.R;
import org.shiloh.android.datainteraction.normal.NormalRequestActivity;
import org.shiloh.android.datainteraction.normal.NormalResponseActivity;
import org.shiloh.android.datainteraction.normal.NormalSendActivity;

import java.util.Date;

public class RegisterRequestActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = RegisterRequestActivity.class.getSimpleName();

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private TextView responseTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final TextView requestTv = this.findViewById(R.id.tv_register_request);
        requestTv.setText(String.format("待发送的消息为：%s", NormalRequestActivity.REQUEST_MSG));
        this.findViewById(R.id.btn_register_request).setOnClickListener(this);
        this.responseTv = this.findViewById(R.id.tv_register_response);

        // 注册处理响应数据的ActivityResultLauncher
        initResponseHandler();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_register_request != v.getId()) {
            return;
        }

        final Intent intent = new Intent(this, NormalResponseActivity.class);
        final Bundle bundle = new Bundle();
        final String requestTime = DateFormatUtils.format(new Date(), NormalSendActivity.DATETIME_PATTERN);
        bundle.putString(NormalSendActivity.NormalMsgKey.REQUEST_TIME, requestTime);
        bundle.putString(NormalSendActivity.NormalMsgKey.REQUEST_MSG, NormalRequestActivity.REQUEST_MSG);
        intent.putExtras(bundle);
        // 通过 activityResultLauncher 来启动 Activity
        this.activityResultLauncher.launch(intent);
    }

    /**
     * 响应数据处理，当响应数据返回时触发
     *
     * @author shiloh
     * @date 2024/8/18 22:26
     */
    private void initResponseHandler() {
        this.activityResultLauncher = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            // 响应数据处理
            if (result.getResultCode() != Activity.RESULT_OK || result.getData() == null) {
                return;
            }

            Log.i(LOG_TAG, String.format("活动结果启动器接收到响应数据：%s", result));

            final Bundle bundle = result.getData().getExtras();
            if (bundle == null) {
                return;
            }

            final String responseTime = bundle.getString(NormalSendActivity.NormalMsgKey.RESPONSE_TIME);
            final String responseMsg = bundle.getString(NormalSendActivity.NormalMsgKey.RESPONSE_MSG);
            this.responseTv.setText(String.format(
                    "收到响应消息：%n响应时间为：%s%n消息内容为：%s", responseTime, responseMsg
            ));
        });
    }
}