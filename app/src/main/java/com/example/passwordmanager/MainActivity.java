package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.activity.MainFrameActivity;
import com.example.passwordmanager.activity.SetPasswordActivity;
import com.example.passwordmanager.entity.RootPassword;
import com.example.passwordmanager.utils.ToastUtils;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int count = LitePal.count(RootPassword.class);
        if(count == 0){
            Intent intent = new Intent(this, SetPasswordActivity.class);
            startActivity(intent);
        }
    }

    public void login(View view) {
        EditText passwordText = this.findViewById(R.id.passwordInput);
        String rootPassword = passwordText.getText().toString();

        if ("".equals(rootPassword)) {
            ToastUtils.show(this, this.getString(R.string.input_password));
            return;
        }
        RootPassword rootPasswordEntity = LitePal.where("rootPassword = ?", rootPassword)
                .findFirst(RootPassword.class);
        if(rootPasswordEntity == null){
            ToastUtils.show(this, this.getString(R.string.wrong_password));
            return;
        }
        ToastUtils.show(this, this.getString(R.string.login_success));
        Intent intent = new Intent(this, MainFrameActivity.class);
        startActivity(intent);
    }

    public void resetPassword(View view){
        count ++;
        if(count == 7){
            RootPassword first = LitePal.findFirst(RootPassword.class);
            if(first != null){
                first.delete();
                ToastUtils.show(this, this.getString(R.string.reset_password));
                Intent intent = new Intent(this, SetPasswordActivity.class);
                startActivity(intent);
            }
            count = 0;
        }

    }
}
