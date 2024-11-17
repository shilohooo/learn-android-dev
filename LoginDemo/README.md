# 页面跳转 DEMO

安卓开发之页面跳转练习

> 参考资料：Android Studio 开发实战：从零基础到 App 上线（第3版）- 章节：4.6

## 需求

创建两个活动页面，模拟注册页面和完成页面，先从注册页面跳转到完成页面，在完成页面按返回键，不能回到注册页面（因为注册成功之后无需重新注册）

## 实现

通过设置 `Intent` 的标识位来达到上述效果，示例代码：

```java
public void onClick(View v) {
    // 设置标志位，清除任务栈，然后创建一个新的任务栈
    this.intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    // 启动意图对象
    this.startActivity(this.intent);
}
```
