package com.example.madhura.contactslist;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Madhura on 17-10-2017.
 */

public class AddContactActivity extends Activity implements AddContact.ACCommunicator{

    //declarations
    AddContact addContactFragmentNew;
    ArrayList <Contact> contactList;
    FragmentManager manager;

    //create temp variables for storing the current values
    public static String current_name;
    public static int current_phone;
    public static ArrayList<Contact> current_relationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Intent intent = new Intent();
            intent.putExtra("fromDetailsActivity", true);
            intent.putExtra("relationFromDetail" ,(Serializable) current_relationList);
            intent.putExtra("nameFromDetails", current_name);
            intent.putExtra("phoneFromDetails", current_phone);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        //set the view to activity_contact_detail.xml
        setContentView(R.layout.activity_contact_detail);
        //to create a link between different activities
        Intent intent = getIntent();
        boolean returnView;

        contactList = (ArrayList<Contact>) intent.getSerializableExtra("contactList");
        manager = getFragmentManager();
        addContactFragmentNew = (AddContact) manager.findFragmentById(R.id.fragment2);
        returnView = intent.getBooleanExtra("returnView", false);
        //if there is an extended data
        if(returnView == true){
            String local_currentname;
            int local_currentphone;

            local_currentname = intent.getStringExtra("receivedName");
            local_currentphone = intent.getIntExtra("receivedPhone", 0);
            addContactFragmentNew.setTextView(local_currentname, local_currentphone);
        }

        addContactFragmentNew.setRelations(contactList);
        addContactFragmentNew.setAddContact_communicator(this);
        addContactFragmentNew.displayRelations();

    }


    @Override
    public void addNewContact(Contact contact_item) {
        Intent sendIntent = new Intent();
        sendIntent.putExtra("contactIntentItem", contact_item);
        setResult(RESULT_OK, sendIntent);
        finish();
    }
}
