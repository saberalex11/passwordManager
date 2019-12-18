package com.example.passwordmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
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
            List<PasswordBean> all = LitePal.findAll(PasswordBean.class);
            this.flushList(all);
        }else{
            List<PasswordBean> all = LitePal.where("name like ?", "%" + condition + "%").find(PasswordBean.class);
            this.flushList(all);
        }

    }

    private void flushList(List<PasswordBean> data) {

        ListView listView = ((ListView) findViewById(R.id.listView));
        ListAdapter adapter = listView.getAdapter();
        if(adapter == null){
            adapter = new QueryListAdapter(this, R.layout.password_show_list, data);
            listView.setAdapter(adapter);
        }else{
            QueryListAdapter queryListAdapter = (QueryListAdapter)adapter;
            queryListAdapter.clear();
            queryListAdapter.addAll(data);
            queryListAdapter.notifyDataSetChanged();
        }

    }
}
