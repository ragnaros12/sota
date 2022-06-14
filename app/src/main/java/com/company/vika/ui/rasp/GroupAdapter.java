package com.company.vika.ui.rasp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.vika.R;
import com.company.vika.models.Group;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.Holder> {
    List<Group> groups;
    Context context;
    LayoutInflater layoutInflater;
    Click click;
    interface Click{
        void Clicked(Group group);
    }
    public GroupAdapter(List<Group> groups, Context context, Click click) {
        this.groups = groups;
        this.click = click;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.group_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(groups.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.Clicked(groups.get(position));
            }
        });
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
