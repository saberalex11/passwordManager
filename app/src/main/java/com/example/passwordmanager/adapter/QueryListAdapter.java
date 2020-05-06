package com.example.passwordmanager.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.passwordmanager.R;
import com.example.passwordmanager.activity.EditActivity;
import com.example.passwordmanager.entity.PasswordBean;
import com.example.passwordmanager.utils.ToastUtils;

import java.util.List;

public class QueryListAdapter extends ArrayAdapter<PasswordBean> implements View.OnClickListener {

    // 子项布局的id
    private int resourceId;

    private PasswordBean selectedPasswordBean;

    public QueryListAdapter(@NonNull Context context, int resource, @NonNull List<PasswordBean> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PasswordBean passwordBean = getItem(position);//获取当前项的实例
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.nameTextView)).setText(getContext().getString(R.string.name) + passwordBean.getName());
        ((TextView) convertView.findViewById(R.id.accountTextView)).setText(getContext().getString(R.string.account) + passwordBean.getAccount());
        ((TextView) convertView.findViewById(R.id.passwordTextView)).setText(getContext().getString(R.string.password) + passwordBean.getPassword());

        Button btn = (Button) convertView.findViewById(R.id.deleteButton);
        btn.setTag(position);
        btn.setOnClickListener(this);

        Button editBtn = (Button) convertView.findViewById(R.id.editButton);
        editBtn.setOnClickListener((v)->{
            Intent intent = new Intent(getContext(), EditActivity.class);
            intent.putExtra("name",passwordBean.getName());
            intent.putExtra("account",passwordBean.getAccount());
            intent.putExtra("password",passwordBean.getPassword());
            getContext().startActivity(intent);
        });

        Button copyBtn = (Button) convertView.findViewById(R.id.copyButton);
        copyBtn.setOnClickListener((v)->{
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(passwordBean.getPassword());
            ToastUtils.show(getContext(),getContext().getString(R.string.copy_success));
        });

        Button copyAccountBtn = (Button) convertView.findViewById(R.id.copyAccountButton);
        copyAccountBtn.setOnClickListener((v)->{
            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(passwordBean.getAccount());
            ToastUtils.show(getContext(),getContext().getString(R.string.copy_success));
        });

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.deleteButton) {
            int tag = (int) v.getTag();
            selectedPasswordBean = getItem(tag);//获取当前项的实例
            if (selectedPasswordBean != null) {
                this.confirm();
            }
        }
    }

    private void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getContext().getString(R.string.delete_confirm));
        builder.setMessage(getContext().getString(R.string.delete_confirm_message));

        builder.setNegativeButton(getContext().getText(R.string.no), (DialogInterface di, int pos) -> {
        });

        builder.setPositiveButton(getContext().getText(R.string.yes), (DialogInterface di, int pos) -> {
            this.remove(selectedPasswordBean);
            selectedPasswordBean.delete();
            ToastUtils.show(getContext(), getContext().getString(R.string.delete_success));
            selectedPasswordBean = null;
        });
        builder.create().show();
    }
}
