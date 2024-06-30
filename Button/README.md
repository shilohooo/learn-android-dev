# 按钮触控

Android 开发之按钮触控

> 参考资料：Android Studio 开发实战：从零基础到 App 上线（第3版）

按钮是从 `TextView` 派生的控件，它可以使用文本视图的属性和方法，区别是按钮有默认的背景颜色。

## 常用属性：

- android:text：设置按钮的文本
- android:textAllCaps：设置按钮文本是否为大写，默认为 false
- android:enabled: 设置按钮是否可用，默认为 true，为 false 代表禁用按钮

## 监听点击事件

在 onCreate 方法中获取页面中的按钮控件实例，然后通过调用 setOnClickListener 方法给按钮控件设置
点击事件监听器

```java
// 获取按钮控件，给它设置点击事件监听器：点击按钮时修改文本视图的内容
super.findViewById(R.id.lower_btn).setOnClickListener(view->{
    Log.i("MainActivity",String.format("当前点击的控件对象为：%s",view.getClass()));
    final CharSequence btnText=((Button)view).getText();
    final String content=String.format("%s - 你点击了按钮： %s",DateUtils.getNowStr(),btnText);
    final TextView textView=super.findViewById(R.id.tv_result);
    textView.setText(content);
});
```

## 监听长按事件

长按控件超过500毫秒，会触发该控件的长按事件

我们可以在 onCreate 方法中获取页面中的按钮控件实例，然后通过调用 setOnLongClickListener 方法给按钮控件设置
长按事件监听器

```java
super.findViewById(R.id.caps_btn).setOnLongClickListener(view -> {
    // 找到要修改内容的文本控件，参数 view 为当前长按的控件对象
    Log.i("MainActivity", String.format("当前长按的控件对象为：%s", view.getClass()));
    final CharSequence btnText = ((Button) view).getText();
    final String content = String.format("%s - 你长按了按钮： %s", DateUtils.getNowStr(), btnText);
    final TextView textView = super.findViewById(R.id.tv_result);
    textView.setText(content);
    return true;    
});
```

## 将整个页面设置为事件监听器

如果一个页面有多个按钮，每个按钮都需要定义自己的监听器，比较麻烦，更好的办法是注册统一的监听器，也
就是让当前页面实现接口 `View.OnClickListener`，然后重写 `onClick` 方法，在 `onClick` 方法中
根据点击的控件 ID 来判断要执行的操作，示例代码如下：

### 页面代码

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">
        <!--
        按钮控件由 TextView 派生而来，文本视图拥有的属性和方法，按钮也能使用。
        按钮的 textAllCaps 表示 是否将按钮的文字转换为大写，默认为：false
         -->
        <Button
            android:id="@+id/caps_btn"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginHorizontal="12dp"
            android:layout_marginBottom="12dp"
            android:text="长按：Hello World"
            android:textAllCaps="true" />

        <!--
        按钮的 enabled 属性表示 按钮是否可用，默认为：true，false 表示按钮被禁用
        禁用状态下的按钮是不能被点击的，背景色、文字颜色变为灰色
        -->
        <Button
            android:id="@+id/lower_btn"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginHorizontal="12dp"
            android:enabled="false"
            android:text="点我：Hello World"
            android:textAllCaps="false" />

        <TextView
            android:id="@+id/tv_result"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginHorizontal="12dp"
            android:layout_marginVertical="12dp"
            android:padding="12dp"
            android:text="在这里查看按钮的点击效果~"
            android:textColor="@color/primary" />
    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### Java 代码

```java
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
```