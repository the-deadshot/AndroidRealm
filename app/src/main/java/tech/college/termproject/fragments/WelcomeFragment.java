package tech.college.termproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import tech.college.termproject.R;

public class WelcomeFragment extends Fragment {

    private TextView EditTxtInfo;
    private EditText EditNameTxt, EditPasswordTxt, EditPhoneTxt, EditAgeTxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_welcome, container, false);

        return view;
    }
}
