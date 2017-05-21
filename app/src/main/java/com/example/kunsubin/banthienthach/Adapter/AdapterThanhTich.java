package com.example.kunsubin.banthienthach.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kunsubin.banthienthach.Activity.DiemCaoNhat;
import com.example.kunsubin.banthienthach.CSDL.ThanhTich;
import com.example.kunsubin.banthienthach.R;

import java.util.List;

/**
 * Created by kunsubin on 5/21/2017.
 */

public class AdapterThanhTich extends BaseAdapter {
    List<ThanhTich> thanhTichList;
    Context context;
    private static LayoutInflater inflater=null;
    public AdapterThanhTich(DiemCaoNhat diemCaoNhat, List<ThanhTich> thanhTichList) {
        // TODO Auto-generated constructor stub
        this.thanhTichList=thanhTichList;
        context=diemCaoNhat;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return thanhTichList.size();
    }

    @Override
    public ThanhTich getItem(int position) {
        // TODO Auto-generated method stub
        return thanhTichList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int i) {
        return i;
    }
    public class Holder
    {
        TextView user;
        TextView score;
        TextView gold;
        public Holder(View view){
           this.user=(TextView)view.findViewById(R.id.user);
            this.score=(TextView)view.findViewById(R.id.score);
            this.gold=(TextView)view.findViewById(R.id.gold);
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item, null);
            holder=new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(Holder) convertView.getTag();
        }
        holder=(Holder) convertView.getTag();

        holder.user.setText(thanhTichList.get(position).getUser());
        holder.score.setText(thanhTichList.get(position).getScore());
        holder.gold.setText(thanhTichList.get(position).getGold());

        return convertView;
    }
}
