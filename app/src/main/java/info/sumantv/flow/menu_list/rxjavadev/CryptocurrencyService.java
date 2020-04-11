package info.sumantv.flow.menu_list.rxjavadev;


import info.sumantv.flow.menu_list.rxjavadev.pojo.Crypto;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CryptocurrencyService {

    String BASE_URL = "https://api.cryptonator.com/api/full/";

    @GET("{coin}-usd")
    Observable<Crypto> getCoinData(@Path("coin") String coin);
}
