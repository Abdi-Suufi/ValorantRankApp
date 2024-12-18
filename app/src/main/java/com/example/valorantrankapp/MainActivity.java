package com.example.valorantrankapp;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private TextView titleText;
    private EditText usernameTagEditText;
    private Button fetchRankButton;
    private TextView currentRankTextView;  // TextView for current rank
    private TextView peakRankTextView;     // TextView for peak rank
    private ImageView currentRankImageView; // ImageView for current rank
    private ImageView peakRankImageView;    // ImageView for peak rank

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.titleText);
        usernameTagEditText = findViewById(R.id.usernameTagEditText);
        fetchRankButton = findViewById(R.id.fetchRankButton);
        currentRankTextView = findViewById(R.id.currentRankTextView);
        peakRankTextView = findViewById(R.id.peakRankTextView);
        currentRankImageView = findViewById(R.id.currentRankImageView);
        peakRankImageView = findViewById(R.id.peakRankImageView);

        fetchRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameTag = usernameTagEditText.getText().toString().trim();

                if (usernameTag.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a username#tag", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Animate the transition of the elements upward
                animateElementsUp();

                // Validate the format of the username#tag input
                if (!usernameTag.contains("#")) {
                    Toast.makeText(MainActivity.this, "Invalid format. Please use username#tag", Toast.LENGTH_SHORT).show();
                    return;
                }

                fetchRank(usernameTag);
            }
        });
    }

    private void animateElementsUp() {
        ObjectAnimator.ofFloat(titleText, "translationY", 0f, -400f).setDuration(500).start();
        ObjectAnimator.ofFloat(usernameTagEditText, "translationY", 0f, -400f).setDuration(500).start();
        ObjectAnimator.ofFloat(fetchRankButton, "translationY", 0f, -400f).setDuration(500).start();
        ObjectAnimator.ofFloat(currentRankTextView, "translationY", 0f, -400f).setDuration(500).start();
        ObjectAnimator.ofFloat(peakRankTextView, "translationY", 0f, -400f).setDuration(500).start();
        ObjectAnimator.ofFloat(currentRankImageView, "translationY", 0f, -400f).setDuration(500).start();
        ObjectAnimator.ofFloat(peakRankImageView, "translationY", 0f, -400f).setDuration(500).start();
    }

    private void fetchRank(String usernameTag) {
        Retrofit retrofit = ApiClient.getClient();
        ValorantApi api = retrofit.create(ValorantApi.class);

        Call<RankData> call = api.getRank(new UsernameTag(usernameTag));
        call.enqueue(new Callback<RankData>() {
            @Override
            public void onResponse(Call<RankData> call, Response<RankData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RankData rankData = response.body();

                    // Set current rank info
                    currentRankTextView.setText("Current: " + rankData.getCurrent_rank());
                    // Set peak rank info
                    peakRankTextView.setText("Peak: " + rankData.getHighest_rank());

                    // Load current rank image using Glide
                    Glide.with(MainActivity.this)
                            .load(rankData.getCurrent_rank_image())  // Ensure this method returns the correct current rank image URL
                            .into(currentRankImageView);

                    // Load peak rank image using Glide
                    Glide.with(MainActivity.this)
                            .load(rankData.getHighest_rank_image())  // Ensure this method returns the correct peak rank image URL
                            .into(peakRankImageView);
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching rank data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RankData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch rank data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}