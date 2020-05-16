package com.example.homework8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayAdapter<String> adapter;
    List<String> contactsList=new ArrayList<>();
    Button backup_button,delete_button,restore_button;
    int numOfRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView contactsView=(ListView)findViewById(R.id.contacts_view);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactsList);
        contactsView.setAdapter(adapter);

        backup_button=(Button)findViewById(R.id.backup_button);
        delete_button=(Button)findViewById(R.id.delete_button);
        restore_button=(Button)findViewById(R.id.restore_button);
        backup_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);
        restore_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backup_button:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
                }else{
                    readContacts();
                }
                break;
            case R.id.delete_button:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
                }else{
                    deleteContacts();
                }
                break;
            case R.id.restore_button:
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
                }else{
                    restoreContacts();
                }
                break;
            default:
        }
    }

    private void readContacts(){
        Cursor cursor=null;
        numOfRecord=0;
        while(!contactsList.isEmpty()){
            contactsList.remove(0);
        }
        try{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    String displayName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName+"\n"+number);
                    Log.d("lyc_backup",displayName+"  "+number);
                    numOfRecord++;
                }
                Toast.makeText(this,"Backup "+numOfRecord+" records in total",Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    private void deleteContacts(){
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor=null;
        numOfRecord=0;
        try{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    String displayName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d("lyc_delete",displayName+"  "+number);
                    getContentResolver().delete(uri,"display_name=?",new String[]{displayName});
                    numOfRecord++;
                }
                Toast.makeText(this,"Success to delete "+numOfRecord+" records!",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Fail to delete.",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    private void restoreContacts(){
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        int len=contactsList.size();
        ContentValues values=new ContentValues();
        ContentResolver resolver=getContentResolver();
        String peopleInfo,name,number;
        for(int i=0;i<len;i++){
            peopleInfo=contactsList.get(i);
            int j;
            for(j=0;j<peopleInfo.length();j++){
                if(peopleInfo.charAt(j)=='\n'){
                    break;
                }
            }
            name=peopleInfo.substring(0,j);
            number=peopleInfo.substring(j+1,peopleInfo.length());
            Log.d("lyc_restore",name+"  "+number);

            uri = Uri.parse("content://com.android.contacts/raw_contacts");
            long contact_id = ContentUris.parseId(resolver.insert(uri, values));
            //插入data表
            uri = Uri.parse("content://com.android.contacts/data");
            //add Name
            values.put("raw_contact_id", contact_id);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
            values.put("data1", name);
            resolver.insert(uri, values);
            values.clear();

            //add Phone
            values.put("raw_contact_id", contact_id);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
            values.put("data1", number);
            resolver.insert(uri, values);
            values.clear();

        }
        Toast.makeText(this,"Restore "+contactsList.size()+" records in total",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode){
                case 1:
                    if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        readContacts();
                    }else{
                        Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }
    }
}
