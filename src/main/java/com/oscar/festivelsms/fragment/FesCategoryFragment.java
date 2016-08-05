package com.oscar.festivelsms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.oscar.festivelsms.ChooseMsgActivity;
import com.oscar.festivelsms.R;
import com.oscar.festivelsms.bean.Festivel;
import com.oscar.festivelsms.bean.FestivelLab;


/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class FesCategoryFragment extends Fragment {
    public static final String ID_FESTIVEL = "festivelId";

    private GridView mGridView;
    private ArrayAdapter<Festivel> mAdapter;
    private LayoutInflater mInflater;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fescategory, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mGridView = (GridView) view.findViewById(R.id.gv);

        mInflater = LayoutInflater.from(getActivity());

        mAdapter = new ArrayAdapter<Festivel>(getActivity(), -1, FestivelLab.getInstance().getFestivels()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder = null;
                if(convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_festivel, parent, false);
                    viewHolder.tvFestivel = (TextView) convertView.findViewById(R.id.tv_festivel);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                viewHolder.tvFestivel.setText(getItem(position).getName());

                return convertView;
            }
        };

        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChooseMsgActivity.class);
                intent.putExtra(ID_FESTIVEL, mAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
    }



    class ViewHolder {
        public TextView tvFestivel;
    }
}
