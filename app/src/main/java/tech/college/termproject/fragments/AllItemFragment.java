package tech.college.termproject.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Random;

import tech.college.termproject.R;
import tech.college.termproject.adapters.AllListItemAdapter;
import tech.college.termproject.model.AllListModel;


public class AllItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all_item, container, false);

        AllListModel[] myListData = new AllListModel[] {
                new AllListModel("Apple","available", generateRandom()),
                new AllListModel("Mango","available", generateRandom()),
                new AllListModel("kiwi","Not available", generateRandom()),
                new AllListModel("Papaya","Not available", generateRandom()),
                new AllListModel("watermelon", "available",generateRandom()),
                new AllListModel("Banana","Not available", generateRandom()),
                new AllListModel("Pineapple","Not available", generateRandom()),
                new AllListModel("orange","available", generateRandom()),
                new AllListModel("Custard apple","available", generateRandom()),

        };

        RecyclerView recyclerView =  view.findViewById(R.id.recyclerView);
        AllListItemAdapter adapter = new AllListItemAdapter(getContext(), myListData, new AllListItemAdapter.MyListnerr() {
            @Override
            public void itemOnClick(int i) {
                Toast.makeText(getActivity(), "asdfas"+i, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    int generateRandom(){
        int randomnum = ((int) (Math.random()*(9 - 1))) + 1;
        Resources resources = getContext().getResources();
        final int resourceId = resources.getIdentifier("a" + randomnum, "drawable",
                getContext().getPackageName());

        return resourceId;
    }
}
