package com.example.steamapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.steamapp.data.PlayerData;
import com.example.steamapp.data.PlayerSummary;

public class SteamPlayerAdapter extends RecyclerView.Adapter<SteamPlayerAdapter.SearchResultViewHolder>{

    private PlayerData playerData;
    private static final String TAG = SteamPlayerAdapter.class.getSimpleName();

    public void updateSearchResults(PlayerData searchResultsList) {
        this.playerData = searchResultsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.playerData == null || this.playerData.getPlayerSummary() == null){
            return 0;
        } else {
            return 1; // since we always have just one, we need to talk about this though
            // since we're using a recycler view here, we need to figure out how to have the
            // header of the recycler view be/show the main player's info, and then below
            // a list of their friends. This is something similar to assignment 4 extra credit
            // maybe it's not that complicated but we can ask Rob how to do that???
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.bind(this.playerData.getPlayerSummary());
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView searchResultTV;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            this.searchResultTV = itemView.findViewById(R.id.tv_search_result);
        }

        void bind(PlayerSummary searchResult) {
            Log.d(TAG, "data from bind fx: " + searchResult.getPersonaname());
            // display only the name for now
            this.searchResultTV.setText(searchResult.getPersonaname());
        }
    }
}
