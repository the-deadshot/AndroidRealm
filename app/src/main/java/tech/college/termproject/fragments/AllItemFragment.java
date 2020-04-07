package tech.college.termproject.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.college.termproject.R;
import tech.college.termproject.adapters.AllListItemAdapter;
import tech.college.termproject.model.AllListModel;


public class AllItemFragment extends Fragment {
    EditText AllItemSearchEt;
    ArrayList<AllListModel> myListData = new ArrayList<>();
    AllListItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_item, container, false);
        AllItemSearchEt = view.findViewById(R.id.all_item_search_et);

        AllItemSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        myListData.add(new AllListModel("Apple", "available", generateRandom()) );
        myListData.add(new AllListModel("Mango", "available", generateRandom()));
        myListData.add(new AllListModel("kiwi", "Not available", generateRandom()));
        myListData.add(new AllListModel("Papaya", "Not available", generateRandom()));
        myListData.add( new AllListModel("watermelon", "available", generateRandom()));
        myListData.add(new AllListModel("Banana", "Not available", generateRandom()));
        myListData.add( new AllListModel("Pineapple", "Not available", generateRandom()));
        myListData.add( new AllListModel("orange", "available", generateRandom()));
        myListData.add( new AllListModel("Custard apple", "available", generateRandom()));


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
         adapter = new AllListItemAdapter(getContext(), myListData, new AllListItemAdapter.MyListnerr() {
            @Override
            public void itemOnClick(int i) {

                openDialog("Are you sure you want to add " + myListData.get(i).getName() + " into favorite list?");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private int generateRandom() {
        int randomnum = ((int) (Math.random() * (9 - 1))) + 1;
        Resources resources = getContext().getResources();
        final int resourceId = resources.getIdentifier("a" + randomnum, "drawable",
                getContext().getPackageName());

        return resourceId;
    }

    private void openDialog(String msg) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.confirm_dialog);

        Button DialogYesBtn = dialog.findViewById(R.id.dialog_yes_btn);
        Button DialogNoBtn = dialog.findViewById(R.id.dialog_no_btn);
        TextView ConfirmationTv = dialog.findViewById(R.id.confirmation_msg_tv);
        ConfirmationTv.setText(msg);

        DialogYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        DialogNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    void filter(String text){
        ArrayList<AllListModel> temp = new ArrayList<>();

        for(AllListModel d: myListData){

            if(d.getName().toLowerCase().startsWith(text.toLowerCase())){
                temp.add(d);
            }
        }
        adapter.updateList(temp);
    }
}
