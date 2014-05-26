package cn.lijie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper{

  public DBhelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt){
    super(paramContext, paramString, paramCursorFactory, paramInt);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase){
    paramSQLiteDatabase.execSQL("create table if not exists city (_id integer primary key autoincrement,cityName text)");
    paramSQLiteDatabase.execSQL("create table if not exists storage (_id integer primary key autoincrement,cityID integer,storageName text,storageLoc text)");
    paramSQLiteDatabase.execSQL("create table if not exists branchStorage (_id integer primary key autoincrement,staorageID integer,branchStorageName text,branchStorageType integer)");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2){
  }
}