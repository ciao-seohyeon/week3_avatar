package com.example.week3_avatar;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week3_avatar.Retrofit.User;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class FourthFriend extends AppCompatActivity {
    private FriendAdapter adapter;
    RecyclerView recyclerView;
    private ArrayList<MyContacts> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourthfriend);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = getContactList();

        adapter = new FriendAdapter(this, userList);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<MyContacts> getContactList() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts._ID
        };

        String[] selectionArgs =null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor cursor =  getContentResolver().query(uri, projection, null,
                selectionArgs, sortOrder);


        LinkedHashSet<MyContacts> hasList = new LinkedHashSet<>();
        ArrayList<MyContacts> contactsList;

        if (cursor.moveToFirst()) {
            do {
                long photo_id = cursor.getLong(2);
                long person = cursor.getLong(3);

                MyContacts myContact = new MyContacts();
                myContact.user_phNumber = cursor.getString(0);
                myContact.user_Name = cursor.getString(1);
                myContact.photo_id = photo_id;
                myContact.person_id = person;

                if (myContact.user_phNumber.startsWith("01")) {
                    hasList.add(myContact);
                    //contactsList.add(myContact);
                    Log.d("<<CONTACTS>>", "name=" + myContact.user_Name + ", phone=" + myContact.user_phNumber);
                }

            } while (cursor.moveToNext());
        }

        contactsList = new ArrayList<MyContacts>(hasList);
        for (int i = 0; i < contactsList.size(); i++) {
            contactsList.get(i).setId(i);
        }

        if (cursor != null) {
            cursor.close();
        }

        return contactsList;
    }

}
