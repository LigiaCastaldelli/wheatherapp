package com.castaldelli.weatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: WeatherService = retrofit.create(WeatherService::class.java)
        val callback = service.getCurrentWeather(q="Araraquara")

        val activity = this

        callback.enqueue(object : Callback<CurrentWeather>{
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<CurrentWeather>, r: Response<CurrentWeather>) {
                if (r.isSuccessful) {
                    val response = r.body()

                    findViewById<TextView>(R.id.txTemp).text = "${response?.current?.temp_c}°C"
                    findViewById<TextView>(R.id.txCidade).text = response?.location?.name
                    findViewById<TextView>(R.id.txCondition).text = response?.current?.condition?.text

                    val condition = findViewById<ImageView>(R.id.imCondition)
                    Glide.with(activity)
                        .load("https:${response?.current?.condition?.icon}")
                        .into(condition)
                } else {
                    findViewById<TextView>(R.id.txTemp).text = "Sistema indisponível"
                }

            }
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e("Ligia", "ooopppss!")
            }
        })
    }
}