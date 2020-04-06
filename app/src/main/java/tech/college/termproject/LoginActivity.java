package tech.college.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LoginButton;
    private TextView CreateAccountTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.login_button);
        LoginButton.setOnClickListener(this);
        CreateAccountTxt = findViewById(R.id.create_account_txt);
        CreateAccountTxt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;

            case R.id.create_account_txt:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }
}
