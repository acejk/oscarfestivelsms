package com.oscar.festivelsms.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.oscar.festivelsms.bean.SendedMsg;
import com.oscar.festivelsms.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class SmsBiz {
    private Context mContext;

    public SmsBiz(Context context) {
        this.mContext = context;
    }

    /**
     * 单个联系人发送短信
     * @param number
     * @param msg
     * @param sentPi
     * @param deliverPi
     * @return
     */
    public int sendMsg(String number, String msg, PendingIntent sentPi, PendingIntent deliverPi) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(msg);
        for(String content : contents) {
            smsManager.sendTextMessage(number, null, content, sentPi, deliverPi);
        }
        return contents.size();
    }

    /**
     * 多个联系人发送短信
     * @param numbers
     * @param msg
     * @param sentPi
     * @param deliverPi
     * @return
     */
    public int sendMsg(Set<String> numbers, SendedMsg msg, PendingIntent sentPi, PendingIntent deliverPi) {
        save(msg);
        int result = 0;
        for(String number : numbers) {
            int count = sendMsg(number, msg.getMsg(), sentPi, deliverPi);
            result += count;
        }
        return result;
    }

    /**
     * 向数据库插入数据
     */

    private void save(SendedMsg sendedMsg) {
        sendedMsg.setDate(new Date());
        ContentValues values = new ContentValues();
        values.put(SendedMsg.COLUMN_DATE, sendedMsg.getDate().getTime());
        values.put(SendedMsg.COLUMN_MSG, sendedMsg.getMsg());
        values.put(SendedMsg.COLUMN_NUMBERS, sendedMsg.getNumbers());
        values.put(SendedMsg.COLUMN_NAMES, sendedMsg.getNames());
        values.put(SendedMsg.COLUMN_FESTIVALNAME, sendedMsg.getFestivalName());

        mContext.getContentResolver().insert(SmsProvider.URI_SMS_ALL, values);
    }
}
