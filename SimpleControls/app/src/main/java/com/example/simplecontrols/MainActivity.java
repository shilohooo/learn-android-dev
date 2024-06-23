package com.example.simplecontrols;

import android.graphics.Color;
import android.os.Bundle;
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
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final TextView textView = this.findViewById(R.id.tv_hello);
        //  将文本控件的文本内容设置为定义在 res/values/strings.xml 文件中的字符串资源
        textView.setText(R.string.msg);
        // 修改文本的大小
        textView.setTextSize(30f);
        // 修改文本的颜色，使用定义好的颜色常量
//        textView.setTextColor(Color.YELLOW);
        // 修改文本控件的背景色，有两个方式
        // 1. 使用 Color 类中定义的颜色
        textView.setBackgroundColor(Color.BLACK);

        // 使用6位十六进制编码设置文本颜色
        final TextView textView2 = this.findViewById(R.id.tv_hello2);
        // 需要注意的是：颜色在 XML 文件中默认是不透明的（等价于透明度为 FF）
        // 在 Java 代码中默认是透明的（等价于透明度为 00），透明 = 看不见
//        textView2.setTextColor(0x00ff00);
        // 修改文本控件的背景颜色，使用在 /res/values/colors.xml 文件中定义的颜色资源
        // setBackgroundResource 不仅可以用来设置背景色，还可以设置背景图片
        textView2.setBackgroundResource(R.color.warning);

//        final TextView textView3 = this.findViewById(R.id.tv_hello3);
        // 设置文本颜色为不透明的绿色，即正常的颜色
//        textView3.setTextColor(0xff00ff00);


    }
}