package com.example.sugano.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class MyActivity extends Activity implements OnClickListener{
    Button btn1;
    TextView tv;
    TextView tvCal;
    String textDialog;
    final static int dbVer = 1;
    final static String dbName = "property_manager.db";
    String table_name = "property_list_table";
    static SQLiteDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mydb = new DBAdapter(getApplicationContext()).open().db;

        btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        tv = (TextView)findViewById(R.id.tvTest);
        Resources res = getResources();
        for(int i=0;i<=41;i++){
            String resViewName = "tv" + i;
            int viewId = res.getIdentifier(resViewName, "id", getPackageName());
            tvCal = (TextView)findViewById(viewId);
            tvCal.setOnClickListener(this);
        }
        writeCalendarDate();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    public void writeCalendarDate(){
        String text = "";
        Calendar cal = Calendar.getInstance();
        int yr =  cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        cal.set(yr,month,1);
        StringBuffer dow = new StringBuffer();

        int i = 0;
        switch (cal.get(Calendar.DAY_OF_WEEK)) {  //(8)現在の曜日を取得
            case Calendar.SUNDAY: dow.append("日曜日"); break;
            case Calendar.MONDAY: dow.append("月曜日"); break;
            case Calendar.TUESDAY: dow.append("火曜日"); break;
            case Calendar.WEDNESDAY: dow.append("水曜日"); break;
            case Calendar.THURSDAY: dow.append("木曜日"); break;
            case Calendar.FRIDAY: dow.append("金曜日"); break;
            case Calendar.SATURDAY: dow.append("土曜日"); break;
        }
        i = cal.get(Calendar.DAY_OF_WEEK);
        Resources res = getResources();
        for(int j=1;j<=cal.getActualMaximum(Calendar.DATE);i++,j++){
            int num = i - 1;
            String resViewName = "tv" + num;
            int viewId = res.getIdentifier(resViewName, "id", getPackageName());
            tvCal = (TextView)findViewById(viewId);
            tvCal.setText(""+j);
        }
        text = dow.toString();
        tv.setText(text);
    }

    public void onClick(View v) {
        if (v==btn1) {
            //Intent intent = new Intent(this, ListPageActivity.class);
            //startActivityForResult(intent, 0);
            /*
            ContentValues values = new ContentValues();
            //_id, person, property, period_date, from is_lending
            values.put("person", "user");
            values.put("property", "game");
            values.put("period_date", "2014-10-10");
            values.put("from_date", "2014-9-4");
            values.put("is_lending", "1");
            //Insert発行
            mydb.insert(table_name, null, values);
             */


            Cursor cursor = mydb.rawQuery("select * from " + table_name + ";",null);
            //TextViewに表示
            StringBuilder str = new StringBuilder();
            while (cursor.moveToNext()) {
                str.append(cursor.getInt(0));
                str.append("," + cursor.getString(1));
                str.append("," + cursor.getString(2));
                str.append("," + cursor.getString(3));
                str.append("," + cursor.getString(4));
                str.append("," + cursor.getInt(5));
                str.append("\n");
            }
            tv.setText(str);
        }else{
            String tmp="";
            Resources res = getResources();
            for(int i=0;i<=41;i++){
                String resViewName = "tv" + i;
                int viewId = res.getIdentifier(resViewName, "id", getPackageName());
                tvCal = (TextView)findViewById(viewId);
                if(v==tvCal){
                    String date = tvCal.getText().toString();
                    textDialog = "";
                    textDialog += date;
                    tvCal.setText(textDialog);
                    LayoutInflater inflater
                            = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.dialog, null);
                    final EditText editText
                            = (EditText)view.findViewById(R.id.editText1);
                    new AlertDialog.Builder(this)
                            .setTitle(textDialog+"日返却予定の物")
                            .setView(view)
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            textDialog += "\n\n";
                                            textDialog += editText.getText().toString();
                                            tvCal.setText(textDialog);
                                        }
                                    })
                            .show();
                    break;
                }
            }
        }
    }
}
