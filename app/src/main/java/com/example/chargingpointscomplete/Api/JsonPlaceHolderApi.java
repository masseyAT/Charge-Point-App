package com.example.chargingpointscomplete.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface JsonPlaceHolderApi {

    // The api allows custom get requests like postcode and charger type
    // This allowed me much faster requests to only search for the information I needed rather
    // than the 7000+ chargers that are available
    @Headers("Content-Type: application/json")
    @GET("connector-type-id/{id}/lat/{lat}/long/{longg}/dist/{mileage}/limit//format/json/")
    Call<Feed> getFeed(@Path("id") String id, @Path("lat") double lat,
                       @Path("longg") double longg, @Path("mileage") String mileage);

}

