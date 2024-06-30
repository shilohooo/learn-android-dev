package org.shiloh.android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.android.util.DateUtils;

public class ActivityButtonEnable extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_button_enable);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        super.findViewById(R.id.btn_enable).setOnClickListener(this);
        super.findViewById(R.id.btn_disable).setOnClickListener(this);
        super.findViewById(R.id.btn_test).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        final Button testBtn = super.findViewById(R.id.btn_test);
        if (R.id.btn_enable == view.getId()) {
            testBtn.setEnabled(true);
            return;
        }

        if (R.id.btn_disable == view.getId()) {
            testBtn.setEnabled(false);
            return;
        }

        final TextView textView = super.findViewById(R.id.tv_test);
        textView.setText(String.format("您在 %s 点击了测试按钮:)", DateUtils.getNowStr()));
    }
}