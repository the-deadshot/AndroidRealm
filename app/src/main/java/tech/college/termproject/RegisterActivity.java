package tech.college.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView LoginUserTxt;
    private ImageView RegisterBackPress;
    private Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LoginUserTxt = findViewById(R.id.login_user_txt);
        LoginUserTxt.setOnClickListener(this);
        RegisterBackPress = findViewById(R.id.register_back_press);
        RegisterBackPress.setOnClickListener(this);
        RegisterButton = findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_user_txt:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;

            case R.id.register_button:
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                break;

            case R.id.register_back_press:
                onBackPressed();
                break;

        }
    }
}
