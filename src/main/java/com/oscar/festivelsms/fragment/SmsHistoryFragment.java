package com.oscar.festivelsms.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oscar.festivelsms.R;
import com.oscar.festivelsms.bean.SendedMsg;
import com.oscar.festivelsms.db.SmsProvider;
import com.oscar.festivelsms.view.FlowLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class SmsHistoryFragment extends ListFragment {
    private static final int LOADER_ID = 1;
    private LayoutInflater mLayoutInflater;
    private CursorAdapter mAdapter;

    private DateFormat mDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutInflater = LayoutInflater.from(getActivity());

        initLoader();

        setupListAdapter();
    }

    private void setupListAdapter() {
        mAdapter = new CursorAdapter(getActivity(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = mLayoutInflater.inflate(R.layout.item_sended_msg, parent, false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
                FlowLayout flContacts = (FlowLayout) view.findViewById(R.id.fl_contacts);
                TextView tvFes = (TextView) view.findViewById(R.id.tv_fes);
                TextView tvDate = (TextView) view.findViewById(R.id.tv_date);

                tvMsg.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_MSG)));
                tvFes.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_FESTIVALNAME)));
                long dateLong = cursor.getLong(cursor.getColumnIndex(SendedMsg.COLUMN_DATE));
                tvDate.setText(parseDate(dateLong));
                String names = cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_NAMES));
                if(TextUtils.isEmpty(names)) {
                    return;
                }
                flContacts.removeAllViews();
                for(String name : names.split(":")) {
                    addTag(name, flContacts);
                }
            }
        };
        setListAdapter(mAdapter);
    }

    private String parseDate(long dateLong) {
        return mDateformat.format(dateLong);
    }

    private void addTag(String name, FlowLayout flContacts) {
        TextView tvContacts = (TextView) mLayoutInflater.inflate(R.layout.tag, flContacts, false);
        tvContacts.setText(name);
        flContacts.addView(tvContacts);
    }

    private void initLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader cursorLoader = new CursorLoader(getActivity(), SmsProvider.URI_SMS_ALL, null, null, null, null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if(loader.getId() == LOADER_ID) {
                    mAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);
            }
        });
    }
}
