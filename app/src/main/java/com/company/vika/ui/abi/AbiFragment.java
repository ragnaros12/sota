package com.company.vika.ui.abi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.company.vika.NetworkService;
import com.company.vika.databinding.FragmentAbiBinding;
import com.company.vika.models.AbiData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbiFragment extends Fragment {

    private FragmentAbiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAbiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NetworkService.getInstance().getApi().getAbiData().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        Document data = Jsoup.parse(response.body().string());
                        Element element = data.select(".art-article").get(0);
                        List<AbiData> abiDatas = new ArrayList<>();
                        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{2}");
                        for (Element el : element.select("p")){
                            if (!el.text().equals("")) {
                                abiDatas.add(new AbiData(el.text(), !pattern.matcher(el.text()).find()));
                            }
                        }

                        RecyclerView recyclerView = binding.listAbiData;
                        recyclerView.setAdapter(new AbiDataAdapter(abiDatas, getContext()));
                    }
                    catch (IOException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),response.code() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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