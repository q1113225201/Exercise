package com.sjl.exercise.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjl.exercise.R;
import com.sjl.exercise.listener.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * TextAdapter
 *
 * @author 林zero
 * @date 2019/4/22
 */
public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {
    private List<String> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public TextAdapter(List<String> list,ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvText.setText(list.get(i));
        viewHolder.itemView.setOnClickListener(v -> {
            if(itemClickListener!=null){
                itemClickListener.onClick(v,viewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }
}
