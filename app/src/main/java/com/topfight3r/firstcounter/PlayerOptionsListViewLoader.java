package com.topfight3r.firstcounter;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.media.MediaRouter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;

/**
 * Created by petro_000 on 2017-05-26.
 */

public class PlayerOptionsListViewLoader extends ListActivity{
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_options_list);
        listView = (ListView) findViewById(R.id.player_options);
        String[] values = {"hello", "world", "my", "name"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.player_options_list, R.id.player_options_text_view, values);
        listView.setAdapter(adapter);
        listView.setOnClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                // ListView Clicked item index
                int itemPosition= position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

            }
        });

    }

}
