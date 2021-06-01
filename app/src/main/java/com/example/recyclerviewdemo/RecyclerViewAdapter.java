package com.example.recyclerviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Card> list;
    private List<Card> fullList; //for search option
    private LayoutInflater mInflater;
    ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<Card> list) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;

        //for search method
        fullList = new ArrayList<>();
        fullList.addAll(list);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    @Override @SuppressLint("SetTextI18n")
    public void onBindViewHolder(ViewHolder holder, int position) {
        Card card = list.get(position);

        holder.title.setText(card.getTitleExample());
        holder.text.setText(card.getTextExample());

        holder.button.setOnClickListener(v -> Toast.makeText(context, "button in card number " + position, Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, text;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_example);
            text = itemView.findViewById(R.id.text_example);
            button = itemView.findViewById(R.id.button_example);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Card> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {

                    filteredList.addAll(fullList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Card card : fullList) {
                        if (card.getTitleExample().toLowerCase().contains(filterPattern)) {
                            filteredList.add(card);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list.clear();
                list.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }
}