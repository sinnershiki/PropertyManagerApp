package com.example.sugano.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ListPageActivity extends Activity implements OnClickListener, OnItemClickListener, RadioGroup.OnCheckedChangeListener {
    int deleteId;
    CustomData item;
    //TextView tv;
    static SQLiteDatabase mydb;
    String table_name = "property_list_table";
    ListView lv;
    RadioGroup rg;
    RadioButton rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        //tv = (TextView)findViewById(R.id.listtextview);
        lv = (ListView)findViewById(R.id.listview1);
        rg = (RadioGroup) findViewById(R.id.lendOrBorrow);
        rb =(RadioButton) findViewById(rg.getCheckedRadioButtonId());
        rg.setOnCheckedChangeListener(this);
        drawList(0);
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

    public void drawList(int isLending){
        mydb = new DBAdapter(getApplicationContext()).open();
        Cursor cursor;
        if (isLending == 1)
            cursor = mydb.rawQuery("select * from " + table_name + " where is_lending = 1;",null);
        else if (isLending == -1)
            cursor = mydb.rawQuery("select * from " + table_name + " where is_lending = -1;",null);
        else
            cursor = mydb.rawQuery("select * from " + table_name + " ;",null);
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

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        Toast.makeText(this, this.getResources().getResourceEntryName(rb.getId()), Toast.LENGTH_SHORT).show();
        if (this.getResources().getResourceEntryName(rb.getId()).toString().equals("lend")) {
            drawList(1);
        }else if (this.getResources().getResourceEntryName(rb.getId()).toString().equals("borrow")) {
            drawList(-1);
        }else {
            drawList(0);
        }
        Log.d("ListPageActivity", "test");
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
                                    drawList(0);
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
