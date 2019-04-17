package com.mavolas.dianpin.api;

import com.mavolas.dianpin.bean.Sellers_Item;
import com.mavolas.dianpin.bean.Sellers_Status;
import com.mavolas.dianpin.common.ResponseCls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author by Andy
 * Date on 2019-04-17.
 */
public interface SellersApi {

    @GET("Sellers/getSellers")
    Call<ResponseCls<List<Sellers_Item>>> getSellers_Info(@Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);


    @GET("Values/getStatus")
    Call<ResponseCls<List<Sellers_Status>>> getSellers_Status();

    @POST("Sellers/SaveStatus")
    Call<ResponseCls<String>> SaveStatus(@Query("id") String id, @Query("status") String status, @Query("remark") String remark);

}
