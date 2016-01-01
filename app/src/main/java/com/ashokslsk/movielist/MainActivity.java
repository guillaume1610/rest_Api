package com.ashokslsk.movielist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ashokslsk.movielist.controller.api.RTMovie;
import com.ashokslsk.movielist.controller.api.RTMovieFactory;
import com.ashokslsk.movielist.model.beans.MoviesResult;
import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    private RTMovie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(getApplicationContext());
        RTMovieFactory controllerFactory = new RTMovieFactory(magnetClient);
        try {
            movie = controllerFactory.obtainInstance();
        } catch (SchemaException e) {
            e.printStackTrace();
        }

        Call<List<MoviesResult>> callObject = movie.getMovies(null);

        try {
            List<MoviesResult> result = (List<MoviesResult>) callObject.get();
            final ListView listview = (ListView) findViewById(R.id.list);

            CustomListAdapter earthAdapter = new CustomListAdapter(MainActivity.this,result);

            listview.setAdapter(earthAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
}
