package com.example.avenflar.wch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.avenflar.wch.RecyclerView.Adapter.ChampionsAdapter;
import com.example.avenflar.wch.RecyclerView.Adapter.GetServices.GetChampionsServices;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    public static final String CHAMPS_UPDATE = "https://raw.githubusercontent.com/ngryman/lol-champions/master/champions.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Champions");

        IntentFilter intentFilter = new IntentFilter(CHAMPS_UPDATE);
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(new ChampionsUpdate(), intentFilter);
        GetChampionsServices.startActionChampions(MainActivity.this);

        rv = (RecyclerView) findViewById(R.id.recycler_view_champs);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ChampionsAdapter(getChampionsFromFile(), Glide.with(this)));

    }

    public class ChampionsUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("tag", "OnReceive");
            ChampionsAdapter adapter = ((ChampionsAdapter) rv.getAdapter());
            adapter.setNewChamps(getChampionsFromFile());

        }
    }

    public JSONArray getChampionsFromFile() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "champs.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer,"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new JSONArray();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Notes :
                Intent intent = new Intent(MainActivity.this,NotesActivity.class);
                startActivity(intent);
                return true;
            case R.id.Quit :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you really want to quit ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Bye bye !",Toast.LENGTH_LONG).show();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Yes stay with us !",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
