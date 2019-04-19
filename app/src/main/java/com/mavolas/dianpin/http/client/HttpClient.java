package com.mavolas.dianpin.http.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author by Andy
 * Date on 2019-04-17.
 */
public class HttpClient {

  public static  Retrofit retrofit = new Retrofit.Builder()
            //设置数据解析器
            .addConverterFactory(GsonConverterFactory.create())
            //设置网络请求的Url地址
            .baseUrl("http://118.24.168.216:8001/api/")
            .build();

}
