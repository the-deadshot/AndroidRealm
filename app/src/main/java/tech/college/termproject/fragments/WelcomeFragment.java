package tech.college.termproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import tech.college.termproject.MainActivity;
import tech.college.termproject.R;
import tech.college.termproject.other.Register;

public class WelcomeFragment extends Fragment implements View.OnClickListener {
    final Context context = getActivity();
    Realm mRealm;
    RealmResults<Register> results;
    private TextView EditInfoTxt;
    private EditText EditNameTxt, EditPasswordTxt, EditPhoneTxt, EditAgeTxt;
    Button UpdateDataBtn;
    private String checkName;
    private String checkPassword;
    private String checkPhone;
    private String checkAge;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        findIds(view);
        Realm.init(getActivity());
        mRealm = Realm.getDefaultInstance();
        readData();
        return view;
    }

    void findIds(View view) {
        EditInfoTxt = view.findViewById(R.id.edit_info_txt);
        EditInfoTxt.setOnClickListener(this);
        EditNameTxt = view.findViewById(R.id.edit_name_txt);
        EditPasswordTxt = view.findViewById(R.id.edit_password_txt);
        EditPhoneTxt = view.findViewById(R.id.edit_phone_txt);
        EditAgeTxt = view.findViewById(R.id.edit_age_txt);
        UpdateDataBtn = view.findViewById(R.id.update_button);
        UpdateDataBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_info_txt:
                EditNameTxt.setEnabled(true);
                EditPasswordTxt.setEnabled(true);
                EditPhoneTxt.setEnabled(true);
                EditAgeTxt.setEnabled(true);
                UpdateDataBtn.setEnabled(true);
                break;

            case R.id.update_button:
                updateData();
                Log.e("gtyu", "");
                break;
        }
    }

    private void readData() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                results = realm.where(Register.class).equalTo("email", MainActivity.emailId).findAll();

                for (Register register : results) {
                    EditNameTxt.setText(register.name);
                    EditPasswordTxt.setText(register.password);
                    EditPhoneTxt.setText(register.phone);
                    EditAgeTxt.setText(register.age);
                }
            }
        });
    }

    private void updateData() {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Register register = realm.where(Register.class).equalTo(Register.PROPERTY_Email, MainActivity.emailId).findFirst();
                Log.e("test", register.toString());
                if (register == null) {
                    register = realm.createObject(Register.class, MainActivity.emailId);
                }

                register.name = EditNameTxt.getText().toString().trim();
                register.password = EditPasswordTxt.getText().toString().trim();
                register.phone = EditPhoneTxt.getText().toString().trim();
                register.age = EditAgeTxt.getText().toString().trim();
                realm.copyToRealmOrUpdate(register);
                EditNameTxt.setEnabled(false);
                EditPasswordTxt.setEnabled(false);
                EditPhoneTxt.setEnabled(false);
                EditAgeTxt.setEnabled(false);
                UpdateDataBtn.setEnabled(false);
                Toast.makeText(getActivity(), "Info Updated", Toast.LENGTH_SHORT).show();



            }
        });
    }
}
