package com.example.woltandroid;

import static com.example.woltandroid.utils.Constants.GET_ALL_RESTAURANTS_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.woltandroid.model.BasicUser;
import com.example.woltandroid.model.Driver;
import com.example.woltandroid.model.Restaurant;
import com.example.woltandroid.model.User;
import com.example.woltandroid.utils.LocalTimeSerializer;
import com.example.woltandroid.utils.RestOperations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WoltMain extends AppCompatActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wolt_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String userInfo = intent.getStringExtra("userJsonObject");
        Gson gson = new Gson();

        JsonObject json = new JsonParser().parse(userInfo).getAsJsonObject();

        //currentUser = gson.fromJson(userInfo, User.class);
        //var connectedUser = gson.fromJson(userInfo, User.class);

        if (json.has("vehicleType")) {
            currentUser = gson.fromJson(userInfo, Driver.class);
        } else if (json.has("restaurantName")) {
            Toast.makeText(this,
                    "Restaurant accounts cannot use the mobile app.",
                    Toast.LENGTH_LONG).show();

            Intent back = new Intent(WoltMain.this, MainActivity.class);
            startActivity(back);
            finish();
            return;
        } else {
            currentUser = gson.fromJson(userInfo, BasicUser.class);
        }

        if (currentUser instanceof Driver) {
            Intent driverIntent = new Intent(WoltMain.this, DriverOrders.class);
            driverIntent.putExtra("userJsonObject", userInfo);
            startActivity(driverIntent);
            finish();
            return;
        } else if (currentUser instanceof Restaurant) {
        }



        if (!(currentUser instanceof Driver) && !(currentUser instanceof Restaurant)) {
            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(()->{
                try {
                    String response = RestOperations.sendGet(GET_ALL_RESTAURANTS_URL);
                    handler.post(()->{
                       try{
                           if(!response.equals("Error") ){

                               GsonBuilder gsonBuilder = new GsonBuilder();
                               gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
                               Gson gsonRestaurants = gsonBuilder.setPrettyPrinting().create();
                               Type restaurantListType = new TypeToken<List<Restaurant>>(){}.getType();
                               List<Restaurant> restaurantsListFromjson = gsonRestaurants.fromJson(response, restaurantListType);

                               ListView restaurantListElement = findViewById(R.id.restaurantList);
                               ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurantsListFromjson);
                               restaurantListElement.setAdapter(arrayAdapter);

                                restaurantListElement.setOnItemClickListener((parent, view, position, id)->{
                                    Restaurant selectedRestaurant = restaurantsListFromjson.get(position);
                                    System.out.println(restaurantsListFromjson.get(position));
                                    Intent intentMenu = new Intent(WoltMain.this, MenuActivity.class);
                                    intentMenu.putExtra("restaurantId", selectedRestaurant.getId());
                                    intentMenu.putExtra("userId", currentUser.getId());
                                    startActivity(intentMenu);
                                });
                           }
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    public void viewPurchaseHistory(View view) {
        Intent intent = new Intent(WoltMain.this, MyOrders.class);
        intent.putExtra("id", currentUser.getId());
        startActivity(intent);
    }

    public void viewMyAccount(View view) {
    }
}