package com.example.sugano.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import java.util.Calendar;


public class MyActivity extends Activity implements OnClickListener{
    Button btn1;
    Button btn2;
    Button leftBtn;
    Button rightBtn;
    TextView tvCal;
    TextView td;
    final static int dbVer = 1;
    final static String dbName = "property_manager.db";
    String table_name = "property_list_table";
    static SQLiteDatabase mydb;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        leftBtn= (Button)findViewById(R.id.left);
        rightBtn= (Button)findViewById(R.id.right);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        td = (TextView)findViewById(R.id.date);
        cal = Calendar.getInstance();
        td.setText(cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月");
        Resources res = getResources();
        for(int i=0;i<=41;i++){
            String resViewName = "tv" + i;
            int viewId = res.getIdentifier(resViewName, "id", getPackageName());
            tvCal = (TextView)findViewById(viewId);
            tvCal.setOnClickListener(this);
        }
        writeCalendarDate(cal);
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

    public void clearCalendarDate(){
        Resources res = getResources();
        for(int i=0;i<42;i++){
            String resViewName = "tv" + i;
            int viewId = res.getIdentifier(resViewName, "id", getPackageName());
            tvCal = (TextView)findViewById(viewId);
            tvCal.setText("");
        }
    }

    public void writeCalendarDate(Calendar cal){
        clearCalendarDate();
        String text = "";
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1);
        StringBuffer dow = new StringBuffer();

        int i = 0;
        i = cal.get(Calendar.DAY_OF_WEEK);
        Resources res = getResources();
        for(int j=1;j<=cal.getActualMaximum(Calendar.DATE);i++,j++){
            int num = i - 1;
            String resViewName = "tv" + num;
            int viewId = res.getIdentifier(resViewName, "id", getPackageName());
            tvCal = (TextView)findViewById(viewId);
            tvCal.setText(""+j);
        }
    }

    public void onClick(View v) {
        if(v==leftBtn){
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, cal.get(Calendar.DAY_OF_MONTH));
            td.setText(cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月");
            Resources res = getResources();
            for(int i=0;i<=41;i++){
                String resViewName = "tv" + i;
                int viewId = res.getIdentifier(resViewName, "id", getPackageName());
                tvCal = (TextView)findViewById(viewId);
                tvCal.setOnClickListener(this);
            }
            writeCalendarDate(cal);
        }else if(v == rightBtn){
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            td.setText(cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月");
            Resources res = getResources();
            for(int i=0;i<=41;i++){
                String resViewName = "tv" + i;
                int viewId = res.getIdentifier(resViewName, "id", getPackageName());
                tvCal = (TextView)findViewById(viewId);
                tvCal.setOnClickListener(this);
            }
            writeCalendarDate(cal);
        }
        else if (v==btn1){
            Intent intent = new Intent(this, EnterDataActivity.class);
            startActivityForResult(intent, 0);
        }
        else if (v==btn2) {
            Intent intent = new Intent(this, ListPageActivity.class);
            startActivityForResult(intent, 0);
        }else{
            String tmp="";
            Resources res = getResources();
            for(int i=0;i<=41;i++){
                String resViewName = "tv" + i;
                int viewId = res.getIdentifier(resViewName, "id", getPackageName());
                tvCal = (TextView)findViewById(viewId);
                if(v==tvCal){
                  /*  String date = tvCal.getText().toString();
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
                    break;*/
                    Intent intent = new Intent(this, EnterDataActivity.class);
                    intent.putExtra("date",tvCal.getText().toString());
                    startActivityForResult(intent, 0);
                }
            }
        }
    }
}
