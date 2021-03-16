package com.example.steamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.steamapp.data.LoadingStatus;
import com.example.steamapp.data.PlayerData;

public class MainActivity extends AppCompatActivity {

    private static final String STEAM_API_KEY = BuildConfig.STEAM_API_KEY;
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView searchResultsRV;
    private EditText searchBoxET;

    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;

    private SteamPlayerAdapter steamPlayerAdapter;

    // lifecycle stuff
    private SteamSearchViewModel steamSearchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.searchBoxET = findViewById(R.id.et_search_box);
        this.searchResultsRV = findViewById(R.id.rv_search_results);

        this.searchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRV.setHasFixedSize(true);

        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);

        this.steamPlayerAdapter = new SteamPlayerAdapter();
        this.searchResultsRV.setAdapter(steamPlayerAdapter);

        this.steamSearchViewModel = new ViewModelProvider(this)
                .get(SteamSearchViewModel.class);


        Button searchButton = (Button)findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchSteamID = searchBoxET.getText().toString();
                if (!TextUtils.isEmpty(searchSteamID)) {
                    performSteamIDSearch(STEAM_API_KEY, searchSteamID);
                }
            }
        });

        this.steamSearchViewModel.getPlayerSearchResults().observe(
                this,
                new Observer<PlayerData>() {
                    @Override
                    public void onChanged(PlayerData playerData) {
                        steamPlayerAdapter.updateSearchResults(playerData);
                    }
                }
        );

        this.steamSearchViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        if (loadingStatus == LoadingStatus.LOADING) {
                            loadingIndicatorPB.setVisibility(View.VISIBLE);
                        } else if (loadingStatus == LoadingStatus.SUCCESS) {

                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.INVISIBLE);

                            // use this for RecyclerView
                            // forecastListRV.setVisibility(View.VISIBLE);
                        } else {
                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.VISIBLE);

                            // use this for RecyclerView
                            // forecastListRV.setVisibility(View.INVISIBLE);
                        }
                    }
                }
        );
    }

    private void performSteamIDSearch(String API_KEY, String playerID){
        Log.d(TAG, "== user entered this steamID: " + playerID);
        steamSearchViewModel.loadPlayerSearchResults(API_KEY, playerID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}