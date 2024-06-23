# 视图基础

Android 开发之视图基础

> 参考教程：Android Studio 开发实战：从零基础到 App 上线（第 3 版）-
> 章节：3.2

## 设置视图的宽高

控件宽度通过属性 `android:layout_width` 表达，控件高度通过属性 `android:layout_height` 表达，宽度和高度的取值有以下三种方式：

1. match_parent - 表示与父控件的宽度保持一致
2. wrap_content - 表示与内容自适应，最大不能超过父控件的宽度，一旦超过就要换行，最高不能超过父控件的高度，一旦超过就会被隐藏
3. 具体数值 - 以 dp 为单位的具体尺寸，如：300dp，表示固定宽高

在 XML 文件中采用以上任一方式都可以设置控件的宽高，但在 Java 代码中设置宽高首先要确保 XML 中的宽高为：
`wrap_content`，这样才允许在 Java 代码中修改控件的宽高。

在 Java 代码中设置控件的宽高，要通过 ViewGroup.LayoutParams 类的 width 和 height 属性进行，且默认单位为 px，我们需要将具体的 dp 值转换为 px，然后再赋值，可以通过以下代码进行转换：

```java
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
```

## 设置视图的间距

`layout_mariginXxx` 属性用于设置控件与外部视图（包括上级和同级视图）的距离，可作用于视图、各类布局和控件。不管是布局还是控件，它们都由 `View` 派生而来，而 `layout_margin`正是 `View` 的一个通用属性，所以 `View` 的子孙类都可以使用 `layout_margin`。有以下几种设置外边距的方式：

- `android:layout_margin` - 一次性设置上下左右四个方向的边距
- `android:layout_marginLeft` - 设置左边距
- `android:layout_marginRight` - 设置右边距
- `android:layout_marginTop` - 设置上边距
- `android:layout_marginBottom` - 设置下边距

除了 `layout_marin` 之外，`padding` 也是 `View` 的一个通用属性，用来设置视图的内部间距，`padding` 指的是视图与内部视图（包括下级视图和文本）的距离，有以下几种设置内边距的方式：

- `android:padding` - 一次性设置上下左右四个方向的边距
- `android:paddingLeft` - 设置左边距
- `android:paddingRight` - 设置右边距
- `android:paddingTop` - 设置上边距
- `android:paddingBottom` - 设置下边距
- `android:paddingStart` - 设置开始边距
- `android:paddingEnd` - 设置结束边距
- `android:paddingHorizontal` - 设置左右边距
- `android:paddingVertical` - 设置上下边距

示例代码：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/Primary"
    android:orientation="vertical"
    tools:context=".ActivityViewMargin">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="20dp"
        android:background="@color/Warning"
        android:padding="60dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <View
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/Error" />
    </LinearLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

## 设置视图的对齐方式

App 界面上的视图排列，默认靠左上对齐，如果想要更改对齐方式，可以在 XML 文件中通过属性`android:layout_gravity` 指定当前视图的对齐方式，分别有以下几种对齐方式：

- top - 朝上对齐
- bottom - 朝下对齐
- left - 靠左对齐
- right - 靠右对齐

可以通过竖线 `|` 连接不同的对齐方式，如靠左上对齐：`top|left`，此时属性标记为：`android:layout_gravity="top|left"`。

`android:layout_gravity` 属性指定的对齐方式指的是当前视图往上级视图的哪个方向对齐，并非当前视图的内部对齐。若想设置内部视图的对齐方式，则需要使用当前视图的 `android:gravity` 属性指定，该属性的对齐方式也有以下几种：

- top - 朝上对齐
- bottom - 朝下对齐
- left - 靠左对齐
- right - 靠右对齐

也可以进行组合。它和 `layout_gravity` 的区别为：`layout_gravity` 设定了当前视图相对于上级视图的对齐方式，而 `gravity` 决定了下级视图的位置。

示例代码：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ActivityViewGravity">
    <!--
    DEMO - 视图的对齐方式
    -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:background="@color/Warning"
        android:padding="5dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <!--
        该布局在上级视图中朝下对齐 - layout_gravity = bottom
        它的下级视图靠左对齐 - gravity = start
        -->
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="200dp"
            android:layout_gravity="bottom"
            android:layout_margin="10dp"
            android:layout_weight="1"
            android:background="@color/Error"
            android:gravity="start"
            android:padding="10dp">

            <View
                android:layout_width="100dp"
                android:layout_height="100dp"
                android:background="@color/Success" />
        </LinearLayout>

        <!--
        该布局在上级视图中朝上对齐 - layout_gravity = top
        它的下级视图靠右对齐 - gravity = end
        -->
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="200dp"
            android:layout_gravity="top"
            android:layout_margin="10dp"
            android:layout_weight="1"
            android:background="@color/Error"
            android:gravity="end"
            android:padding="10dp">

            <View
                android:layout_width="100dp"
                android:layout_height="100dp"
                android:background="@color/Success" />
        </LinearLayout>
    </LinearLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```