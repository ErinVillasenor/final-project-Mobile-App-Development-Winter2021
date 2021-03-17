package com.example.steamapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.steamapp.data.FriendsList;
import com.example.steamapp.data.LoadingStatus;
import com.example.steamapp.data.PlayerSummary;

public class FriendsActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYER_DATA = "FriendsActivity.PlayerSummary";

    private static final String STEAM_API_KEY = BuildConfig.STEAM_API_KEY;
    private static final String TAG = FriendsActivity.class.getSimpleName();

    private RecyclerView searchResultsRV;

    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;

    private SteamFriendAdapter steamFriendAdapter;

    private PlayerSummary playerSummary = null;

    // lifecycle stuff
    private FriendSearchViewModel friendSearchViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.searchResultsRV = findViewById(R.id.rv_search_results);

        this.searchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRV.setHasFixedSize(true);

        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);

        this.steamFriendAdapter = new SteamFriendAdapter();
        this.searchResultsRV.setAdapter(steamFriendAdapter);

        this.friendSearchViewModel = new ViewModelProvider(this)
                .get(FriendSearchViewModel.class);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_PLAYER_DATA)) {
            this.playerSummary = (PlayerSummary)intent.getSerializableExtra(EXTRA_PLAYER_DATA);

            // DON'T REALLY NEED THIS STUFF, WE ONLY NEED THE STEAM ID, WHICH
            // WE CAN ACCESS FROM this.playerSummary
            // So, get rid of these two lines when you've seen the new activity opening
             TextView playerSummaryTV = findViewById(R.id.tv_friends);
             playerSummaryTV.setText(this.playerSummary.getSteamid());
        }

        this.friendSearchViewModel.getFriendSearchResults().observe(
                this,
                new Observer<FriendsList>() {
                    @Override
                    public void onChanged(FriendsList friendsList) {
                        steamFriendAdapter.updateSearchResults(friendsList);
                    }
                }
        );

        this.friendSearchViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        if (loadingStatus == LoadingStatus.LOADING) {
                            loadingIndicatorPB.setVisibility(View.VISIBLE);
                        } else if (loadingStatus == LoadingStatus.SUCCESS) {

                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.INVISIBLE);

                        } else {

                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_games:
                Intent intent = new Intent(this, GamesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
