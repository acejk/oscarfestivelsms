package com.oscar.festivelsms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oscar.festivelsms.bean.Festivel;
import com.oscar.festivelsms.bean.FestivelLab;
import com.oscar.festivelsms.bean.Msg;
import com.oscar.festivelsms.bean.SendedMsg;
import com.oscar.festivelsms.biz.SmsBiz;
import com.oscar.festivelsms.view.FlowLayout;

import java.util.HashSet;

public class SendMsgActivity extends AppCompatActivity {
    public static final String KEY_FESTIVEL_ID = "festivelId";
    public static final String KEY_MSG_ID = "msgId";
    private static final int CODE_REQUEST = 1;

    private EditText mEtMsg;
    private Button mBtnAdd;
    private FlowLayout mFlContacts;
    private FloatingActionButton mFabSend;
    private View mLayoutLoading;

    private int mFesId;//节日id
    private int mMsgId;//短信id

    private Festivel mFestivel;//节日
    private Msg mMsg;//短信

    private HashSet<String> mContactNames = new HashSet<String>();//联系人
    private HashSet<String> mContactNums = new HashSet<String>();//联系人电话

    private LayoutInflater mInflater;

    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_DELEVER_MSG = "ACTION_DELEVER_MSG";

    private PendingIntent mSendPi;
    private PendingIntent mDeleverPi;

    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeleverBroadcastReceiver;

    private SmsBiz mSmsBiz;

    private int mMsgSendCount;//成功接收到短信的个数
    private int mTotalSendCount;//发送短信的总个数



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        initDatas();

        initViews();

        initEvents();

        initReceiver();


    }

    private void initReceiver() {
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deleverIntent = new Intent(ACTION_DELEVER_MSG);
        mDeleverPi = PendingIntent.getBroadcast(this, 0, deleverIntent, 0);

        /**
         * 注册广播
         */
        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mMsgSendCount++;
                if(getResultCode() == RESULT_OK) {
                    Log.e("TAG", "短信发送成功！" + (mMsgSendCount + "/" + mTotalSendCount));
                } else {
                    Log.e("TAG", "短信发送失败！");
                }
                Toast.makeText(SendMsgActivity.this, (mMsgSendCount + "/" + mTotalSendCount) + "短信发送成功", Toast.LENGTH_SHORT).show();

                if(mMsgSendCount == mTotalSendCount) {
                    finish();
                }
            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeleverBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               Log.e("TAG", "联系人已经成功接收到我们的短信！");
            }
        }, new IntentFilter(ACTION_DELEVER_MSG));
    }

    /**
     * 添加联系人
     */
    private void initEvents() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);
            }
        });

        /**
         * 发送短信
         */
        mFabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContactNums.size() == 0) {
                    Toast.makeText(SendMsgActivity.this, "请选择联系人", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = mEtMsg.getText().toString();
                if(TextUtils.isEmpty(msg)) {
                    Toast.makeText(SendMsgActivity.this, "短信内容不能为空！", Toast.LENGTH_SHORT).show();
                }
                mLayoutLoading.setVisibility(View.VISIBLE);
                mTotalSendCount = mSmsBiz.sendMsg(mContactNums, buildSendMsg(msg), mSendPi, mDeleverPi);
                mMsgSendCount = 0;
            }
        });
    }

    private SendedMsg buildSendMsg(String msg) {
        SendedMsg sendedMsg = new SendedMsg();
        sendedMsg.setMsg(msg);
        sendedMsg.setFestivalName(mFestivel.getName());
        String names = "";
        for(String name : mContactNames) {
            names += name + ":";
        }
        sendedMsg.setNames(names.substring(0, names.length() - 1));
        String numbers = "";
        for(String number : mContactNums) {
            numbers += number;
        }
        sendedMsg.setNumbers(numbers.substring(0, numbers.length() - 1));

        return sendedMsg;
    }

    private void initDatas() {
        mSmsBiz = new SmsBiz(this);

        mFesId = getIntent().getIntExtra(KEY_FESTIVEL_ID, -1);
        mMsgId = getIntent().getIntExtra(KEY_MSG_ID, -1);

        mFestivel = FestivelLab.getInstance().getFestivelById(mFesId);
        setTitle(mFestivel.getName());

        mInflater = LayoutInflater.from(this);
    }

    private void initViews() {
        mEtMsg = (EditText) findViewById(R.id.et_msg);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mFlContacts = (FlowLayout) findViewById(R.id.fl_contacts);
        mFabSend = (FloatingActionButton) findViewById(R.id.fab_send);
        mLayoutLoading = findViewById(R.id.fl_loading);

        if(mMsgId != -1) {
            mMsg = FestivelLab.getInstance().getMsgById(mMsgId);
            mEtMsg.setText(mMsg.getContent());
        }

        mLayoutLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_REQUEST) {
            if(resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactNum = getContactNum(cursor);
                if(!TextUtils.isEmpty(contactNum)) {
                    mContactNames.add(contactName);
                    mContactNums.add(contactNum);

                    addTag(contactName);
                }
            }
        }
    }

    /**
     * 添加联系人标签
     * @param contactName
     */
    private void addTag(String contactName) {
        TextView tvContact = (TextView) mInflater.inflate(R.layout.tag, mFlContacts, false);
        tvContact.setText(contactName);
        mFlContacts.addView(tvContact);

    }

    /**
     * 获取联系人电话
     * @param cursor
     * @return
     */
    private String getContactNum(Cursor cursor) {
        String contactNum = null;
        try {
            int numCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER));
            contactNum = null;
            if(numCount > 0) {
                int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                phoneCursor.moveToFirst();
                contactNum = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneCursor.close();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactNum;
    }

    /**
     * 从选择短信界面到发送短信界面
     * @param context
     * @param fesId
     * @param msgId
     */
    public static void toActivity(Context context, int fesId, int msgId) {
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(KEY_FESTIVEL_ID, fesId);
        intent.putExtra(KEY_MSG_ID, msgId);
        context.startActivity(intent);
    }

    /**
     * 取消注册的广播
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeleverBroadcastReceiver);
    }
}
