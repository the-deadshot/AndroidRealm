package tech.college.termproject.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import io.realm.Realm;
import io.realm.RealmResults;
import tech.college.termproject.R;
import tech.college.termproject.activities.MainActivity;
import tech.college.termproject.other.Register;

public class WelcomeFragment extends Fragment implements View.OnClickListener {
    final Context context = getActivity();
    Realm mRealm;
    RealmResults<Register> results;
    Button UpdateDataBtn;
    private TextView EditInfoTxt;
    private EditText EditNameTxt, EditPasswordTxt, EditPhoneTxt, EditAgeTxt;

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

        if (isEmpty(EditNameTxt)) {
            openDialog("Please enter your \n name");
        } else if (isEmpty(EditPasswordTxt)) {
            openDialog("Please enter  \n password");
        } else if (isEmpty(EditPhoneTxt)) {
            openDialog("please enter your phone \n number");
        } else if (isEmpty(EditAgeTxt)) {
            openDialog("Please enter your \n age");
        } else {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    Register register = realm.where(Register.class).equalTo(Register.PROPERTY_Email, MainActivity.emailId).findFirst();
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
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }
}
