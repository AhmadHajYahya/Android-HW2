package com.ahmad.ahmadhajyahya_hw2.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmad.ahmadhajyahya_hw2.R;
import com.ahmad.ahmadhajyahya_hw2.Score.HighScore;
import com.ahmad.ahmadhajyahya_hw2.Score.HighScoreAdapter;
import com.ahmad.ahmadhajyahya_hw2.interfaces.CallBack_List;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Fragment_List extends Fragment {
    private ListView listView;
    private ArrayAdapter<HighScore> adapter;
    private List<HighScore> highScoreList;
    private CallBack_List callBackList;

    public void setCallBackList(CallBack_List callBackList) {
        this.callBackList = callBackList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = view.findViewById(R.id.high_scores_list);
        highScoreList = new ArrayList<>();
        adapter = new HighScoreAdapter(getContext(), highScoreList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            HighScore highScore = highScoreList.get(position);
            if (callBackList != null) {
                callBackList.showLocationInMap(highScore.getLat(), highScore.getLon());
            }
        });

        loadHighScores();
        return view;
    }

    private void loadHighScores() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("HighScores", Context.MODE_PRIVATE);
        String highScores = sharedPreferences.getString("high_scores", "");

        if (!highScores.isEmpty()) {
            String[] scores = highScores.split(";");
            for (String score : scores) {
                String[] parts = score.split(",");
                if (parts.length == 3) {
                    int points = Integer.parseInt(parts[0]);
                    double lat = Double.parseDouble(parts[1]);
                    double lon = Double.parseDouble(parts[2]);
                    highScoreList.add(new HighScore(points, lat, lon));
                }
            }

            // Sort high scores in descending order and keep the top 10
            Collections.sort(highScoreList, Comparator.comparingInt(HighScore::getScore).reversed());
            if (highScoreList.size() > 10) {
                highScoreList = highScoreList.subList(0, 10);
            }

            adapter.notifyDataSetChanged();
        }
    }

}
