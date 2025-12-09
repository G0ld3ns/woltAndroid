package com.example.woltandroid;
import static com.example.woltandroid.utils.Constants.UPDATE_BASIC_USER_URL;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.woltandroid.model.BasicUser;
import com.example.woltandroid.utils.RestOperations;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccountUpdate extends AppCompatActivity {

    private BasicUser basicUser;
    private EditText editLogin;
    private EditText editPassword;
    private EditText editName;
    private EditText editSurname;
    private EditText editPhone;
    private EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });
        editLogin    = findViewById(R.id.changeLogin);
        editPassword = findViewById(R.id.changePass);
        editName     = findViewById(R.id.changeName);
        editSurname  = findViewById(R.id.changeSurname);
        editPhone    = findViewById(R.id.changePhone);
        editAddress  = findViewById(R.id.changeAddress);
        Button btnSave = findViewById(R.id.btnSaveAccount);

        // get user from intent
        String json = getIntent().getStringExtra("userJsonObject");
        Gson gson = new Gson();
        basicUser = gson.fromJson(json, BasicUser.class);

        // prefill fields with current values
        if (basicUser != null) {
            editLogin.setText(basicUser.getLogin());
            editPassword.setText(basicUser.getPassword());
            editName.setText(basicUser.getName());
            editSurname.setText(basicUser.getSurname());
            editPhone.setText(basicUser.getPhoneNumber());
            editAddress.setText(basicUser.getAddress());
        }

        btnSave.setOnClickListener(v -> saveChanges());
    }

    private void saveChanges() {
        if (basicUser == null) return;

        // Keep a copy of original values
        BasicUser original = basicUser;
        String newLogin    = editLogin.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();
        String newName     = editName.getText().toString().trim();
        String newSurname  = editSurname.getText().toString().trim();
        String newPhone    = editPhone.getText().toString().trim();
        String newAddress  = editAddress.getText().toString().trim();

        // Build payload: if field is blank, keep old value
        BasicUser payload = new BasicUser();
        payload.setId(original.getId());
        payload.setLogin(newLogin.isEmpty() ? original.getLogin() : newLogin);
        payload.setPassword(newPassword.isEmpty() ? original.getPassword() : newPassword);
        payload.setName(newName.isEmpty() ? original.getName() : newName);
        payload.setSurname(newSurname.isEmpty() ? original.getSurname() : newSurname);
        payload.setPhoneNumber(newPhone.isEmpty() ? original.getPhoneNumber() : newPhone);
        payload.setAddress(newAddress.isEmpty() ? original.getAddress() : newAddress);
        payload.setAdmin(original.isAdmin());
        // dateCreated will be kept in backend; dateUpdated is set in controller

        Gson gson = new Gson();
        String jsonBody = gson.toJson(payload, BasicUser.class);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = UPDATE_BASIC_USER_URL + payload.getId();
                String response = RestOperations.sendPut(url, jsonBody);

                handler.post(() -> {
                    if (!"Error".equals(response)) {
                        Toast.makeText(this, "Account updated", Toast.LENGTH_SHORT).show();
                        finish(); // go back to WoltMain
                    } else {
                        Toast.makeText(this, "Failed to update account", Toast.LENGTH_SHORT).show();
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

    public void sendBack(View view) {
        finish();
    }
}