package org.shiloh.android.datainteraction.normal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.android.R;

public class NormalReceiveActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_normal_receive);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 将上一个页面传递过来的消息显示到文本视图中
        this.textView = this.findViewById(R.id.tv_receive);
        this.displayReceiveMsg();

        this.findViewById(R.id.btn_receive).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_receive != v.getId()) {
            return;
        }

        this.finish();
    }

    /**
     * 显示接收到的消息
     *
     * @author shiloh
     * @date 2024/8/18 11:45
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
        this.textView.setText(msg);
    }
}