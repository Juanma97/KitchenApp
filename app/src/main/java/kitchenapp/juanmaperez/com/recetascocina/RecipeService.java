package kitchenapp.juanmaperez.com.recetascocina;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeService {

    String API_ROUTE = "search";
    //String API_KEY = "&app_key=" + Credentials.API_KEY;
    //String APP_ID = "&app_id=" + Credentials.APP_ID;

    @GET(API_ROUTE)
    Call<ResponseAPI> getRecipe(@Query("q") String q, @Query("app_id") String APP_ID, @Query("app_key") String API_KEY);

}
