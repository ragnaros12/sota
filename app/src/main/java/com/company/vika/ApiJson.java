package com.company.vika;

import com.company.vika.models.Group;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiJson {

    @GET("/b/0X3B")
    Call<List<Group>> getRasp();
}
