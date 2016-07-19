package com.example.raymour.joinslabfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raymour on 7/18/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String COL_SSN_EMP = "ssn_EMP";
    public static final String COL_ID = "_id";
    public static final String COL_FIRST_NAME = "firstName";
    public static final String COL_LAST_NAME = "lastName";
    public static final String COL_YEAR_BIRTH = "year of birth";
    public static final String COL_CITY = "home city";

    public static final String JOB_TABLE_NAME = "job";
    public static final String COL_SSN_JOB = "ssn_JOB";
    public static final String COL_COMPANY = "company";
    public static final String COL_SALARY = "salary";
    public static final String COL_EXPERIENCE = "experience";

    private DatabaseHelper(Context context){ super(context, "db", null, 1);}

    private static DatabaseHelper INTSTANCE;

    public static synchronized DatabaseHelper getINTSTANCE (Context context) {
        if(INTSTANCE == null)
            INTSTANCE = new DatabaseHelper(context.getApplicationContext());
        return INTSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_EMPLOYEE);
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_JOBS);
        onCreate(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_EMPLOYEE);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_JOBS);
        sqLiteDatabase.close();
    }

    public void emptyTable () {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + EMPLOYEE_TABLE_NAME);
        sqLiteDatabase.execSQL("DELETE FROM " + JOB_TABLE_NAME);
        sqLiteDatabase.close();
    }

    private static final String SQL_CREATE_ENTRIES_EMPLOYEE = "CREATE TABLE" +
            EMPLOYEE_TABLE_NAME + " (" +
            COL_ID + "TEXT PRIMARY KEY," +
            COL_SSN_EMP + " TEXT," +
            COL_FIRST_NAME + "TEXT," +
            COL_LAST_NAME + "TEXT," +
            COL_YEAR_BIRTH + "TEXT," +
            COL_CITY + "TEXT" + ")";

    private static final String SQL_CREATE_ENTRIES_JOBS = "CREATE TABLE" +
            JOB_TABLE_NAME + " (" +
            COL_ID + "TEXT PRIMARY KEY," +
            COL_SSN_JOB + " TEXT," +
            COL_COMPANY + "TEXT," +
            COL_SALARY + "TEXT," +
            COL_EXPERIENCE + "TEXT," + ")";

    private static final String SQL_DELETE_ENTRIES_EMPLOYEE = "DROP TABLE IF EXISTS" + EMPLOYEE_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_JOBS = "DROP TABLE IF EXISTS" + JOB_TABLE_NAME;

    public void insertRowEmployee (Employee employee) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SSN_EMP, employee.getSsn());
        values.put(COL_FIRST_NAME, employee.getFirstName());
        values.put(COL_LAST_NAME, employee.getLastName());
        values.put(COL_YEAR_BIRTH, employee.getYearOfBirth());
        values.put(COL_CITY, employee.getHomeCity());
        sqLiteDatabase.insertOrThrow(EMPLOYEE_TABLE_NAME, null, values);
    }

    public void insertRowJobs(Job job) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SSN_JOB, job.getSsn());
        values.put(COL_COMPANY, job.getCompany());
        values.put(COL_SALARY, job.getSalary());
        values.put(COL_EXPERIENCE, job.getExperience());
        sqLiteDatabase.insertOrThrow(JOB_TABLE_NAME, null, values);

    }

    public Cursor getSameCompany() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT" + COL_ID + "," + COL_FIRST_NAME + ", " + COL_LAST_NAME + "FROM " + JOB_TABLE_NAME+ " JOIN " + EMPLOYEE_TABLE_NAME + " ON " + EMPLOYEE_TABLE_NAME + "." + COL_SSN_EMP + " = " + JOB_TABLE_NAME + "." + COL_SSN_JOB + " WHERE " + COL_COMPANY + " LIKE 'Macy%'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor companyBoston () {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT " + COL_ID + ", " + COL_COMPANY + " FROM" + EMPLOYEE_TABLE_NAME + "." + COL_SSN_EMP + " = " + JOB_TABLE_NAME + "." + COL_SSN_JOB + " WHERE " + COL_CITY + " 'Boston'";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor higestSalary () {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(JOB_TABLE_NAME, new String[]{COL_COMPANY}, null, null, null, null, COL_SALARY + " DESC");
        return cursor;

    }


}

