package com.arunabhdas.mobile.broccoli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arunabhdas.mobile.broccoli.R;

/**
 * Created by das on 27/01/17.
 */

public class PostmanActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postman);

        Spinner spinner = (Spinner) findViewById(R.id.accounts_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        // Specify the layout to use when the list of choices appears
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.accounts_array, R.layout.simple_spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_postman) {
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
