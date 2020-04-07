package tech.college.termproject.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmResults;
import tech.college.termproject.R;
import tech.college.termproject.other.Register;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    final Context context = this;
    Realm mRealm;
    RealmResults<Register> results;
    private Button LoginButton;
    private TextView CreateAccountTxt;
    private EditText LoginEmailEt, LoginPasswordEt;
    private String checkEmail;
    private String checkPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginEmailEt = findViewById(R.id.login_email_et);
        LoginPasswordEt = findViewById(R.id.login_password_et);
        LoginButton = findViewById(R.id.login_button);
        LoginButton.setOnClickListener(this);
        CreateAccountTxt = findViewById(R.id.create_account_txt);
        CreateAccountTxt.setOnClickListener(this);

        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                loginBtnClick();
                break;

            case R.id.create_account_txt:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }

    void loginBtnClick() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                results = realm.where(Register.class).equalTo("email", LoginEmailEt.getText().toString().trim()).findAll();

                for (Register register : results) {
                    checkEmail = register.email;
                    checkPassword = register.password;
                }
            }
        });
        if (isEmpty(LoginEmailEt)) {
            openDialog("Please check your email \n Address");
            return;
        } else if (isEmpty(LoginPasswordEt)) {
            openDialog("Please enter your \n password");
            return;
        }
        if (results.isEmpty()) {
            openDialog("Wrong \ncredentials");
        } else if (LoginEmailEt.getText().toString().trim().equals(checkEmail) && LoginPasswordEt.getText().toString().trim().equals(checkPassword)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Email_id", LoginEmailEt.getText().toString().trim());
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
            finishAffinity();
        } else {
            openDialog("Please check your \n password");

        }
    }

    private void openDialog(String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.error_dialog);

        Button DialogOkBtn = dialog.findViewById(R.id.dialog_ok_btn);
        TextView ErrorMsgTv = dialog.findViewById(R.id.error_msg_tv);
        ErrorMsgTv.setText(msg.toString());

        DialogOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }
}
