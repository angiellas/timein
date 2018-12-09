/*
08/12/2018
Author AngieSR

NOTE: This class was based on these tutorials: https://www.simplifiedcoding.net/firebase-realtime-database-crud/
*/

package com.example.angel.timein;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ShiftList extends ArrayAdapter<Shift> {

    private Activity context;
    private List<Shift> shiftList;

    public ShiftList(Activity context, List<Shift> shiftList){
        super(context, R.layout.list_shifts, shiftList);
        this.context = context;
        this.shiftList = shiftList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View lvItem = inflater.inflate(R.layout.list_shifts, null, true);

        TextView tvDate = (TextView) lvItem.findViewById(R.id.tvDate);
        TextView tvTimeStart = (TextView) lvItem.findViewById(R.id.tvTimeStart);
        TextView tvTimeEnd = (TextView) lvItem.findViewById(R.id.tvTimeEnd);

        Shift shift = shiftList.get(position);

        tvDate.setText(shift.getDate());
        tvTimeStart.setText(shift.getTimeStart());
        tvTimeEnd.setText(shift.getTimeEnd());

        return lvItem;
    }
}
