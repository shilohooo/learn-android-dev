package org.shiloh;

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

public class PageBActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_bactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.textView = this.findViewById(R.id.tv_page_a_msg);
        // 取出页面A跳转时传递的数据
        final Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            // bundle 内部使用一个 Map 存储数据
            this.textView.setText(bundle.getString("msg"));
        }

        this.intent = new Intent(this, MainActivity.class);
        this.findViewById(R.id.btn_to_page_a).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_to_page_a != v.getId()) {
            return;
        }

        // 带数据跳转页面A，可以直接调用 Intent 实例的 putExtra 方法
        // 如果 bundle 还没有初始化，则会自动初始化一个新的
        this.intent.putExtra("msg", "hello world from pageB");
        // 返回页面时设置结果代码：RESULT_OK 表示处理成功
        this.setResult(Activity.RESULT_OK, this.intent);
        this.finish();
    }
}