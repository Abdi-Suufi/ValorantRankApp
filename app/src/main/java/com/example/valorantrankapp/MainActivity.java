package com.example.valorantrankapp;

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
    private EditText usernameTagEditText;
    private Button fetchRankButton;
    private TextView rankTextView;
    private ImageView rankImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameTagEditText = findViewById(R.id.usernameTagEditText);
        fetchRankButton = findViewById(R.id.fetchRankButton);
        rankTextView = findViewById(R.id.rankTextView);
        rankImageView = findViewById(R.id.rankImageView);

        fetchRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameTag = usernameTagEditText.getText().toString().trim();

                if (usernameTag.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a username#tag", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate the format of the username#tag input
                if (!usernameTag.contains("#")) {
                    Toast.makeText(MainActivity.this, "Invalid format. Please use username#tag", Toast.LENGTH_SHORT).show();
                    return;
                }

                fetchRank(usernameTag);
            }
        });
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

                    // Set rank details on the UI
                    rankTextView.setText("Current Rank: " + rankData.getCurrent_rank() + "\n" +
                            "Current Elo: " + rankData.getCurrent_elo() + "\n" +
                            "Highest Rank: " + rankData.getHighest_rank());

                    // Load the rank image using Glide
                    Glide.with(MainActivity.this)
                            .load(rankData.getCurrent_rank_image())
                            .into(rankImageView);
                } else {
                    // Handle case when API response is not successful
                    rankTextView.setText("Failed to fetch rank. Please check the username#tag.");
                    Toast.makeText(MainActivity.this, "Failed to fetch rank data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RankData> call, Throwable t) {
                // Handle failure due to network issues or other errors
                rankTextView.setText("Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "API request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}