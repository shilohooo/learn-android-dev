package org.shiloh.android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.android.util.DateUtils;

/**
 * 实现 {@link View.OnClickListener}，将整个页面当作点击事件监听器
 * 在触发点击事件时，要判断当前点击的是哪个控件，然后执行相应的逻辑
 * <p>
 * 实现 {@link View.OnLongClickListener}，将整个页面当作长按事件监听器
 * 在触发长按事件时，要判断当前点击的是哪个控件，然后执行相应的逻辑
 * <p>
 * 点击和长按事件不局限于按钮控件，它们都来自视图基类 {@link View}，凡是派生自 {@link View} 的控件，都可以注
 * 册点击和长按监听器。
 *
 * @author shiloh
 * @date 2024/6/30 12:21
 */
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnLongClickListener {

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

        // 获取按钮控件，给它设置点击事件监听器：点击按钮时修改文本视图的内容
        super.findViewById(R.id.lower_btn).setOnClickListener(this);
        // 获取按钮控件，给它设置长按事件监听器：每当控件被按住超过500毫秒之后，就会触发该控件的长按事件
        super.findViewById(R.id.caps_btn).setOnLongClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (R.id.lower_btn != view.getId()) {
            return;
        }

        // 找到要修改内容的文本控件，参数 view 为当前点击的控件对象
        Log.i("MainActivity", String.format("当前点击的控件对象为：%s", view.getClass()));
        final CharSequence btnText = ((Button) view).getText();
        final String content = String.format("%s - 你点击了按钮： %s", DateUtils.getNowStr(), btnText);
        final TextView textView = super.findViewById(R.id.tv_result);
        textView.setText(content);
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param view The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View view) {
        if (R.id.caps_btn != view.getId()) {
            return false;
        }

        // 找到要修改内容的文本控件，参数 view 为当前长按的控件对象
        Log.i("MainActivity", String.format("当前长按的控件对象为：%s", view.getClass()));
        final CharSequence btnText = ((Button) view).getText();
        final String content = String.format("%s - 你长按了按钮： %s", DateUtils.getNowStr(), btnText);
        final TextView textView = super.findViewById(R.id.tv_result);
        textView.setText(content);
        return true;
    }
}