package com.example.appmobquiz01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editTextCustomerName, editTextProductName, editTextQuantity;
    RadioGroup radioGroupMember;
    RadioButton radioButtonGold, radioButtonSilver, radioButtonRegular;
    Button buttonProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextProductName = findViewById(R.id.TextProductName);
        editTextQuantity = findViewById(R.id.TextQuantity);
        radioGroupMember = findViewById(R.id.radioGroupMember);
        radioButtonGold = findViewById(R.id.radioButtonGold);
        radioButtonSilver = findViewById(R.id.radioButtonSilver);
        radioButtonRegular = findViewById(R.id.radioButtonRegular);
        buttonProcess = findViewById(R.id.buttonProcess);

        buttonProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil nilai inputan dari pengguna
                String customerName = editTextCustomerName.getText().toString();
                String productName = editTextProductName.getText().toString();
                int quantity = Integer.parseInt(editTextQuantity.getText().toString());
                String memberType = getMemberType();

                // Intent untuk pindah ke halaman detail transaksi
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("customerName", customerName);
                intent.putExtra("productName", productName);
                intent.putExtra("quantity", quantity);
                intent.putExtra("memberType", memberType);
                startActivity(intent);
            }
        });
    }

    // Method untuk mendapatkan tipe member yang dipilih
    private String getMemberType() {
        int selectedId = radioGroupMember.getCheckedRadioButtonId();
        if (selectedId == radioButtonGold.getId()) {
            return "Gold";
        } else if (selectedId == radioButtonSilver.getId()) {
            return "Silver";
        } else {
            return "Biasa";
        }
    }
}
