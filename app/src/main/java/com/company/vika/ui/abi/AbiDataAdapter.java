package com.company.vika.ui.abi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.vika.R;
import com.company.vika.models.AbiData;

import java.util.List;

public class AbiDataAdapter extends RecyclerView.Adapter<AbiDataAdapter.AbiDataItem> {
    List<AbiData> list;
    Context context;
    LayoutInflater layoutInflater;

    public AbiDataAdapter(List<AbiData> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AbiDataItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AbiDataItem(layoutInflater.inflate(viewType == 1 ? R.layout.abi_data_item_title : R.layout.abi_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AbiDataItem holder, int position) {
        AbiData abiData = list.get(position);
        holder.data.setText(abiData.getTitle());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 1 : 2;
    }

    class AbiDataItem extends RecyclerView.ViewHolder{
        TextView data;

        public AbiDataItem(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.abi_data_text);
        }
    }
}
