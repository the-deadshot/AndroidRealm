package tech.college.termproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.college.termproject.R;
import tech.college.termproject.model.AllListModel;

public class AllListItemAdapter extends RecyclerView.Adapter<AllListItemAdapter.ViewHolder>{
    Context context;
    private ArrayList<AllListModel> listdata;
    MyListnerr myListner;

    // RecyclerView recyclerView;
    public AllListItemAdapter(Context context, ArrayList<AllListModel> listdata, MyListnerr myListner) {
        this.context = context;
        this.listdata = listdata;
        this.myListner = myListner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.all_item_list_view, parent, false);
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

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ItemImg;
        public TextView ItemNameTxt, ItemStatusTxt;
        public LinearLayout CardLl;

        public ViewHolder(View itemView) {
            super(itemView);
            ItemImg = itemView.findViewById(R.id.item_img);
            ItemNameTxt = itemView.findViewById(R.id.item_name_txt);
            ItemStatusTxt = itemView.findViewById(R.id.item_status_txt);
            CardLl = itemView.findViewById(R.id.card_ll);

            CardLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListner.itemOnClick(getLayoutPosition());
                }
            });

        }
    }

    public void updateList(ArrayList<AllListModel> updatedList){
        listdata = updatedList;
        notifyDataSetChanged();
    }

    public interface MyListnerr{
        void itemOnClick(int i);
    }
}