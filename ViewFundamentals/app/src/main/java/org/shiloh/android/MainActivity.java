package org.shiloh.android;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
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
        // 自适应布局，自动设置边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 使用 Java 代码的方式修改控件的宽度高度，这种方式的前提是：控件在 xml 中的宽高为 wrap_content
        // 只有这样才允许通过 Java 代码修改控件的宽高
        // 1、获取控件对象
        final TextView textView = this.findViewById(R.id.tvByCode);
        // 2、获取布局参数
        final ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        // 3、设置宽高，布局参数的宽高默认单位为 px，这里要先把 dp 转成 px
        layoutParams.width = dp2px(this, 100);
//        layoutParams.width = dp2px(this, 200);
        // 4、设置布局参数
        textView.setLayoutParams(layoutParams);
    }

    /**
     * dp 转为 px
     *
     * @param context 上下文
     * @param dpValue dp 值
     * @return px 值
     * @author shiloh
     * @date 2024/6/23 13:43
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}