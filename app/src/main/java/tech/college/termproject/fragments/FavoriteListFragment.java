package tech.college.termproject.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import tech.college.termproject.R;
import tech.college.termproject.adapters.FavoriteItemAdapter;
import tech.college.termproject.model.AllListModel;
import tech.college.termproject.other.FavoriteRealm;


public class FavoriteListFragment extends Fragment {

    EditText FavoriteSearchEt;
    ArrayList<AllListModel> myListData = new ArrayList<>();
    FavoriteItemAdapter favoriteItemAdapter;
    RealmResults<FavoriteRealm> results;
    Realm mRealm;
    RecyclerView FavoriteRecyclerView;
    boolean firstTimeVisible = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Realm.init(getActivity());
        mRealm = Realm.getDefaultInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        FavoriteSearchEt = view.findViewById(R.id.favorite_search_et);
        FavoriteRecyclerView = view.findViewById(R.id.favorite_recyclerView);


        Realm.init(getActivity());
        mRealm = Realm.getDefaultInstance();
        textWatcher();

        favoriteItemAdapter = new FavoriteItemAdapter(getContext(), myListData);
        FavoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavoriteRecyclerView.setAdapter(favoriteItemAdapter);
        readFavoriteList();
        firstTimeVisible = true;
        return view;
    }


    void filter(String text) {
        ArrayList<AllListModel> temp = new ArrayList<>();

        for (AllListModel d : myListData) {

            if (d.getName().toLowerCase().startsWith(text.toLowerCase())) {
                temp.add(d);
            }
        }
        favoriteItemAdapter.favoriteUpdateList(temp);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (firstTimeVisible) {
            mRealm = Realm.getDefaultInstance();
            readFavoriteList();
        }
    }

    private void readFavoriteList() {
        myListData.clear();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                results = realm.where(FavoriteRealm.class).findAll();

                for (FavoriteRealm favoriteRealm : results) {
                    myListData.add(new AllListModel(favoriteRealm.name, favoriteRealm.status, favoriteRealm.image));
                }
                favoriteItemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void textWatcher() {

        FavoriteSearchEt.addTextChangedListener(new TextWatcher() {
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

}
