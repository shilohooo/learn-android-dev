# 💻 常用布局

Android 开发之常用布局

> 参考书籍：Android Studio 开发实战：从零基础到 App 上线（第 3 版）-
> 章节：3.3

常见的布局包括：

- 线性布局：在某个方向上顺序排列
- 相对布局：参照其他视图的位置相对排列
- 网格布局：像表格那样分行分列显示
- 滚动视图：支持通过滑动操作拉出更多内容

## 🧵 线性布局 - LinearLayout

线性布局内部的控件排列顺序是固定，要么是从左到右，要么是从上到下。 `LinearLayout` 通过属性
`android:orientation` 来设置排列方向。其中从左到右排列为水平方向，属性值为：`horizontal`，从上到下排列为垂直
方向，属性值为：`vertical`。如果不指定排列方向，系统默认该布局为水平方向排列，也就是默认：
`android:layout_orientation="horizontal"`。

示例代码：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/main"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical" tools:context=".MainActivity">

    <LinearLayout android:id="@+id/llh" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:orientation="horizontal"
        app:layout_constraintTop_toTopOf="parent">

        <TextView android:layout_width="0dp" android:layout_height="wrap_content"
            android:layout_weight="1" android:background="@color/success" android:gravity="center"
            android:text="横排第一个" android:textColor="@color/white" />

        <TextView android:layout_width="0dp" android:layout_height="wrap_content"
            android:layout_weight="1" android:background="@color/warning" android:gravity="center"
            android:text="横排第二个" android:textColor="@color/white" />
    </LinearLayout>

    <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
        android:orientation="vertical" app:layout_constraintTop_toBottomOf="@id/llh">

        <TextView android:layout_width="match_parent" android:layout_height="0dp"
            android:layout_weight="1" android:background="@color/primary" android:gravity="center"
            android:text="竖排第一个" android:textColor="@color/white" />

        <TextView android:layout_width="match_parent" android:layout_height="0dp"
            android:layout_weight="1" android:background="@color/error" android:gravity="center"
            android:text="竖排第二个" android:textColor="@color/white" />
    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

除了方向之外，线性布局还有一个权重概念。权限指的是线性布局的下级视图各自拥有多大比例的宽高。权重在 XML
文件中通过属性 `android:layout_weight` 来设置，权重值越大，下级视图的宽高比例越大。

视图有宽、高两个方向，要让系统知道表示哪个方向的权限，需要将宽/高设置为 `0dp`，如果宽度为 `0dp`，则表示水平方向
的权重，下级视图会从左往右分割线性布局，如果高度为 `0dp`，则表示垂直方向的权重，下级视图会从上往下分割线性布局。

## 🤼 相对布局 - RelativeLayout

相对布局的下级视图由其他视图决定，因为下级视图的位置是相对位置，所以得有具体的参照物才能确定最终位置。如何不设定
下级视图的参照物，那么下级视图默认显示在相对布局的左上角。

用于确定下级视图位置的参照物分为两种：

1. 与该视图自身平级的视图
2. 该视图的上级视图（也就是它归属的 `RelativeLayout` ）

结合两种参照物，相对位置在 XML 文件中的属性取值说明如下表所示：

|         相对位置的属性取值          |      相对位置说明      |
|:--------------------------:|:----------------:|
|     `layout_toLeftOf`      |   当前视图在指定视图的左边   |
|     `layout_toRightOf`     |   当前视图在指定视图的右边   |
|       `layout_above`       |   当前视图在指定视图的上放   |
|       `layout_below`       |   当前视图在指定视图的下方   |
|     `layout_alignLeft`     |  当前视图与指定视图的左侧对齐  |
|    `layout_alignRight`     |  当前视图与指定视图的右侧对齐  |
|     `layout_alignTop`      |  当前视图与指定视图的顶部对齐  |
|    `layout_alignBottom`    |  当前视图与指定视图的底部对齐  |
|  `layout_centerInParent`   |   当前视图在上级视图中间    |
| `layout_centerHorizontal`  | 当前视图在上级视图的水平方向居中 |
|  `layout_centerVertical`   | 当前视图在上级视图的垂直方向居中 |
|  `layout_alignParentLeft`  |  当前视图与上级视图的左侧对齐  |
| `layout_alignParentRight`  |  当前视图与上级视图的右侧对齐  |
|  `layout_alignParentTop`   |  当前视图与上级视图的顶部对齐  |
| `layout_alignParentBottom` |  当前视图与上级视图的底部对齐  |

## 🥅 网格布局 - GridLayout

网格布局就像表格，拥有列和行，默认从左往右，从上到下排列，先从第一行开始从左往右放置下级视图，塞满之后另起一行放
置其余的下级视图，如此循环往复直到所有下级视图都放置完毕。为了能够判断能够容纳几行几列，网格布局新增了
`android:columnCount` 与 `android:rowCount` 两个属性，用于指定列数和行数。

示例代码：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/main"
    android:layout_width="match_parent" android:layout_height="match_parent"
    tools:context=".ActivityGridLayout">
    <!--
    网格布局 demo：两行两列
    columnCount：列数
    rowCount：行数
    -->
    <GridLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:columnCount="2" android:rowCount="2">

        <TextView android:layout_width="180dp" android:layout_height="60dp"
            android:background="@color/primary" android:gravity="center" android:text="Primary"
            android:textColor="@color/white" />

        <TextView android:layout_width="180dp" android:layout_height="60dp"
            android:background="@color/success" android:gravity="center" android:text="Success"
            android:textColor="@color/white" />

        <TextView android:layout_width="180dp" android:layout_height="60dp"
            android:background="@color/warning" android:gravity="center" android:text="Warning"
            android:textColor="@color/white" />

        <TextView android:layout_width="180dp" android:layout_height="60dp"
            android:background="@color/error" android:gravity="center" android:text="Error"
            android:textColor="@color/white" />
    </GridLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

## 🪜 滚动视图 - ScrollView

与线性布局类型，滚动视图也分为垂直方向和水平反向两类，其中垂直滚动视图的控件名称为：`ScrollView`，水平滚动视图
的控件名称为：`HorizontalScrollView`。在使用滚动视图时，需要注意以下3点：

1. 在垂直滚动方向，`layout_width` 属性值需要设置为：`match_parent`，`layout_height` 属性值需要设置为：
   `wrap_content`。
2. 在水平滚动方向，`layout_width` 属性值需要设置为：`wrap_content`，`layout_height` 属性值需要设置为：
   `match_parent`。
3. 滚动视图节点下面只能有一个子节点，否则会报错：
   `Caused by: java.lang.IllegalStateException: ScrollView can host only one direct child`。

示例代码：

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools" android:id="@+id/main"
    android:layout_width="match_parent" android:layout_height="match_parent"
    tools:context=".ActivityScrollView">
    <!--滚动视图 demo-->
    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:orientation="vertical" app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">
        <!--        HorizontalScrollView - 水平方向的滚动视图-->
        <HorizontalScrollView android:layout_width="wrap_content" android:layout_height="200dp">

            <!--            水平方向的线性布局-->
            <LinearLayout android:layout_width="wrap_content" android:layout_height="match_parent"
                android:orientation="horizontal">

                <View android:layout_width="300dp" android:layout_height="match_parent"
                    android:background="@color/primary" />

                <View android:layout_width="300dp" android:layout_height="match_parent"
                    android:background="@color/success" />
            </LinearLayout>
        </HorizontalScrollView>

        <!--
        ScrollView - 垂直方向的滚动视图
        如果滚动视图中的实际内容不够多，又想让它充满屏幕，可以添加以下两个属性：
        android:layout_height="match_parent"
        android:fillViewport="true" - 允许填满视图窗口
        -->
        <ScrollView android:layout_width="match_parent" android:layout_height="wrap_content">

            <!--            垂直方向的线性布局-->
            <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
                android:orientation="vertical">

                <View android:layout_width="match_parent" android:layout_height="400dp"
                    android:background="@color/warning" />

                <View android:layout_width="match_parent" android:layout_height="400dp"
                    android:background="@color/error" />
            </LinearLayout>
        </ScrollView>
    </LinearLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```
