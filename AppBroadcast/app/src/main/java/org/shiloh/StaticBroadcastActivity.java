package org.shiloh;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.shiloh.appbroadcast.R;
import org.shiloh.receiver.MyStaticReceiver;

/**
 * 静态广播收发示例
 *
 * @author shiloh
 * @date 2024/9/16 10:43
 * @see org.shiloh.receiver.MyStaticReceiver
 */
public class StaticBroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_static_broadcast);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.findViewById(R.id.btn_send_static_broadcast).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.btn_send_static_broadcast != v.getId()) {
            return;
        }

        // 发送静态广播
        final Intent intent = new Intent(MyStaticReceiver.ACTION_SHOCK);
        // Android8.0之后删除了大部分静态注册，防止退出App后仍在接收广播，
        // 为了让应用能够继续接收静态广播，需要给静态注册的广播指定包名。
        intent.setComponent(new ComponentName(this, MyStaticReceiver.class));
        this.sendBroadcast(intent);
        Toast.makeText(this, "振动广播发送成功", Toast.LENGTH_SHORT)
                .show();
    }
}