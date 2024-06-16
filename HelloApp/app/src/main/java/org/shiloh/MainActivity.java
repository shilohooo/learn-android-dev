package org.shiloh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // 当前页面的内容来自 res/layout/activity_main.xml
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("MainActivity", "这是一条调试级别的日志~");

        // 获取 ID 为 tv_hello 的 TextView 控件
        // findViewById() 方法用于在布局文件中查找指定 ID 对应的控件
        final TextView textView = this.findViewById(R.id.tv_hello);
        // 设置 TextView 的文本内容
        textView.setText("你好，世界！");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.toNextPage();
    }

    /**
     * 跳转到下一个页面
     *
     * @author shiloh
     * @date 2024/6/16 15:54
     */
    private void toNextPage() {
        // 修改页面提示
        final TextView textView = this.findViewById(R.id.tv_hello);
        textView.setText("3秒后跳转到下一个页面");
        // 延迟三秒执行任务，从MainActivity跳转到Main2Activity
        final View mainView = this.findViewById(R.id.main);
        mainView.postDelayed(() -> this.startActivity(
                new Intent(this, Main2Activity.class)), 3000L
        );
    }
}