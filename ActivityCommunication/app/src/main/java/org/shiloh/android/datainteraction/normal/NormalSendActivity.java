package org.shiloh.android.datainteraction.normal;

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

public class NormalSendActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_normal_send);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.textView = this.findViewById(R.id.tv_send);
        this.findViewById(R.id.btn_send).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_send != v.getId()) {
            return;
        }

        // 跳转到指定页面，并发送消息
        final Intent intent = new Intent(this, NormalReceiveActivity.class);
        // 发送的数据放在 Bundle 类中
        final Bundle bundle = new Bundle();
        // 往 bundle 中塞入指定类型的数据
        final String requestTime = DateFormatUtils.format(new Date(), DATETIME_PATTERN);
        bundle.putString(NormalMsgKey.REQUEST_TIME, requestTime);
        bundle.putString(NormalMsgKey.REQUEST_MSG, this.textView.getText().toString());
        // 将 bundle 放入 intent 中，这样可以一次将多个数据传递给下一个页面
        intent.putExtras(bundle);

        this.startActivity(intent);
    }

    /**
     * 普通消息键值
     *
     * @author shiloh
     * @date 2024/8/18 11:47
     */
    public static class NormalMsgKey {
        private NormalMsgKey() {
        }

        public static final String REQUEST_TIME = "request_time";
        public static final String REQUEST_MSG = "request_msg";
        public static final String RESPONSE_TIME = "response_time";
        public static final String RESPONSE_MSG = "response_msg";
    }
}