package si.uni_lj.fri.lrss.databaseexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDbHelper;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DatabaseHelper(this);

        // Empty database at the start
        clearAll();

        // Insert records
        insertContacts();

        // Create a cursor (iterator)
        Cursor c = readContacts();
        mAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, c,
                DatabaseHelper.columns, new int[] { R.id._id, R.id.name, R.id.email}, 0);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);

        Button fixButton = (Button) findViewById(R.id.fix_button);
        fixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // execute database operations
                fix();

                // Redisplay data
                /*
                 Actually, you should refresh your cursor by running the code again to get
                 the Cursor, and do so on a background thread. You can then refresh your
                 ListView by calling changeCursor() or swapCursor() on the CursorAdapter.
                 We use the deprecated requery() method here for simplicity, but on a
                 large DB this could block the UI.
                 */
                mAdapter.getCursor().requery();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    // Insert several artist records
    private void insertContacts() {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_NAME, "Veljko Pejovic");
        values.put(DatabaseHelper.USER_EMAIL, "Veljko.Pejovic@fri.uni-lj.si");
        mDbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_NAME, null, values);
        values.clear();

        values.put(DatabaseHelper.USER_NAME, "Martin Gjoreski");
        values.put(DatabaseHelper.USER_EMAIL, "Martin@fri.uni-lj.si");
        mDbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_NAME, null, values);
        values.clear();

        values.put(DatabaseHelper.USER_NAME, "Jure Lokovsek");
        values.put(DatabaseHelper.USER_EMAIL, "Jure.Lokovsek@fri.uni-lj.si");
        mDbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_NAME, null, values);
        values.clear();

        values.put(DatabaseHelper.USER_NAME, "Daniel Pellarini");
        values.put(DatabaseHelper.USER_EMAIL, "Daniel.Pellarini@fri.uni-lj.si");
        mDbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_NAME, null, values);

        values.put(DatabaseHelper.USER_NAME, "Xyz");
        values.put(DatabaseHelper.USER_EMAIL, "xyz@fri.uni-lj.si");
        mDbHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    // Returns all artist records in the database
    private Cursor readContacts() {
        return mDbHelper.getWritableDatabase().query(DatabaseHelper.TABLE_NAME,
                DatabaseHelper.columns, null, new String[] {}, null, null,
                null);
    }

    // Modify the contents of the database
    private void fix() {

        mDbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE_NAME,
                DatabaseHelper.USER_NAME + "=?",
                new String[] { "Xyz" });

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_NAME, "Martin Gjoreski");
        values.put(DatabaseHelper.USER_EMAIL, "Martin.Gjoreski@fri.uni-lj.si");

        mDbHelper.getWritableDatabase().update(DatabaseHelper.TABLE_NAME, values,
                DatabaseHelper.USER_NAME + "=?",
                new String[] { "Martin Gjoreski" });
    }

    // Delete all records
    private void clearAll() {
        mDbHelper.getWritableDatabase().delete(DatabaseHelper.TABLE_NAME, null, null);
    }

    // Close database
    @Override
    protected void onDestroy() {
        mDbHelper.getWritableDatabase().close();
        mDbHelper.deleteDatabase(this);

        super.onDestroy();
    }

}
