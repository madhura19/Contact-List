package com.example.madhura.contactslist;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by Madhura on 17-10-2017.
 */

public class ContactProfileActivity extends Activity {

    Contact contactItem;
    static Contact sendContact_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Intent intent = new Intent();
            if (sendContact_main != null){
                intent.putExtra("fromProfileActivity", true);
                intent.putExtra("contactItem", (Serializable) sendContact_main);
            }
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_contact_profile);
        Intent intent = getIntent();
        String uniqueID = intent.getStringExtra("uniqueID");

        if (uniqueID.equals("fromMainActivity")){
            contactItem = (Contact) intent.getSerializableExtra("contactItem");
        }
        else if (uniqueID.equals("fromACActivity")){
            contactItem = (Contact) intent.getSerializableExtra("contactItem");
        }
        else if (uniqueID.equals("fromCPActivity")){
            contactItem = (Contact) intent.getSerializableExtra("contactItem");
        }


        ContactProfile cont_prof = (ContactProfile) getFragmentManager().findFragmentById(R.id.fragment3);
        //check that the profile is not empty
        if (cont_prof != null){
            cont_prof.changeLocalData(contactItem);
        }
    }

}
