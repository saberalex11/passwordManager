package com.example.passwordmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.passwordmanager.R;
import com.example.passwordmanager.entity.RootPassword;
import com.example.passwordmanager.utils.ToastUtils;

import org.litepal.LitePal;

public class SetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        this.checkJump();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.checkJump();
    }

    private void checkJump(){
        int count = LitePal.count(RootPassword.class);
        if(count != 0){
            Intent intent = new Intent(this, MainFrameActivity.class);
            startActivity(intent);
        }
    }

    public void setPassword(View view){
        EditText passwordText = this.findViewById(R.id.setPasswordInput);
        String newRootPassword = passwordText.getText().toString();

        if ("".equals(newRootPassword)) {
            ToastUtils.show(this, this.getString(R.string.input_password));
            return;
        }
        RootPassword rootPassword = new RootPassword();
        rootPassword.setRootPassword(newRootPassword);
        rootPassword.save();
        ToastUtils.show(this, this.getString(R.string.set_password_success));
        Intent intent = new Intent(this, MainFrameActivity.class);
        startActivity(intent);
    }
}
