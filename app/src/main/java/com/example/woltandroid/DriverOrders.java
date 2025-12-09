package com.example.woltandroid;

import static com.example.woltandroid.utils.Constants.GET_PREPARING_ORDERS;
import static com.example.woltandroid.utils.Constants.UPDATE_ORDER_STATUS;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.woltandroid.model.FoodOrder;
import com.example.woltandroid.model.OrderStatus;
import com.example.woltandroid.model.User;
import com.example.woltandroid.utils.Constants;
import com.example.woltandroid.utils.RestOperations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriverOrders extends AppCompatActivity {

    private User currentUser;
    private List<FoodOrder> orders;
    private ArrayAdapter<FoodOrder> adapter;
    private ListView ordersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_orders);

        ordersListView = findViewById(R.id.driverOrdersList);

        orders = new ArrayList<>();

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                orders);
        ordersListView.setAdapter(adapter);

        ordersListView.setOnItemClickListener((parent, view, position, id) -> {
            FoodOrder selected = orders.get(position);
            // optional: check status first
            updateOrderStatus(selected, "IN_DELIVERY", position);
        });

        loadPreparingOrders();
    }

    private void loadPreparingOrders() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String response = RestOperations.sendGet(GET_PREPARING_ORDERS);

                handler.post(() -> {
                    if ("Error".equals(response) || response.isEmpty()) {
                        Toast.makeText(this, "Failed to load orders", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<FoodOrder>>() {}.getType();
                        List<FoodOrder> loadedOrders = gson.fromJson(response, listType);

                        orders.clear();
                        orders.addAll(loadedOrders);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing orders", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                handler.post(() ->
                        Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void markOrderInDelivery(FoodOrder order) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = UPDATE_ORDER_STATUS + order.getId()
                        + "/status?status=IN_DELIVERY";

                String response = RestOperations.sendPut(url, ""); // or sendPut if you have it

                handler.post(() -> {
                    if ("Error".equals(response)) {
                        Toast.makeText(this, "Failed to update order", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Order taken for delivery", Toast.LENGTH_SHORT).show();
                        // remove from list because it's no longer PREPARING
                        orders.remove(order);
                        adapter.notifyDataSetChanged();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                handler.post(() ->
                        Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void updateOrderStatus(FoodOrder order, String newStatus, int positionInList) {

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Constants.UPDATE_ORDER_STATUS
                        + order.getId()
                        + "/status?status=" + newStatus;

                String response = RestOperations.sendPut(url, "");

                handler.post(() -> {
                    if (!"Error".equals(response)) {
                        // update local object
                        // if you use enum in Android:
                        // order.setOrderStatus(OrderStatus.valueOf(newStatus));
                        order.setOrderStatus(OrderStatus.valueOf(newStatus));

                        // remove from list so it disappears (because driver only sees PREPARING)
                        orders.remove(positionInList);
                        adapter.notifyDataSetChanged();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
