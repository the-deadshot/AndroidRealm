package tech.college.termproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import tech.college.termproject.R;

public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private TextView EditInfoTxt;
    private EditText EditNameTxt, EditPasswordTxt, EditPhoneTxt, EditAgeTxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        findIds(view);


        return view;
    }

    void findIds(View view) {

        EditInfoTxt = view.findViewById(R.id.edit_info_txt);
        EditInfoTxt.setOnClickListener(this);
        EditNameTxt = view.findViewById(R.id.edit_name_txt);
        EditPasswordTxt = view.findViewById(R.id.edit_password_txt);
        EditPhoneTxt = view.findViewById(R.id.edit_phone_txt);
        EditAgeTxt = view.findViewById(R.id.edit_age_txt);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_info_txt:
                EditNameTxt.setEnabled(true);
                EditPasswordTxt.setEnabled(true);
                EditPhoneTxt.setEnabled(true);
                EditAgeTxt.setEnabled(true);
                break;
        }
    }
}
