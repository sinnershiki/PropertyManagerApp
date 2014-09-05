package com.example.sugano.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Sugano on 2014/09/04.
 */
public class DBAdapter {
    final static int dbVer = 2;
    final static String dbName = "property_manager.db";
    String table_name = "property_list_table";
    protected final Context context;
    protected DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public DBAdapter(Context context){
        this.context = context;
        dbHelper = new DatabaseHelper(this.context);
    }

    public SQLiteDatabase open() {
        db = dbHelper.getWritableDatabase();
        return db;
    }


    public void close(){
        dbHelper.close();
    }

    class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context con){
            super(con, dbName, null, dbVer);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // テーブル作成のクエリを発行
            db.execSQL(
                    //_id, person, property, period_date, from_date is_lending
                    "create table "+table_name+" ( " +
                            "id integer primary key autoincrement, " +
                            "person text not null," +
                            "property text not null," +
                            "period_date text not null," +
                            "from_date text not null," +
                            "is_lending int not null"+
                            ");");
        }

        /**
         * データベースのバージョンアップ時に実行される処理
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // テーブルの破棄と再作成
            db.execSQL("drop table "+table_name+";");
            onCreate(db);
        }
    }
}
