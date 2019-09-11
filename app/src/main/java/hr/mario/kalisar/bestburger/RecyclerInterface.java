package hr.mario.kalisar.bestburger;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecyclerInterface {

    String JSONURL = "https://raw.githubusercontent.com/Mario94k/Burgers/master/";

    @GET("Burger21.json")
    Call<String> getString();
}