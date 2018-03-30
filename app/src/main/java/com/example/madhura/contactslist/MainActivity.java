package com.example.madhura.contactslist;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends Activity implements Communicator {

    ContactList contactListFragment;
    ContactProfile contactProfileFragment;
    AddContact addContactFragment;
    FragmentManager manager;
    static Contact currentProfile;
    static ArrayList<Contact> contactDetailList;

    static String current_nameDetails;
    static int current_phoneDetails;

    private static final int REQUEST_CODE = 123;
    private static final int RESULT_CODE = 321;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if activity is already created
        if (savedInstanceState != null){
            String result = savedInstanceState.getString("savedInstance");
            if(result != null && result.equals("saveProfile")){
                Contact c = (Contact) savedInstanceState.getSerializable("showProfile");

                respondForProfile(c);
            }
        }
        manager = getFragmentManager();
        contactListFragment = (ContactList) manager.findFragmentById(R.id.fragment);
        contactListFragment.setCommunicator(this);
    }

    @Override
    public void respondForProfile(Contact contactItem) {
        //landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            currentProfile = contactItem;
            FragmentTransaction transaction = manager.beginTransaction();
            contactProfileFragment = new ContactProfile();
            contactProfileFragment.setLocalData(contactItem);
            transaction.replace(R.id.group, contactProfileFragment, "fragment_profile");
            transaction.commit();
        }
        //portrait
        else {
            Intent intent = new Intent(this, ContactProfileActivity.class);
            intent.putExtra("contactItem", (Serializable) contactItem);
            intent.putExtra("uniqueID", "fromMainActivity");
            startActivityForResult(intent, RESULT_CODE);
        }
    }

    @Override
    public void add(ArrayList<Contact> contactList) {
        //landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            FragmentTransaction transaction = manager.beginTransaction();
            addContactFragment = new AddContact();
            addContactFragment.setRelations(contactList);
            addContactFragment.setLandCommunicator(this);
            transaction.replace(R.id.group, addContactFragment, "fragment_details");
            transaction.commit();

        }
        //portrait
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(this, AddContactActivity.class);
            intent.putExtra("contactList", (Serializable) contactList);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void addWithText(ArrayList<Contact> contactList, String name, int phone) {
        //landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentTransaction transaction = manager.beginTransaction();
            addContactFragment = new AddContact();
            addContactFragment.setRelations(contactList);
            addContactFragment.setTextView(name, phone);
            addContactFragment.setLandCommunicator(this);
            transaction.replace(R.id.group, addContactFragment, "fragment_details");
            transaction.commit();
        }
        //portrait
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(this, AddContactActivity.class);
            intent.putExtra("contactList", (Serializable) contactList);
            intent.putExtra("receivedName", current_nameDetails);
            intent.putExtra("receivedPhone", current_phoneDetails);
            intent.putExtra("returnView", true);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void respondWithContactLand(Contact contact_item) {
        contactListFragment.addNewItem(contact_item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //respond request
        if (requestCode == RESULT_CODE){
            if (intent != null){
                boolean success = intent.getBooleanExtra("fromProfileActivity", false);
                if (success == true){
                    Contact contactProfile = (Contact) intent.getSerializableExtra("contactItem");
                    if (contactProfile != null){
                        respondForProfile(contactProfile);
                    }
                }
            }
        }

        //add request
        if (requestCode == REQUEST_CODE){
            if (intent != null) {
                boolean success = intent.getBooleanExtra("fromDetailsActivity", false);
                if (success == true){
                    String temp_name;
                    int temp_phone;

                    ArrayList<Contact> temp_contactList = (ArrayList<Contact>) intent.getSerializableExtra("relationFromDetails");
                    temp_name = intent.getStringExtra("nameFromDetails");
                    temp_phone = intent.getIntExtra("phoneFromDetails", 0);
                    contactDetailList = temp_contactList;

                    if (temp_contactList != null){
                        addWithText(temp_contactList, temp_name, temp_phone);
                    }
                }
                else {
                    Contact contactItem = (Contact) intent.getSerializableExtra("contactIntentItem");
                    if (contactItem != null){
                        //add the item to the contact list
                        contactListFragment.addNewItem(contactItem);
                    }
                }
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Contact Detail
            AddContact myFrag;
            myFrag = (AddContact) getFragmentManager().findFragmentByTag("fragment_details");
            if (myFrag != null && myFrag.isVisible()) {
                String saved = "saveDetails";
                outState.putString("saveInstance", saved);
                outState.putSerializable("showDetails", (Serializable) contactDetailList);
            }

            //Contact Profile
            ContactProfile myFrag1;
            myFrag1 = (ContactProfile) getFragmentManager().findFragmentByTag("fragment_profile");
            if (myFrag1 != null && myFrag1.isVisible()) {
                String saved;
                saved = "saveProfile";
                outState.putString("saveInstance", saved);
                outState.putSerializable("showProfile", (Serializable) currentProfile);
            }
        }
    }

    /*public void sharedPreferences(ArrayList<Contact> contactList){
        SharedPreferences app_pref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = app_pref.edit();
        //GSON
        editor.putString("contactList", new Gson().toJson(contactList));
        editor.commit();
    }*/
}
