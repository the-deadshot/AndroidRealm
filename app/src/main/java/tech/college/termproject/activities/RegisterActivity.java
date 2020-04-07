package tech.college.termproject.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import tech.college.termproject.R;
import tech.college.termproject.other.Register;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    final Context context = this;
    Realm mRealm;
    private TextView LoginUserTxt;
    private ImageView RegisterBackPress;
    private Button RegisterButton;
    private EditText RegisterNameEt, RegisterEmailEt, RegisterPasswordEt, RegisterPhoneEt, RegisterAgeEt;


    public static boolean isEmailValid(EditText email) {
        String strEmail = email.getText().toString().trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strEmail);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findIds();
        Realm.init(getApplicationContext());
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_user_txt:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            case R.id.register_button:
                registerButtonClick();
                break;
            case R.id.register_back_press:
                onBackPressed();
                break;
        }
    }

    private void findIds() {
        RegisterNameEt = findViewById(R.id.register_name_et);
        RegisterEmailEt = findViewById(R.id.register_email_et);
        RegisterPasswordEt = findViewById(R.id.register_password_et);
        RegisterPhoneEt = findViewById(R.id.register_phone_et);
        RegisterAgeEt = findViewById(R.id.register_age_et);
        LoginUserTxt = findViewById(R.id.login_user_txt);
        LoginUserTxt.setOnClickListener(this);
        RegisterBackPress = findViewById(R.id.register_back_press);
        RegisterBackPress.setOnClickListener(this);
        RegisterButton = findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(this);

    }

    public void registerButtonClick() {

        if (isEmpty(RegisterNameEt)) {
            openDialog("Please enter your \n name");
        } else if (!isEmailValid(RegisterEmailEt)) {
            openDialog("Please check your email \n format");
        } else if (isEmpty(RegisterPasswordEt)) {
            openDialog("Please enter  \n password");
        } else if (isEmpty(RegisterPhoneEt)) {
            openDialog("please enter your phone \n number");
        } else if (isEmpty(RegisterAgeEt)) {
            openDialog("Please enter your \n age");
        } else {

            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {


                        try {
                            Register register = new Register();
                            register.email = RegisterEmailEt.getText().toString().trim();
                            // employee.age = Integer.parseInt(inAge.getText().toString().trim());
                            register.name = RegisterNameEt.getText().toString().trim();
                            register.password = RegisterPasswordEt.getText().toString().trim();
                            register.phone = RegisterPhoneEt.getText().toString().trim();
                            register.age = RegisterAgeEt.getText().toString().trim();

                            realm.copyToRealm(register);
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("Email_id", RegisterEmailEt.getText().toString().trim());
                            startActivity(intent);
                            Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        } catch (RealmPrimaryKeyConstraintException e) {
                            openDialog("you are already register. \nPlease login ");
                        }
                    }
                });
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }
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
