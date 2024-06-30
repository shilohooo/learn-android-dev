# 图像显示 - ImageView

Android 开发之图像显示

> 参考资料：Android Studio 开发实战：从零基础到 App 上线（第3版）

显示图像需要用到 `ImageView` 控件，先把图片放到 res/drawable 目录下，然后设置 `ImageView` 的 `src`
属性即可。

```xml
<!--
通过 src 属性设置图片资源，contentDescription 属性设置图片描述
ImageView 控件默认居中显示图片，且会自动缩放使图片能够适应控件大小
缩放类型在 XML 文件中通过 android:scaleType 属性设置，默认值为 fitCenter
表示让图像自适应居中显示
-->
<ImageView android:id="@+id/img_goku" android:layout_width="match_parent"
    android:layout_height="200dp" android:scaleType="fitCenter" android:contentDescription="goku"
    android:src="@drawable/goku" />
```

也可以通过 Java 代码进行设置

```java
package org.shiloh.android;

import android.os.Bundle;
import android.widget.ImageView;

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

        // 获取图片视图控件
        final ImageView imageView = super.findViewById(R.id.img_icon);
        // 设置要显示的图片资源
        imageView.setImageResource(R.drawable.icon);
        // 设置图片的缩放类型，这里设置为自适应居中显示
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
}
```

## 图片缩放

图片默认自适应居中显示，可以通过 `android:scaleType` 属性设置缩放类型，缩放类型的取值说明请看下表：

|  XML 中的缩放类型  | ScaleType 类中的缩放类型 |             说 明             |
|:------------:|:-----------------:|:---------------------------:|
|  fitCenter   |    FIT_CENTER     |   默认值，保持宽高比例，缩放图片使其位于视图中间   |
|  centerCrop  |    CENTER_CROP    | 缩放图片使其填满整个视图，超出部分裁剪，并位于视图中间 |
| centerInside |   CENTER_INSIDE   |     保持宽高比例，缩小视图，并位于视图中间     |
|    center    |      CENTER       |      保持图片原始尺寸，并位于视图中间       |
|    fitXY     |      FIT_XY       |  缩放图片使其正好填充满视图（图片可能被拉伸变形）   |
|   fitStart   |     FIT_START     |   保持宽高比例，缩放图片使其位于视图上方或左侧    |
|    fitEnd    |      FIT_END      |   保持宽高比例，缩放图片使其位于视图下方或右侧    |

## 图像按钮 - ImageButton

可以显示图片的按钮，但不能显示文本

```xml
<!--
ImageButton 可以显示图片的按钮，但不能显示文本，派生自 ImageView
可以使用 ImageView 的属性和方法
可以设置前景图和背景图，从而实现图片叠加的效果
-->
<ImageButton
    android:layout_width="match_parent"
    android:contentDescription="icon btn"
    android:layout_height="50dp"
    android:src="@drawable/icon"
    android:scaleType="fitCenter" />
```

如果想同时展示图标和文字，可以使用 Button 控件，并通过以下使用来设置图标：

- drawableTop: 设置图标在文字上方
- drawableLeft: 设置图标在文字左侧
- drawableRight: 设置图标在文字右侧
- drawableBottom: 设置图标在文字下方
- drawablePadding: 设置图标和文字之间的间距

示例代码：

```xml
<!--
带图标的按钮：通过 Button 的 drawableXXX（上下左右）属性实现
-->
<Button
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:drawableTop="@drawable/search"
    android:text="我是带图标的按钮" />
```