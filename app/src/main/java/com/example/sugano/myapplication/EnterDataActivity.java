package com.example.sugano.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;


public class EnterDataActivity extends Activity implements View.OnClickListener{
    Button enterbn;
    EditText et1;
    EditText et2;
    DatePicker dp;
    RadioGroup rg;
    RadioButton rb;
    final static int dbVer = 1;
    final static String dbName = "property_manager.db";
    String table_name = "property_list_table";
    static SQLiteDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        Intent i = getIntent();
        String date = i.getStringExtra("date");
        enterbn = (Button)findViewById(R.id.enterbutton);
        enterbn.setOnClickListener(this);
        et1 = (EditText)findViewById(R.id.propertyEdit);
        et2 = (EditText)findViewById(R.id.personEdit);
        Calendar cal = Calendar.getInstance();
        dp = (DatePicker)findViewById(R.id.datePicker1);
        if (date != null) {
            dp.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), Integer.parseInt(date));
        }
        rg = (RadioGroup) findViewById(R.id.lendOrBorrow);
        rb =(RadioButton) findViewById(rg.getCheckedRadioButtonId());
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // ラジオグループのチェック状態が変更された時に呼び出されます
            // チェック状態が変更されたラジオボタンのIDが渡されます
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enter_data, menu);
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


    public void onClick(View v) {
        if (v==enterbn) {
            mydb = new DBAdapter(getApplicationContext()).open();
            ContentValues values;
            Calendar date = Calendar.getInstance();
           //Toast.makeText(this,dp.getYear()+"-"+(dp.getMonth()+1)+"-"+dp.getDayOfMonth(), Toast.LENGTH_SHORT).show();
           // Toast.makeText(this, date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, this.getResources().getResourceEntryName(rb.getId()), Toast.LENGTH_SHORT).show();
            if (this.getResources().getResourceEntryName(rb.getId()).toString().equals("lend")) {
                //Toast.makeText(this, et1.getText().toString() + "\n" + et2.getText().toString() + "\n" + et3.getText().toString() + "\n" + "1", Toast.LENGTH_SHORT).show();
                values = new ContentValues();
                //_id, person, property, period_date, from is_lending
                values.put("person", et1.getText().toString());
                values.put("property", et2.getText().toString());
                values.put("period_date", dp.getYear()+"-"+(dp.getMonth()+1)+"-"+dp.getDayOfMonth());
                values.put("from_date", date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE));
                values.put("is_lending", "1");
                //Insert発行
                mydb.insert(table_name, null, values);

            }else if (this.getResources().getResourceEntryName(rb.getId()).toString().equals("borrow")) {
                //Toast.makeText(this, et1.getText().toString() + "\n" + et2.getText().toString() + "\n" + et3.getText().toString() + "\n" + "-1", Toast.LENGTH_SHORT).show();
                values = new ContentValues();
                //_id, person, property, period_date, from is_lending
                values.put("person", et1.getText().toString());
                values.put("property", et2.getText().toString());
                values.put("period_date", dp.getYear()+"-"+(dp.getMonth()+1)+"-"+dp.getDayOfMonth());
                values.put("from_date",date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.DATE));
                values.put("is_lending", "-1");
                //Insert発行
                mydb.insert(table_name, null, values);
            }else{}
            mydb.close();
            finish();
        }
    }
}
