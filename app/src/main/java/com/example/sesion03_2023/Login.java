package com.example.sesion03_2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class Login extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton;
    HashMap<String, User> userMap;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.btnLogin);

        userMap = new HashMap<>();
        // Agregar usuarios para probar
        userMap.put("user1", new User("Jhossep", "Dareck", "user1", "123", "Female", "987-654-321", true, R.drawable.image1));
            userMap.put("user2", new User("Gerson", "Bravo", "user2", "1234", "Male", "927-351-001", true, R.drawable.image2));
        userMap.put("user3", new User("Rubi", "Figueroa", "user3", "12345", "Female", "987-134-121", true, R.drawable.image4));
        userMap.put("user4", new User("Ailin", "Leche", "user4", "123456", "Male", "937-044-313", true, R.drawable.image3));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });
    }

    private void authenticate() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (userMap.containsKey(username)) {
            User user = userMap.get(username);
            if (user.getPassword().equals(password)) {
                currentUser = user; // Asignar el usuario actual
                showSuccessMessageAndNavigate("Credenciales correctas");
            } else {
                showErrorMessage("Contraseña incorrecta");
            }
        } else {
            showErrorMessage("Usuario no encontrado");
        }
    }

    private void showErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_error, null);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnDismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView errorMessageTextView = dialogView.findViewById(R.id.errorMessageTextView);
        errorMessageTextView.setText(message);

        dialog.show();
    }

    private void showSuccessMessageAndNavigate(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_success, null);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView successMessageTextView = dialogView.findViewById(R.id.successMessageTextView);
        successMessageTextView.setText(message);

        dialog.show();

        dialogView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("user", userMap.get(usernameEditText.getText().toString()));
                startActivity(intent);
                finish();
            }
        }, 800); // Cambia el valor si deseas que la ventana emergente se muestre durante más tiempo
    }
}
