# Activity 的启动模式

安卓开发之 Activity 的启动模式

> 参考资料：Android Studio 开发实战：从零基础到 App 上线（第 3 版）- 章节：4.1.3

安卓系统为每个正在运行的App分配了活动栈，用于存储已经创建且尚未销毁的活动页面信息，而栈是一种后进先出的数据结构，后面入栈的活动总是先出栈，假设3个活动页面（A、B、C）的入栈顺序为：A->
B->C，则它们的出栈顺序将变为：C->B->A，可见活动页面 C 结束之后会返回到 B，而不是返回到 A 或者其他地方。

上述出入栈情况为默认的标准模式，实际上 Android 允许在创建活动时指定该活动的启动模式，通过启动模式来控制活动的出入栈行为。App
提供了两种办法用于设置活动页面的启动模式，其一是修改 AndroidManifest.xml 配置文件，在指定的activity
节点添加属性 android:launchMode="xxx" 属性，表示本活动以哪个启动模式允许；其二是在代码中调用 Intent
对象的 setFlags 方法，表明后续打开的活动页面采用该启动标志。

- 在配置文件中指定启动模式

```xml

<activity android:launchMode="standard" android:name=".MainActivity" />
```

launchMode 属性取值说明见下表：

| launchMode 属性值 |                                说明                                |
|:--------------:|:----------------------------------------------------------------:|
|    standard    | 标准模式，无论何时启动哪个活动页面，都是重新创建该页面的实例并放入栈顶。如果不指定 launchMode 属性，则默认为标准模式 |
|   singleTop    |    启动新活动时，判断如果栈顶正好就是该活动的实例，则直接重用该实例；否则创建新的实例并放入栈顶，也就是按照标准模式处理    |
|   singleTask   |     启动新活动时，判断如果栈中存在该活动的实例，则重用该实例，并清除位于该实例上面的所有实例；否则按照标准模式处理      |
| singleInstance |                启动新活动时，将该活动的实例放入一个新栈中，原栈的实例列表保持不变                 |

- 在 Java 代码里面设置启动标志

```java
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_login != v.getId()) {
            return;
        }

        final Intent intent = new Intent(this, HomeActivity.class);
        // 设置启动标志：跳转到新页面时，栈中的原有实例都被清空，同时开辟新任务的活动栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
}
```

之所以要在 Java 代码中动态指定活动页面的启动模式，是因为 AndroidManifest.xml 对每个活动只能指定唯一的启动模式。
若想在不同时候对同一个活动运用不同的启动模式，显然固定的 launchMode 属性无法满足需求。于是 Android
允许在代码中手动
设置启动标志，这样在不同时候调用 startActivity 方法时，就可以指定不同的启动模式。

适用于 setFlags 方法的几种启动标志取值说明见下表：

|          Intent 类的启动标志          |                                                                         说明                                                                         |
|:-------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------:|
|  Intent.FLAG_ACTIVITY_NEW_TASK  |                                                    开辟一个新的任务栈，类似标准模式，不同之处在于：如果原来不存在活动栈，则会创建一个新的                                                     |
| Intent.FLAG_ACTIVITY_SINGLE_TOP |                                                当栈顶为待跳转的活动实例之时，则重用栈顶的时。该值等同于 launchMode="singleTop"                                                 |
| Intent.FLAG_ACTIVITY_CLEAR_TOP  | 当栈中存在待跳转的活动实例时，则重新创建一个新的实例，并清除原实例上方的所有实例。该值于 launchMode="singleTask" 类似，但 singleTask 采取 onNewIntent 方法启用原任务，而该值采取先调用 onDestroy 再调用 onCreate 来创建新任务 |
| Intent.FLAG_ACTIVITY_NO_HISTORY |                                    该标志与 launchMode="standard" 类型，但栈中不保存新启动的活动实例，这样下次无论以何种方式再启动该实例，也要走标准模式的完整流程                                     |
| Intent.FLAG_ACTIVITY_CLEAR_TASK |                                          该标志非常暴力，跳转到新页面时，栈中的原有实例都被清空。注意该标志需要结合 FLAG_ACTIVITY_NEW_TASK 使用                                           |

## 示例代码

[点此查看页面互相跳转的示例代码](./app/src/main/java/org/shiloh/android/MainActivity.java)
[点此查看登录后进入主页的示例代码](./app/src/main/java/org/shiloh/android/LoginActivity.java)