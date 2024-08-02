package org.shiloh.android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.android.constant.Constants;
import org.shiloh.android.util.StringUtils;

/**
 * 计算器 App
 *
 * @author shiloh
 * @date 2024/8/1 22:10
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 第一个操作数
     */
    private Double firstNum;

    /**
     * 第二个操作数
     */
    private Double secondNum;

    /**
     * 运算符
     */
    private String operator;

    /**
     * 计算结果
     */
    private String result;

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

        Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();

        // 给界面上的全部按钮添加点击事件
        super.findViewById(R.id.btn_backspace).setOnClickListener(this);
        super.findViewById(R.id.btn_clear).setOnClickListener(this);
        super.findViewById(R.id.btn_square).setOnClickListener(this);
        super.findViewById(R.id.btn_modulo).setOnClickListener(this);
        super.findViewById(R.id.btn_plus).setOnClickListener(this);
        super.findViewById(R.id.btn_minus).setOnClickListener(this);
        super.findViewById(R.id.btn_multiplication).setOnClickListener(this);
        super.findViewById(R.id.btn_division).setOnClickListener(this);

        super.findViewById(R.id.btn_plus_or_minus).setOnClickListener(this);
        super.findViewById(R.id.btn_dot).setOnClickListener(this);
        super.findViewById(R.id.btn_equal).setOnClickListener(this);

        super.findViewById(R.id.btn_one).setOnClickListener(this);
        super.findViewById(R.id.btn_two).setOnClickListener(this);
        super.findViewById(R.id.btn_three).setOnClickListener(this);
        super.findViewById(R.id.btn_four).setOnClickListener(this);
        super.findViewById(R.id.btn_five).setOnClickListener(this);
        super.findViewById(R.id.btn_six).setOnClickListener(this);
        super.findViewById(R.id.btn_seven).setOnClickListener(this);
        super.findViewById(R.id.btn_eight).setOnClickListener(this);
        super.findViewById(R.id.btn_nine).setOnClickListener(this);
        super.findViewById(R.id.btn_zero).setOnClickListener(this);
    }

    /**
     * 点击事件处理
     *
     * @param view 当前点击的控件
     * @author shiloh
     * @date 2024/8/2 22:38
     */
    @Override
    public void onClick(View view) {
        final Button button = (Button) view;
        final String btnText = button.getText().toString();
        final String msg = String.format("current click button text is %s\r", btnText);
        Log.i(super.getString(R.string.app_name), msg);

        // 清空当前计算结果
        if (R.id.btn_clear == button.getId()) {
            this.clear();
            return;
        }

        final EditText editText = (EditText) super.findViewById(R.id.result);
        // 退格键
        if (R.id.btn_backspace == button.getId()) {
            this.result = editText.getText().toString();
            if (Constants.EMPTY_STR.equals(this.result)) {
                return;
            }

            if (this.secondNum != null) {
                this.secondNum = null;
            } else if (StringUtils.isNotBlank(this.operator)) {
                this.operator = null;
            } else {
                this.firstNum = null;
            }

            this.result = this.result.substring(0, this.result.length() - 1);
            editText.setText(this.result);
            return;
        }

        // 数字键
        if (Constants.NUMBER_PATTERN.matcher(btnText).matches()) {
            if (this.firstNum == null) {
                this.firstNum = Double.parseDouble(btnText);
                this.result = btnText;
                editText.setText(this.result);
                return;
            }

            if (this.secondNum == null) {
                this.secondNum = Double.parseDouble(btnText);
                this.result = String.format("%s%s", this.result, btnText);
                editText.setText(this.result);
            }
        }

        // 运算符
        if (Constants.OPERATOR_PATTERN.matcher(btnText).matches()) {
            this.operator = btnText;
            this.result = String.format("%s%s", this.result, btnText);
            editText.setText(this.result);
        }
    }

    /**
     * 清空当前计算结果
     *
     * @author shiloh
     * @date 2024/8/2 23:04
     */
    private void clear() {
        final EditText editText = (EditText) super.findViewById(R.id.result);
        this.firstNum = null;
        this.secondNum = null;
        this.operator = null;
        this.result = Constants.EMPTY_STR;
        editText.setText(this.result);
        editText.setHint(R.string.result_hint);
    }
}