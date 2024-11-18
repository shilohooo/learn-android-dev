# 页面数据传输 DEMO

安卓开发之页数数据传输练习

> 参考资料：Android Studio 开发实战：从零基础到 App 上线（第3版）- 章节：4.6 - 2

## 需求

创建两个活动页面，从A页面携带请求数据跳转到B页面，B页面应当展示A页面传递来的信息，然后B页面向A页面返回应答数据，A页面也要展示B页面返回的信息

## 实现

通过 `Intent` 类的 `putExtra()` 方法设置数据，然后跳转到指定的页面，代码如下：

```java
// 带数据跳转页面A，可以直接调用 Intent 实例的 putExtra 方法
// 如果 bundle 还没有初始化，则会自动初始化一个新的
this.intent.putExtra("msg", "hello world from pageB");
```

目标页面可以在 `onCreate()` 方法中取出数据，代码如下：

```java
// 取出页面跳转时传递的数据
final Bundle bundle = this.getIntent().getExtras();
if (bundle != null) {
    // bundle 内部使用一个 Map 存储数据
    this.textView.setText(bundle.getString("msg"));
}
```
