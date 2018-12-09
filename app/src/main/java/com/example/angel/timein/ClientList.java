//
/*
08/12/2018
Author AngieSR

NOTE: This class was based on these tutorials: https://www.simplifiedcoding.net/firebase-realtime-database-crud/
*/
//

package com.example.angel.timein;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ClientList extends ArrayAdapter <Clients> {

    private Activity context;
    private List<Clients> clientList;

    public ClientList(Activity context, List<Clients> clientList){
        super (context, R.layout.list_layout, clientList);
        this.context = context;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //creates layout inflater
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tvName);

        Clients client = clientList.get(position);

        textViewName.setText(client.getClientName());

        return listViewItem;
    }
}
