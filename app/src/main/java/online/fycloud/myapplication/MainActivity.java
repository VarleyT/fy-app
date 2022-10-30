package online.fycloud.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_change).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.login");
            startActivity(intent);
        });
        findViewById(R.id.btn_dy).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.douyin");
            startActivity(intent);
        });
        findViewById(R.id.btn_freeGame).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.freeGame");
            startActivity(intent);
        });
        findViewById(R.id.btn_register).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.register");
            startActivity(intent);
        });
    }
}