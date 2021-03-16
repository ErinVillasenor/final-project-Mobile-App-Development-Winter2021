package com.example.steamapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.steamapp.data.FriendsList;
import com.example.steamapp.data.LoadingStatus;

public class FriendsActivity extends AppCompatActivity {
    private static final String STEAM_API_KEY = BuildConfig.STEAM_API_KEY;
    private static final String TAG = FriendsActivity.class.getSimpleName();

    private RecyclerView searchResultsRV;

    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;

    private SteamFriendAdapter steamFriendAdapter;

    // lifecycle stuff
    private FriendSearchViewModel friendSearchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_activity);

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
}
