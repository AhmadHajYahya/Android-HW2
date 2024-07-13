package com.ahmad.ahmadhajyahya_hw2.Score;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ahmad.ahmadhajyahya_hw2.R;

import java.util.List;

public class HighScoreAdapter extends ArrayAdapter<HighScore> {

    public HighScoreAdapter(Context context, List<HighScore> highScores) {
        super(context, 0, highScores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HighScore highScore = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_high_score, parent, false);
        }

        TextView score = convertView.findViewById(R.id.text_view_high_score);
        score.setText(""+highScore.getScore());

        return convertView;
    }
}
