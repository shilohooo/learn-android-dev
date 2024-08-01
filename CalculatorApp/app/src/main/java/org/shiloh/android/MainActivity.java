package org.shiloh.android;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 计算器 App
 * @author shiloh
 * @date 2024/8/1 22:10
 */
public class MainActivity extends AppCompatActivity {
    /**
     * 第一个操作数
     */
    private Double firstNum;

    /**
     * 第二个操作数
     */
    private Double secondNum;

    /**
     * 运算符
     */
    private String operator;

    /**
     * 计算结果
     */
    private String result;

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

        Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();
    }
}