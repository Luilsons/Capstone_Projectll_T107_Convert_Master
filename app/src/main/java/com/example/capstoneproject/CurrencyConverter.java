package com.example.capstoneproject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class CurrencyConverter {
    private static final String API_URL = "https://app.currencyapi.com/v4/latest/";
    private static final String API_KEY = "cur_live_rf2brlQTEQhlT4MIqwzBsBZ8dSxIoUySMlAYdTry";

    public interface CurrencyCallback {
        void onSuccess(double conversionRate);
        void onFailure(Exception e);
    }

    public static void convertCurrency(String fromCurrency, String toCurrency, double amount, CurrencyCallback callback) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(API_URL + fromCurrency + "?apikey=" + API_KEY)
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                double rate = jsonResponse.getJSONObject("rates").getDouble(toCurrency);

                // Return the conversion rate via the callback
                callback.onSuccess(rate);
            } catch (Exception e) {
                callback.onFailure(e);
            }
        }).start();
    }
}
