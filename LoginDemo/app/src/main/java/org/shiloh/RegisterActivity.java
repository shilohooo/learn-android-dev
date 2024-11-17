package org.shiloh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 注册页面
 *
 * @author shiloh
 * @date 2024/11/17 13:14
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 跳转到完成页面的意图对象
     */
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(this.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 指定这个意图对象要跳转的页面
        this.intent = new Intent(this, FinishActivity.class);

        // 按钮点击事件监听器设置为当前页面
        this.findViewById(R.id.btn_register).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // 判断当前点击的元素是否为注册按钮
        if (R.id.btn_register != v.getId()) {
            return;
        }

        // 设置标志位，清除任务栈，然后创建一个新的任务栈
        this.intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        // 启动意图对象
        this.startActivity(this.intent);
    }
}