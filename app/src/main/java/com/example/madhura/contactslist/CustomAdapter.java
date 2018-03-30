package com.example.madhura.contactslist;

import  android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madhura on 15-10-2017.
 */

public class CustomAdapter extends ArrayAdapter<Contact> {

    TextView contactItem;
    CheckBox checkbox;

    public CustomAdapter(Context context, int resource, ArrayList<Contact> contactList) {
        super(context, R.layout.item_list_view, contactList);
    }

    /*static class CustomHandler{
        TextView contactname;
        CheckBox checkbox;
    }*/

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        String name;
        //CustomHandler handler;
        //listitem = convertView;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listitem = inflater.inflate(R.layout.item_list_view, parent, false);
        contactItem = (TextView)listitem.findViewById(R.id.list_item);
        checkbox = (CheckBox)listitem.findViewById(R.id.checkbox);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the position of checked items
                getItem(position).setDelete_request(!getItem(position).isDelete_request());
                System.out.println("3403 ---- check box clicked " + getItem(position).getName() + "  " + getItem(position).isDelete_request());
            }
        });

        name = getItem(position).getName();
        contactItem.setText(name);
        return listitem;
    }

    //When the list item is clicked, return the position of the clicked item
    public Contact OnClickList(int position){
        contactItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("3403 ---- list item clicked");
            }
        });
        return getItem(position);
    }
}
