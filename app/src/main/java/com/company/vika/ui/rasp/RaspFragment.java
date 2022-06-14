package com.company.vika.ui.rasp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.company.vika.NetworkService;
import com.company.vika.databinding.FragmentRaspBinding;
import com.company.vika.models.Group;
import com.company.vika.models.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaspFragment extends Fragment {

    private FragmentRaspBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRaspBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = binding.groups;

        NetworkService.getInstance().getApiJson().getRasp().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                Toast.makeText(getContext(), response.body().size() + "", Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(new GroupAdapter(response.body(), getContext(), new GroupAdapter.Click() {
                    @Override
                    public void Clicked(Group group) {
                        recyclerView.setAdapter(new PairAdapter(group.getPairs(), getContext()));
                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}