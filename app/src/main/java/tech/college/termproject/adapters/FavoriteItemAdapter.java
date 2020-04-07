package tech.college.termproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import tech.college.termproject.R;
import tech.college.termproject.model.AllListModel;


public class FavoriteItemAdapter extends RecyclerView.Adapter<FavoriteItemAdapter.ViewHolder> {
    Context context;
    private ArrayList<AllListModel> listdata;

    // RecyclerView recyclerView;
    public FavoriteItemAdapter(Context context, ArrayList<AllListModel> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.all_item_list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AllListModel myListData = listdata.get(position);
        holder.ItemNameTxt.setText(myListData.getName());
        holder.ItemStatusTxt.setText(myListData.getStatus());
        holder.ItemImg.setImageResource(myListData.getImgId());
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public void favoriteUpdateList(ArrayList<AllListModel> updatedList) {
        listdata = updatedList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ItemImg;
        public TextView ItemNameTxt, ItemStatusTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            ItemImg = itemView.findViewById(R.id.item_img);
            ItemNameTxt = itemView.findViewById(R.id.item_name_txt);
            ItemStatusTxt = itemView.findViewById(R.id.item_status_txt);

        }
    }

}

