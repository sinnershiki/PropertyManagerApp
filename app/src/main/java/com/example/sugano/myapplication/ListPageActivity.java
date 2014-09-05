package com.example.sugano.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ListPageActivity extends Activity implements OnClickListener, OnItemClickListener {
    int deleteId;
    CustomData item;
    TextView tv;
    static SQLiteDatabase mydb;
    String table_name = "property_list_table";
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        tv = (TextView)findViewById(R.id.listtextview);
        lv = (ListView)findViewById(R.id.listview1);
        drawList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void drawList(){
        mydb = new DBAdapter(getApplicationContext()).open();
        Cursor cursor = mydb.rawQuery("select * from " + table_name + ";",null);
        List<CustomData> objects = new ArrayList<CustomData>();
        while (cursor.moveToNext()) {
            item = new CustomData();
            item.setId(cursor.getInt(0));
            item.setPerson(cursor.getString(1));
            item.setProperty(cursor.getString(2));
            item.setPeriodDate(cursor.getString(3));
            item.setFromDate(cursor.getString(4));
            item.setIsLending(cursor.getInt(5));
            objects.add(item);
        }
        CustomAdapter customAdapter = new CustomAdapter(this, 0, objects);
        lv.setAdapter(customAdapter);
        //lv.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        mydb.close();
    }

    public void onClick(View v) {

    }
    public void onItemClick(AdapterView adapter, View view, int position, long id) {
        CustomData item1 = (CustomData)lv.getItemAtPosition(position);
        deleteId = item1.getId();
        if(position==1) {
           // Toast.makeText(this, ""+item1.getId(), Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(this)
                    .setTitle("削除しますか？")
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mydb = new DBAdapter(getApplicationContext()).open();
                                    mydb.delete(table_name, "id ="+ deleteId, null);
                                    mydb.close();
                                    drawList();
                                }
                            })
                    .setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .show();
        }
    }
}
