package com.rob;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rob.db.AdminSQLiteOpenHelper;


public class MainActivity extends Activity {

    public EditText dni;
    public EditText name;
    public EditText university;
    public EditText subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dni        = (EditText)findViewById(R.id.inputDNI);
        name       = (EditText)findViewById(R.id.inputName);
        university = (EditText)findViewById(R.id.inputUniversity);
        subject    = (EditText)findViewById(R.id.inputClass);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addAction(View view){
        boolean validFields     = true;
        String dniString        = dni.getText().toString();
        String nameString       = name.getText().toString();
        String universityString = university.getText().toString();
        String subjectString    = subject.getText().toString();
        Resources resource      = getResources();

        if(dniString.equals("")){
            validFields = false;
            dni.setError(resource.getString(R.string.dni_required));
        }

        if(nameString.equals("")){
            validFields = false;
            name.setError(resource.getString(R.string.name_required));
        }

        if(universityString.equals("")){
            validFields = false;
            university.setError(resource.getString(R.string.university_required));
        }

        if(subjectString.equals("")){
            validFields = false;
            subject.setError(resource.getString(R.string.subject_required));
        }

        if(validFields == true){
            AdminSQLiteOpenHelper sqlite = new AdminSQLiteOpenHelper(this, "management", null, 1);
            SQLiteDatabase db = sqlite.getWritableDatabase();

            ContentValues registry = new ContentValues();
            registry.put("dni", dniString);
            registry.put("name", nameString);
            registry.put("university", universityString);
            registry.put("subject", subjectString);

            db.insert("people", null, registry);
            db.close();

            Toast.makeText(this, resource.getString(R.string.data_created), Toast.LENGTH_SHORT).show();
        }

    }


    public void deleteAction(View view){
        String dniString = dni.getText().toString();
        Resources resource = getResources();

        AdminSQLiteOpenHelper sqlite = new AdminSQLiteOpenHelper(this, "management", null, 1);
        SQLiteDatabase db = sqlite.getWritableDatabase();

        int quantity = db.delete("people", "dni=" + dniString, null);
        db.close();

        dni.setText("");
        name.setText("");
        university.setText("");
        subject.setText("");

        if(quantity == 1){
            Toast.makeText(this, resource.getString(R.string.data_deleted), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resource.getString(R.string.data_not_deleted), Toast.LENGTH_SHORT).show();
        }
    }


    public void updateAction(View view){
        String dniString        = dni.getText().toString();
        String nameString       = name.getText().toString();
        String universityString = university.getText().toString();
        String subjectString    = subject.getText().toString();
        Resources resource      = getResources();

        AdminSQLiteOpenHelper sqlite = new AdminSQLiteOpenHelper(this, "management", null, 1);
        SQLiteDatabase db = sqlite.getWritableDatabase();

        ContentValues registry = new ContentValues();
        registry.put("dni", dniString);
        registry.put("name", nameString);
        registry.put("university", universityString);
        registry.put("subject", subjectString);

        int quantity = db.update("people", registry, "dni=" + dniString, null);
        db.close();

        if(quantity == 1){
            Toast.makeText(this, resource.getString(R.string.data_updated), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resource.getString(R.string.data_not_updated), Toast.LENGTH_SHORT).show();
        }
    }


    public void showAction(View view){
        String dniString = dni.getText().toString();
        Resources resource = getResources();

        AdminSQLiteOpenHelper sqlite = new AdminSQLiteOpenHelper(this, "management", null, 1);
        SQLiteDatabase db = sqlite.getWritableDatabase();

        Cursor row = db.rawQuery("SELECT name, university, subject FROM people WHERE dni=" + dniString, null);

        if(row.moveToFirst()){
            name.setText(row.getString(0));
            university.setText(row.getString(1));
            subject.setText(row.getString(2));
        } else {
            Toast.makeText(this, resource.getString(R.string.data_not_exists), Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}


















