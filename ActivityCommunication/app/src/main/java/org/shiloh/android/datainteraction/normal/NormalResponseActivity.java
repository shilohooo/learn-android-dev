package org.shiloh.android.datainteraction.normal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.shiloh.android.R;

import java.util.Date;

public class NormalResponseActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String RESPONSE_MSG = "吃了:)";

    private TextView receiveMsgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_normal_response);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.receiveMsgTextView = this.findViewById(R.id.tv_receive_msg);
        this.displayReceiveMsg();

        final TextView returnMsgTextView = this.findViewById(R.id.tv_return_msg);
        returnMsgTextView.setText(String.format("待返回的消息为：%s", RESPONSE_MSG));

        this.findViewById(R.id.btn_response).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_response != v.getId()) {
            return;
        }

        final Intent intent = new Intent();
        // 塞入响应数据
        final Bundle bundle = new Bundle();
        final String responseTime = DateFormatUtils.format(new Date(),
                NormalSendActivity.DATETIME_PATTERN);
        bundle.putString(NormalSendActivity.NormalMsgKey.RESPONSE_TIME, responseTime);
        bundle.putString(NormalSendActivity.NormalMsgKey.RESPONSE_MSG, RESPONSE_MSG);
        intent.putExtras(bundle);
        // 携带 intent 返回上一个页面，并将 resultCode 设置为 RESULT_OK，表示处理成功
        this.setResult(Activity.RESULT_OK, intent);
        this.finish();
    }

    /**
     * 将接收到消息显示到文本视图中
     *
     * @author shiloh
     * @date 2024/8/18 12:37
     */
    private void displayReceiveMsg() {
        final Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        final String requestTime = bundle.getString(NormalSendActivity.NormalMsgKey.REQUEST_TIME);
        final String requestMsg = bundle.getString(NormalSendActivity.NormalMsgKey.REQUEST_MSG);
        final String msg = String.format(
                "收到请求消息：%n请求时间为：%s%n消息内容为：%s", requestTime, requestMsg
        );
        this.receiveMsgTextView.setText(msg);
    }
}