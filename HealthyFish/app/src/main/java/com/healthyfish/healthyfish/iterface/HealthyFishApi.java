package com.healthyfish.healthyfish.iterface;

import com.healthyfish.healthyfish.api.Url;


import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by User on 25-08-2016.
 */
public interface HealthyFishApi {


    @FormUrlEncoded
    @POST(Url.home_url)
    public void dashboadRequest(
            @Field("req_id") String req_id,
            Callback<Response> response);

    @FormUrlEncoded
    @POST(Url.registration_url)
    public void registrationRequest(
            @Field("reg_val_name") String name,
            @Field("reg_val_email") String email,
            @Field("reg_val_mobile") String mobile,
            @Field("reg_val_password") String password,
            @Field("reg_val_location")String location,
            Callback<Response> response);


    @FormUrlEncoded
    @POST(Url.registration_url)
    public void otpSend(
            @Field("reg_val_otp") String otp,
            @Field("reg_val_id") String id,

            Callback<Response> response);

    @FormUrlEncoded
    @POST(Url.login_url)
    public void login(
            @Field("reg_val_email") String username,
            @Field("reg_val_password") String password,

            Callback<Response> response);

    @FormUrlEncoded
    @POST(Url.homeheader_url)
    public void homeheader(
            @Field("page_number") String page_number,
            @Field("display_size") String displaysize,
            @Field("category_id") String category_id,
            @Field("req_id") String req_id,
            Callback<Response> response);


    @FormUrlEncoded
    @POST(Url.other_url)
    public void otherRequest(
            @Field("req_id") String req_id,
            @Field("page_req_id") String url_id,
            Callback<Response> response);

    @FormUrlEncoded
    @POST(Url.hf_addto_cart_url)
    public void sendShopCart(
            @Field("hf_user_id") String user_id,
            @Field("req_id") String req_id,
            @Field("hf_product_id") String product_id,
            @Field("hf_quantity") String quantity,
            @Field("hf_quantity_type") String type,
            @Field("hf_quantity_amount") String amount,
            Callback<Response> response);

    @FormUrlEncoded
    @POST(Url.shopping_cart_url)
    public void ReceiveShopCart(
            @Field("req_id") String user_id,
            @Field("page_number") String start_value,
            @Field("display_size") String end_value,
            Callback<Response> response);

    @FormUrlEncoded
    @POST(Url.place_order_url)
    public void placeorder(
            @Field("req_id") String user_id,
            Callback<Response> response);
    @FormUrlEncoded
    @POST(Url.hf_product_display)
    public void categoryDetailsRequest(
            @Field("req_id") String req_id,
            @Field("prod_id") String product_id,
            Callback<Response> response);

    @FormUrlEncoded
    @POST(Url.view_more_url)
    public void viewMore(
            @Field("page_number") String page_number,
            @Field("display_size") String displaysize,
            @Field("listing_id") String listing_id,
            @Field("req_id") String req_id,
            Callback<Response> response);
    @FormUrlEncoded
    @POST(Url.recipe_url)
    public void recipeRequest(
            @Field("page_number") String start_value,
            @Field("display_size") String end_value,
            @Field("req_id") String id,
            Callback<Response> response);
}



