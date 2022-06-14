package com.company.vika.ui.contact;

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
import com.company.vika.databinding.FragmentContactBinding;
import com.company.vika.models.AbiData;
import com.company.vika.ui.abi.AbiDataAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactFragment extends Fragment {
    private FragmentContactBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        NetworkService.getInstance().getApi().getContacts().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        Document document = Jsoup.parse(response.body().string());
                        List<String> places = new ArrayList<>();
                        List<String> contacts = new ArrayList<>();
                        List<String> rasp = new ArrayList<>();
                        boolean flag = false;
                        for (Element element : document.select(".art-article").get(0).select("span")){
                            if(!element.text().equals("")){
                                if(flag){
                                    if(element.text().startsWith("Как нас")){
                                        flag = false;
                                    }
                                    else{
                                        rasp.add(element.text());
                                    }
                                }
                                else if(element.text().startsWith("Россия")){
                                    places.add(element.text());
                                }
                                else if(element.text().startsWith("Тел") || element.text().startsWith("Факс") || element.text().startsWith("E-mail")){
                                    contacts.add(element.text().trim());
                                }
                                else if(element.text().startsWith("Время")){
                                    flag = true;
                                }
                            }
                        }
                        binding.addresses.setAdapter(new TextAdapter(places, getContext()));
                        binding.contacts.setAdapter(new TextAdapter(contacts, getContext()));
                        binding.rasps.setAdapter(new TextAdapter(rasp, getContext()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
