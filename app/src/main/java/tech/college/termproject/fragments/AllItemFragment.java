package tech.college.termproject.fragments;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import tech.college.termproject.R;
import tech.college.termproject.adapters.AllListItemAdapter;
import tech.college.termproject.model.AllListModel;
import tech.college.termproject.other.FavoriteRealm;


public class AllItemFragment extends Fragment {
    EditText AllItemSearchEt;
    ArrayList<AllListModel> myListData = new ArrayList<>();
    AllListItemAdapter adapter;
    Realm mRealm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_item, container, false);
        AllItemSearchEt = view.findViewById(R.id.all_item_search_et);

        Realm.init(getActivity());
        mRealm = Realm.getDefaultInstance();

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
        myListData.clear();
        myListData.add(new AllListModel("Apple", "available", generateRandom()));
        myListData.add(new AllListModel("Mango", "available", generateRandom()));
        myListData.add(new AllListModel("kiwi", "Not available", generateRandom()));
        myListData.add(new AllListModel("Papaya", "Not available", generateRandom()));
        myListData.add(new AllListModel("watermelon", "available", generateRandom()));
        myListData.add(new AllListModel("Banana", "Not available", generateRandom()));
        myListData.add(new AllListModel("Pineapple", "Not available", generateRandom()));
        myListData.add(new AllListModel("orange", "available", generateRandom()));
        myListData.add(new AllListModel("Custard apple", "available", generateRandom()));


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new AllListItemAdapter(getContext(), myListData, new AllListItemAdapter.MyListnerr() {
            @Override
            public void itemOnClick(int i) {

                openDialog("Are you sure you want to add " + myListData.get(i).getName() + " into favorite list?", myListData.get(i));
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

    private void openDialog(String msg, final AllListModel selectedFavorite) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.confirm_dialog);

        Button DialogYesBtn = dialog.findViewById(R.id.dialog_yes_btn);
        Button DialogNoBtn = dialog.findViewById(R.id.dialog_no_btn);
        TextView ConfirmationTv = dialog.findViewById(R.id.confirmation_msg_tv);
        ConfirmationTv.setText(msg);

        DialogYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = null;
                try {
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {


                            try {
                                FavoriteRealm favoriteRealm = new FavoriteRealm();
                                favoriteRealm.name = selectedFavorite.getName();
                                favoriteRealm.status = selectedFavorite.getStatus();
                                favoriteRealm.image = selectedFavorite.getImgId();

                                realm.copyToRealm(favoriteRealm);
                            } catch (RealmPrimaryKeyConstraintException e) {
                                Toast.makeText(getActivity(), "Oops something wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }

                dialog.dismiss();
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

    private void filter(String text) {
        ArrayList<AllListModel> temp = new ArrayList<>();

        for (AllListModel d : myListData) {

            if (d.getName().toLowerCase().startsWith(text.toLowerCase())) {
                temp.add(d);
            }
        }
        adapter.updateList(temp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }
}
