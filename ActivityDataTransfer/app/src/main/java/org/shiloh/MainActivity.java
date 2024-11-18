package org.shiloh;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView textView;
    private Intent intent;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        this.textView = this.findViewById(R.id.tv_page_b_msg);
        this.registerActivityResultLauncher();

        // 取出页面B跳转时传递的数据
        // final Bundle bundle = this.getIntent().getExtras();
        // if (bundle != null) {
        //     // bundle 内部使用一个 Map 存储数据
        //     this.textView.setText(bundle.getString("msg"));
        // }

        this.intent = new Intent(this, PageBActivity.class);
        this.findViewById(R.id.btn_to_page_b).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_to_page_b != v.getId()) {
            return;
        }

        // 带数据跳转页面B，Bundle 里面可以放多个数据，底层使用的是 Map
        final Bundle bundle = new Bundle();
        bundle.putString("msg", "hello world from pageA");
        this.intent.putExtras(bundle);
        // 执行启动器的跳转与回调处理
        this.activityResultLauncher.launch(this.intent);
    }

    /**
     * 注册 activityResultLauncher
     *
     * @author shiloh
     * @date 2024/11/18 21:44
     */
    private void registerActivityResultLauncher() {
        // 注册 activityResultLauncher
        this.activityResultLauncher = this.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                // 指定对活动页面返回的数据的处理逻辑
                result -> {
                    Log.i(LOG_TAG, "onActivityResult: " + result);
                    // 返回码不是 OK，或者活动页面没有返回数据，则无需处理
                    if (RESULT_OK != result.getResultCode() || result.getData() == null) {
                        return;
                    }

                    final Bundle bundle = result.getData().getExtras();
                    if (bundle == null) {
                        return;
                    }

                    this.textView.setText(bundle.getString("msg"));
                }
        );
    }
}