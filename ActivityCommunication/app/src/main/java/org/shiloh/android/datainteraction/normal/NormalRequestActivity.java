package org.shiloh.android.datainteraction.normal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.shiloh.android.R;

import java.util.Date;

public class NormalRequestActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String REQUEST_MSG = "吃了吗您？";

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_normal_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final TextView requestTestView = this.findViewById(R.id.tv_request);
        requestTestView.setText(String.format("待发送的消息为：%s", REQUEST_MSG));

        this.responseTextView = this.findViewById(R.id.tv_response);

        this.findViewById(R.id.btn_request).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_request != v.getId()) {
            return;
        }

        final Intent intent = new Intent(this, NormalResponseActivity.class);
        final Bundle bundle = new Bundle();
        final String requestTime = DateFormatUtils.format(new Date(),
                NormalSendActivity.DATETIME_PATTERN);
        bundle.putString(NormalSendActivity.NormalMsgKey.REQUEST_TIME, requestTime);
        bundle.putString(NormalSendActivity.NormalMsgKey.REQUEST_MSG, REQUEST_MSG);
        intent.putExtras(bundle);
        // 跳转到指定页面，并设置请求编码，期望接收到响应数据
        this.startActivityForResult(intent, 0);
    }

    /**
     * 当接收到响应数据时，会回调此方法
     *
     * @param requestCode 请求编码
     * @param resultCode  响应编码
     * @param data        响应数据
     * @author shiloh
     * @date 2024/8/18 12:25
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 请求编码需要和跳转时设置的一致，才是符合预期的响应，且响应编码应为 Activity.RESULT_OK
        if (0 != requestCode || Activity.RESULT_OK != resultCode || data == null) {
            return;
        }

        // 取出响应信息并显示到指定的文本视图中
        final Bundle bundle = data.getExtras();
        if (bundle == null) {
            return;
        }

        final String responseTime = bundle.getString(NormalSendActivity.NormalMsgKey.RESPONSE_TIME);
        final String responseMsg = bundle.getString(NormalSendActivity.NormalMsgKey.RESPONSE_MSG);
        final String msg = String.format(
                "收到响应消息：%n响应时间为：%s%n消息内容为：%s", responseTime, responseMsg
        );
        this.responseTextView.setText(msg);
    }
}