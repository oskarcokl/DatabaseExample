package si.uni_lj.fri.lrss.databaseexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "contacts_db";
    final static Integer DATABASE_VERSION = 1;
    final static String TABLE_NAME = "contacts";
    final static String USER_NAME = "name";
    final static String USER_EMAIL = "email";
    final static String _ID = "_id";
    final static String[] columns = { _ID, USER_NAME, USER_EMAIL };

    final private static String CREATE_CMD =

            "CREATE TABLE "+TABLE_NAME+" (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_NAME + " TEXT NOT NULL,"
                    + USER_EMAIL + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
