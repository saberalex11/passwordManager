package com.example.passwordmanager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.R;
import com.example.passwordmanager.constants.PasswordType;
import com.example.passwordmanager.entity.PasswordBean;
import com.example.passwordmanager.utils.ToastUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private final int passwordMinLength = 6;
    private final int passwordMaxLength = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void add(View view) {
        String name = ((EditText)findViewById(R.id.nameText)).getText().toString();
        String account = ((EditText)findViewById(R.id.accountText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();

        if ("".equals(name)) {
            ToastUtils.show(this, this.getString(R.string.name_empty));
            return;
        } else {
            //校验唯一性
            int result = LitePal.where("name = ?", name).count(PasswordBean.class);
            if (result != 0) {
                ToastUtils.show(this, this.getString(R.string.repeat_name));
                return;
            }
        }
        if ("".equals(account)) {
            ToastUtils.show(this, this.getString(R.string.account_empty));
            return;
        }
        if ("".equals(password)) {
            ToastUtils.show(this, this.getString(R.string.password_empty));
            return;
        }
        PasswordBean passwordBean = new PasswordBean();
        passwordBean.setName(name);
        passwordBean.setAccount(account);
        passwordBean.setPassword(password);
        passwordBean.save();
        this.clearText();
        ToastUtils.show(this, this.getString(R.string.save_success));



    }

    public void generatePassword(View view) {

        EditText passwordLengthText = this.findViewById(R.id.passwordLengthText);
        String passwordLength = passwordLengthText.getText().toString();
        if ("".equals(passwordLength)) {
            ToastUtils.show(this, this.getString(R.string.password_length_empty));
            return;
        }
        int passwordLengthInteger = Integer.valueOf(passwordLength);
        if (passwordLengthInteger < passwordMinLength || passwordLengthInteger > passwordMaxLength) {
            ToastUtils.show(this, this.getString(R.string.password_length_tips));
            return;
        }
        CheckBox upperCheckBox = this.findViewById(R.id.upperCheckBox);
        CheckBox symbolCheckBox = this.findViewById(R.id.symbolCheckBox);

        EditText passwordText = this.findViewById(R.id.passwordText);
        passwordText.setText(this.generatePassword(passwordLengthInteger, upperCheckBox.isChecked(), symbolCheckBox.isChecked()));


    }

    private void clearText(){
        EditText name = (EditText)findViewById(R.id.nameText);
        EditText account = (EditText)findViewById(R.id.accountText);
        EditText password = (EditText)findViewById(R.id.passwordText);
        EditText passwordLengthText = findViewById(R.id.passwordLengthText);
        name.setText(null);
        account.setText(null);
        password.setText(null);
        passwordLengthText.setText(null);

    }

    private String generatePassword(int length, boolean isIncludeUpper, boolean isIncludeSymbol) {


        List<Integer> typeList = this.initTypeList(isIncludeUpper, isIncludeSymbol);
        List<Integer> generateList = new ArrayList<>();

        //生成一个都包含的
        do {
            generateList.clear();
            for (int i = 0; i < length; i++) {
                generateList.add(typeList.get((int)(Math.random() * typeList.size())));
            }
        } while (!this.isAllInclude(generateList, typeList));

        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int type = generateList.get(i);
            passwordBuilder.append(this.getPasswordString(type));

        }
        return passwordBuilder.toString();
    }

    private String getPasswordString(int type){
        String[] numberArray = this.getResources().getStringArray(R.array.number);
        String[] lowerArray = this.getResources().getStringArray(R.array.lower);
        String[] upperArray = this.getResources().getStringArray(R.array.upper);
        String[] symbolArray = this.getResources().getStringArray(R.array.symbol);
        String c = null;
        if(type == PasswordType.LOWER.getType()){
            c = lowerArray[(int)(Math.random() * lowerArray.length)];
        }else if(type == PasswordType.UPPER.getType()){
            c = upperArray[(int)(Math.random() * upperArray.length)];
        }else if(type == PasswordType.NUMBER.getType()){
            c = numberArray[(int)(Math.random() * numberArray.length)];
        }else if(type == PasswordType.SYMBOL.getType()){
            c = symbolArray[(int)(Math.random() * symbolArray.length)];
        }
        return c;
    }

    private boolean isAllInclude(List<Integer> generateList, List<Integer> typeList) {

        for (int type : typeList) {
            if (!generateList.contains(type)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 初始化生成密码的所需求的字符类型
     *
     * @param isIncludeUpper
     * @param isIncludeSymbol
     * @return
     */
    private List<Integer> initTypeList(boolean isIncludeUpper, boolean isIncludeSymbol) {
        List<Integer> typeList = new ArrayList<>();
        typeList.add(PasswordType.LOWER.getType());
        typeList.add(PasswordType.NUMBER.getType());
        if (isIncludeSymbol) {
            typeList.add(PasswordType.SYMBOL.getType());
        }
        if (isIncludeUpper) {
            typeList.add(PasswordType.UPPER.getType());
        }
        return typeList;
    }

}
