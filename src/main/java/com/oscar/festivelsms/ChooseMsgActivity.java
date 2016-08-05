package com.oscar.festivelsms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.oscar.festivelsms.bean.FestivelLab;
import com.oscar.festivelsms.bean.Msg;
import com.oscar.festivelsms.fragment.FesCategoryFragment;


public class ChooseMsgActivity extends AppCompatActivity {
    private ListView mLvMsg;
    private ArrayAdapter<Msg> mAdapter;

    private FloatingActionButton mFabToSend;
    private int mFesId;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);

        mFesId = getIntent().getIntExtra(FesCategoryFragment.ID_FESTIVEL, -1);

        mInflater = LayoutInflater.from(this);

        setTitle(FestivelLab.getInstance().getFestivelById(mFesId).getName());

        initViews();

        initEvents();

    }

    private void initEvents() {
        mFabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMsgActivity.toActivity(ChooseMsgActivity.this, mFesId, -1);
            }
        });
    }

    private void initViews() {
        mLvMsg = (ListView) findViewById(R.id.lv_msg);
        mFabToSend = (FloatingActionButton) findViewById(R.id.fab);

        mLvMsg.setAdapter(mAdapter = new ArrayAdapter<Msg>(this, -1, FestivelLab.getInstance().getMsgsByFestivelId(mFesId))
        {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder = null;
                if(convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_msg, parent, false);
                    viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                    viewHolder.btnToSend = (Button) convertView.findViewById(R.id.btn_toSend);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.tvContent.setText("  " + getItem(position).getContent());
                viewHolder.btnToSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMsgActivity.toActivity(ChooseMsgActivity.this, mFesId, getItem(position).getId());
                    }
                });

                return convertView;
            }
        });
    }

    class ViewHolder {
        public TextView tvContent;
        public Button btnToSend;
    }
}
