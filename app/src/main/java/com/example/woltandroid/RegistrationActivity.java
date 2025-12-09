package com.example.woltandroid;

import static com.example.woltandroid.utils.Constants.CREATE_BASIC_USER_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.woltandroid.model.BasicUser;
import com.example.woltandroid.model.Driver;
import com.example.woltandroid.model.VehicleType;
import com.example.woltandroid.utils.Constants;
import com.example.woltandroid.utils.RestOperations;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CheckBox isDriverCheck = findViewById(R.id.regIsDriver);
        View rowBDate = findViewById(R.id.rowBDate);
        View rowVehicleType = findViewById(R.id.rowVehicleType);
        Spinner vehicleSpinner = findViewById(R.id.spinnerVehicleType);

        // Fill spinner with enum values
        ArrayAdapter<VehicleType> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                VehicleType.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(adapter);

        // Listener for checkbox
        isDriverCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int visibility = isChecked ? View.VISIBLE : View.GONE;
            rowBDate.setVisibility(visibility);
            rowVehicleType.setVisibility(visibility);
        });
    }

    public void createAccount(View view) {

        TextView login = findViewById(R.id.regLoginField);
        TextView password = findViewById(R.id.regPassField);
        TextView name = findViewById(R.id.regNameField);
        TextView surname = findViewById(R.id.regSurNameField);
        TextView phone = findViewById(R.id.regPhoneField);
        TextView address = findViewById(R.id.regAddressField);

        CheckBox isDriverCheck = findViewById(R.id.regIsDriver);
        EditText bDateField = findViewById(R.id.regBDateField);
        Spinner vehicleSpinner = findViewById(R.id.spinnerVehicleType);



        String userInfo = "{}";
        Gson gson = new Gson();
        String url;

        if (isDriverCheck.isChecked()){
            LocalDate bDate = LocalDate.parse(bDateField.getText().toString());
            // if you need a custom format, use DateTimeFormatter

            VehicleType selectedVehicle =
                    (VehicleType) vehicleSpinner.getSelectedItem();

            Driver driver = new Driver(
                    login.getText().toString(),
                    password.getText().toString(),
                    name.getText().toString(),
                    surname.getText().toString(),
                    phone.getText().toString(),
                    bDate,
                    selectedVehicle,
                    address.getText().toString()
            );

            userInfo = gson.toJson(driver, Driver.class);
            url = Constants.CREATE_DRIVER_URL;
        }else {
            BasicUser basicUser = new BasicUser(login.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString(), phone.getText().toString(), address.getText().toString());
            //Gson gson = new Gson();
            userInfo = gson.toJson(basicUser, BasicUser.class);
            url = CREATE_BASIC_USER_URL;
        }

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        String finalUserInfo = userInfo;
        executor.execute(() -> {
            try {
                String response = RestOperations.sendPost(url, finalUserInfo);
                handler.post(() -> {
                    if (!response.equals("Error") && !response.isEmpty()) {
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (IOException e) {
                //Toast reikes
            }

        });


    }
}