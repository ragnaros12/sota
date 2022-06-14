package com.company.vika.ui.rasp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.vika.R;
import com.company.vika.models.Group;
import com.company.vika.models.Pair;

import java.util.List;

public class PairAdapter extends RecyclerView.Adapter<PairAdapter.Holder> {
    List<Pair> groups;
    Context context;
    LayoutInflater layoutInflater;

    public PairAdapter(List<Pair> groups, Context context) {
        this.groups = groups;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.group_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(!groups.get(position).getName().equals(""))
            holder.textView.setText("предмет: " + groups.get(position).getName() + "\nПреподаватель: " + groups.get(position).getTeacher());
        else
            holder.textView.setText("Пара отсутствует");
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.group_item);
        }
    }
}
