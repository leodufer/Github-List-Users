package py.edu.facitec.githubusers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements Callback<List<User>> {

    ListView usersListView;
    TextView errorMessageTextView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usersListView = findViewById(R.id.listviewUsers);
        errorMessageTextView = findViewById(R.id.textViewErrorMessage);
        progressBar = findViewById(R.id.progressBar);

        UserService service = new RestAdapter.Builder()
                                        .setEndpoint("https://api.github.com")
                                        .build()
                                        .create(UserService.class);

        service.getUsers(this);

    }

    @Override
    public void success(List<User> users, Response response) {
        progressBar.setVisibility(View.GONE);
        usersListView.setVisibility(View.VISIBLE);
        ArrayAdapter adapter = new ArrayAdapter(
                                            this,
                                                    android.R.layout.simple_list_item_1,
                                                    users
                                                );
        usersListView.setAdapter(adapter);
    }

    @Override
    public void failure(RetrofitError error) {
        progressBar.setVisibility(View.GONE);
        errorMessageTextView.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(error.getLocalizedMessage());
    }
}