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

import java.util.Locale;

/**
 * 计算器 App
 *
 * @author shiloh
 * @date 2024/8/1 22:10
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String LOG_TAG = "Calculator App";

    /**
     * 第一个操作数
     */
    private String firstNum;

    /**
     * 第二个操作数
     */
    private String secondNum;

    /**
     * 运算符
     */
    private String operator;

    /**
     * 计算结果
     */
    private String calcResult;

    /**
     * 显示计算结果的 this.editText 控件
     */
    private EditText editText;

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
        this.firstNum = Constants.EMPTY_STR;
        this.secondNum = Constants.EMPTY_STR;
        this.operator = Constants.EMPTY_STR;
        this.editText = super.findViewById(R.id.result);
        // 给界面上的全部按钮添加点击事件
        // 清除、退格按钮
        super.findViewById(R.id.btn_backspace).setOnClickListener(this);
        super.findViewById(R.id.btn_clear).setOnClickListener(this);
        // 运算符按钮
        super.findViewById(R.id.btn_modulo).setOnClickListener(this);
        super.findViewById(R.id.btn_plus).setOnClickListener(this);
        super.findViewById(R.id.btn_minus).setOnClickListener(this);
        super.findViewById(R.id.btn_multiplication).setOnClickListener(this);
        super.findViewById(R.id.btn_division).setOnClickListener(this);
        // 小数点、等于号
        super.findViewById(R.id.btn_dot).setOnClickListener(this);
        super.findViewById(R.id.btn_equal).setOnClickListener(this);
        // 数字按钮
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
        Log.i(LOG_TAG, msg);

        // 清空当前计算结果
        if (R.id.btn_clear == button.getId()) {
            this.clear();
            return;
        }

        // 退格键
        if (R.id.btn_backspace == button.getId()) {
            this.handleBackspace();
            return;
        }

        // 运算符
        if (Constants.OPERATOR_PATTERN.matcher(btnText).matches()) {
            this.handleInputOperator(btnText);
            return;
        }

        // 数字键
        if (Constants.NUMBER_PATTERN.matcher(btnText).matches()) {
            this.handleInputNumber(btnText);
            return;
        }

        // 小数点
        if (R.id.btn_dot == button.getId()) {
            this.handleInputDot(btnText);
            return;
        }

        // 等于号
        if (R.id.btn_equal == button.getId()) {
            this.handleInputEqual();
        }
    }

    /**
     * 清空当前计算结果
     *
     * @author shiloh
     * @date 2024/8/2 23:04
     */
    private void clear() {
        if (Constants.EMPTY_STR.equals(this.editText.getText().toString())) {
            // 没有可以清除的内容
            this.showToast("没有可以清除的内容");
            return;
        }

        this.firstNum = Constants.EMPTY_STR;
        this.secondNum = Constants.EMPTY_STR;
        this.operator = Constants.EMPTY_STR;
        this.calcResult = Constants.EMPTY_STR;
        this.editText.setHint(R.string.result_hint);
        this.editText.setText(Constants.EMPTY_STR);
    }

    /**
     * 退格
     *
     * @author shiloh
     * @date 2024/8/4 12:15
     */
    private void handleBackspace() {
        if (Constants.EMPTY_STR.equals(this.editText.getText().toString())) {
            this.showToast("没有可以删除的数字/运算符");
            this.editText.setText(Constants.EMPTY_STR);
            this.editText.setHint(R.string.result_hint);
            return;
        }

        // 删除第二个数字
        if (StringUtils.isNotBlank(this.secondNum)) {
            // 第二个数字只有1位
            if (1 == this.secondNum.length()) {
                this.secondNum = Constants.EMPTY_STR;
                this.refreshDisplayExpression();
                return;
            }

            // 逐位删除第二个数字
            this.secondNum = this.secondNum.substring(0, this.secondNum.length() - 1);
            this.refreshDisplayExpression();
            return;
        }

        // 删除运算符
        if (StringUtils.isNotBlank(this.operator)) {
            this.operator = Constants.EMPTY_STR;
            this.refreshDisplayExpression();
            return;
        }

        // 删除第一个数字
        if (1 == this.firstNum.length()) {
            this.firstNum = Constants.EMPTY_STR;
            this.refreshDisplayExpression();
            return;
        }

        // 逐位删除第一个数字
        this.firstNum = this.firstNum.substring(0, this.firstNum.length() - 1);
        this.refreshDisplayExpression();
    }

    /**
     * 运算符处理
     *
     * @param btnText 按钮文本
     * @author shiloh
     * @date 2024/8/4 12:31
     */
    private void handleInputOperator(String btnText) {
        if (StringUtils.isBlank(this.operator)) {
            this.operator = btnText;
            this.refreshDisplayExpression();
            return;
        }

        // 处理重复点击运算符
        if (StringUtils.isBlank(this.calcResult)) {
            return;
        }

        // 已有计算结果了，把计算结果作为第一个数字
        this.firstNum = this.calcResult;
        this.secondNum = Constants.EMPTY_STR;
        this.operator = btnText;
        this.refreshDisplayExpression();
    }

    /**
     * 数字点击处理
     *
     * @author shiloh
     * @date 2024/8/4 12:32
     */
    private void handleInputNumber(String btnText) {
        if (StringUtils.isBlank(this.firstNum)) {
            this.firstNum = btnText;
            this.refreshDisplayExpression();
            return;
        }

        // 多位数输入处理，还没点击运算符，说明输入的是第一个数字
        if (StringUtils.isBlank(this.operator)) {
            this.firstNum += btnText;
            this.refreshDisplayExpression();
            return;
        }

        if (StringUtils.isBlank(this.secondNum)) {
            this.secondNum = btnText;
            this.refreshDisplayExpression();
            return;
        }

        // 多位数输入处理，已经点击了运算符，说明输入的是第二个数字
        this.secondNum += btnText;
        this.refreshDisplayExpression();
    }

    /**
     * 小数点输入处理
     *
     * @param btnText 按钮文本
     * @author shiloh
     * @date 2024/8/4 12:41
     */
    private void handleInputDot(String btnText) {
        if (StringUtils.isNotBlank(this.secondNum)) {
            // 第二个数字已经为浮点数，不允许再输入小数点
            if (this.secondNum.contains(Constants.DOT)) {
                return;
            }

            this.secondNum += btnText;
            this.refreshDisplayExpression();
            return;
        }

        if (StringUtils.isNotBlank(this.firstNum)) {
            // 第一个数字已经为浮点数，不允许再输入小数点
            if (this.firstNum.contains(Constants.DOT)) {
                return;
            }

            this.firstNum += btnText;
            this.refreshDisplayExpression();
            return;
        }

        this.showToast("请至少输入一个数字");
    }

    /**
     * 等号输入处理：求计算结果
     *
     * @author shiloh
     * @date 2024/8/4 15:46
     */
    private void handleInputEqual() {
        if (StringUtils.isBlank(this.firstNum)) {
            this.showToast("请输入第一个数字");
            return;
        }

        if (StringUtils.isBlank(this.operator)) {
            this.showToast("请先输入运算符");
            return;
        }

        if (StringUtils.isBlank(this.secondNum)) {
            this.showToast("请输入第二个数字");
            return;
        }

        this.setCalcResult();
        this.refreshDisplayCalcResult();
    }

    /**
     * 弹出提示信息
     *
     * @param msg 提示信息
     * @author shiloh
     * @date 2024/8/4 12:05
     */
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 刷新当前显示的计算表达式
     *
     * @author shiloh
     * @date 2024/8/4 15:44
     */
    private void refreshDisplayExpression() {
        this.editText.setText(this.getExpression());
    }

    /**
     * 获取计算表达式
     *
     * @return 计算表达式
     * @author shiloh
     * @date 2024/8/4 15:24
     */
    public String getExpression() {
        return this.firstNum + this.operator + this.secondNum;
    }

    /**
     * 计算结果
     *
     * @author shiloh
     * @date 2024/8/4 15:20
     */
    public void setCalcResult() {
        final double firstCalcNum = Double.parseDouble(this.firstNum);
        final double secondCalcNum = Double.parseDouble(this.secondNum);
        Log.i(LOG_TAG, String.format("expression: %s %s %s", firstCalcNum, this.operator, secondCalcNum));
        switch (this.operator) {
            case Constants.PLUS_SIGN:
                this.calcResult = String.valueOf(firstCalcNum + secondCalcNum);
                break;
            case Constants.MINUS_SIGN:
                this.calcResult = String.valueOf(firstCalcNum - secondCalcNum);
                break;
            case Constants.MULTIPLICATION_SIGN:
                this.calcResult = String.valueOf(firstCalcNum * secondCalcNum);
                break;
            case Constants.DIVISION_SIGN:
                this.calcResult = String.valueOf(firstCalcNum / secondCalcNum);
                break;
            case Constants.MODULO_SIGN:
                this.calcResult = String.valueOf(firstCalcNum % secondCalcNum);
                break;
            default:
                this.showToast("请输入运算符");
                break;
        }

        final double result = Double.parseDouble(this.calcResult);
        if (result == Math.floor(result) || result == Math.ceil(result)) {
            // 计算结果为整数
            this.calcResult = String.valueOf(((long) result));
        }
    }

    /**
     * 刷新当前显示的计算结果
     *
     * @author shiloh
     * @date 2024/8/4 15:57
     */
    private void refreshDisplayCalcResult() {

        this.editText.setText(String.format(
                Locale.CHINA, "%s=%s", this.getExpression(), this.calcResult
        ));
    }
}