# Activity 启动和结束

安卓开发之 Activity 的启动和结束

> 参考资料：Android Studio 开发实战：从零基础到 App 上线（第 3 版）- 章节：4.1.1

在安卓开发中，一个Activity（活动）就代表一个界面（页面），与浏览器的页面不同，安卓没有打开页面、关闭页面的说法，而是称作启动和结束活动，因此，页面导航的方法名称并不是 `open`
和 `close`，而是 `startActivity` 和 `finish`。

## 示例

[点击按钮跳转到指定的 Activity](./app/src/main/java/org/shiloh/android/MainActivity.java)

```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        // 给页面上的元素绑定点击事件
        this.findViewById(R.id.btn_next_page).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Log.i(LOG_TAG, String.format("clicked view id: %s", R.id.btn_next_page));
        if (R.id.btn_next_page != v.getId()) {
            return;
        }

        // 点击按钮后，跳转到指定的页面（活动 = 页面）
        this.startActivity(new Intent(this, AboutActivity.class));
    }
}
```

----

[返回到上一个 Activity](./app/src/main/java/org/shiloh/android/AboutActivity.java)

```java
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        // 给页面中的元素绑定点击事件
        this.findViewById(R.id.img_back).setOnClickListener(this);
        this.findViewById(R.id.btn_back).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // 点击返回图标或完成按钮，回到上一页
        if (R.id.img_back == v.getId() || R.id.btn_back == v.getId()) {
            this.finish();
        }
    }
}
```