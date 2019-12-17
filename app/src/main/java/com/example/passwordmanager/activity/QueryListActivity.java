package com.example.passwordmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.passwordmanager.R;
import com.example.passwordmanager.adapter.QueryListAdapter;
import com.example.passwordmanager.entity.PasswordBean;
import com.example.passwordmanager.utils.ToastUtils;

import org.litepal.LitePal;

import java.util.List;

public class QueryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list);
        List<PasswordBean> all = LitePal.findAll(PasswordBean.class);
        this.flushList(all);
    }

    public void query(View view) {
        String condition = ((TextView) this.findViewById(R.id.conditionText)).getText().toString();
        if ("".equals(condition)) {
            ToastUtils.show(this, this.getString(R.string.condition_empty));
            return;
        }
        List<PasswordBean> all = LitePal.where("name like ?", "%" + condition + "%").find(PasswordBean.class);
        this.flushList(all);
    }

    private void flushList(List<PasswordBean> data) {
        QueryListAdapter adapter = new QueryListAdapter(this, R.layout.password_show_list, data);
        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
    }
}
