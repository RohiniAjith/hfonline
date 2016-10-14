//package rest;
//
//import com.healthyfish.healthyfish.api.Url;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by User on 30-08-2016.
// */
//public class ApiClient {
//
//
//    private static Retrofit retrofit = null;
//
//
//    public static Retrofit getClient() {
//        if (retrofit==null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(Url.base_link)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }
//}
