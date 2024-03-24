package com.example.appmobquiz01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    TextView textViewWelcome, textViewMemberType,
            textViewProductCode, textViewProductName, textViewProductPrice,
            textViewTotalPrice, textViewDiscountPrice, textViewDiscountMember,
            textViewTotalPay;
    Button buttonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewMemberType = findViewById(R.id.textViewMemberType);
        textViewProductCode = findViewById(R.id.textViewProductCode);
        textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductPrice = findViewById(R.id.textViewProductPrice);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        textViewDiscountPrice = findViewById(R.id.textViewDiscountPrice);
        textViewDiscountMember = findViewById(R.id.textViewDiscountMember);
        textViewTotalPay = findViewById(R.id.textViewTotalPay);
        buttonShare = findViewById(R.id.buttonShare);

        // Ambil data transaksi dari intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String customerName = extras.getString("customerName");
            String memberType = extras.getString("memberType");
            String productName = extras.getString("productName");

            // Periksa apakah data yang diperlukan ada
            if (customerName != null && memberType != null && productName != null) {
                // Tampilkan data transaksi pada TextView
                textViewWelcome.setText(getString(R.string.welcome, customerName));
                textViewMemberType.setText(getString(R.string.member_type, memberType));
                textViewProductCode.setText(getString(R.string.item_code, productName));
                textViewProductName.setText(getString(R.string.product_name, getProductName(productName)));

                // Format harga dengan rupiah
                int productPrice = getProductPrice(productName);
                String formattedPrice = formatToRupiah(productPrice);
                textViewProductPrice.setText(getString(R.string.price, formattedPrice));

                // Hitung total harga dan format dengan rupiah
                int quantity = extras.getInt("quantity");
                int totalPrice = productPrice * quantity;
                String formattedTotalPrice = formatToRupiah(totalPrice);
                textViewTotalPrice.setText(getString(R.string.total_price, formattedTotalPrice));

                // Hitung diskon harga dan format dengan rupiah
                int discountPrice = calculateDiscountPrice(totalPrice);
                String formattedDiscountPrice = formatToRupiah(discountPrice);
                textViewDiscountPrice.setText(getString(R.string.price_discount, formattedDiscountPrice));

                // Hitung diskon member dan format dengan rupiah
                int discountMember = calculateDiscountMember(totalPrice, memberType);
                String formattedDiscountMember = formatToRupiah(discountMember);
                textViewDiscountMember.setText(getString(R.string.member_discount, formattedDiscountMember));

                // Hitung total bayar dan format dengan rupiah
                int totalPay = totalPrice - discountPrice - discountMember;
                String formattedTotalPay = formatToRupiah(totalPay);
                textViewTotalPay.setText(getString(R.string.total_payment, formattedTotalPay));
            }
        }

        // Set onClickListener untuk tombol Share
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTransaction();
            }
        });
    }

    // Method untuk mendapatkan nama barang berdasarkan kode
    private String getProductName(String productCode) {
        // Implementasi pengambilan nama barang berdasarkan kode
        switch (productCode) {
            case "SGS":
                return "Samsung Galaxy S";
            case "IPX":
                return "Iphone X";
            case "AAE":
                return "Acer Aspire 5";
            default:
                return "";
        }
    }

    // Method untuk mendapatkan harga barang berdasarkan kode
    private int getProductPrice(String productCode) {
        // Implementasi pengambilan harga barang berdasarkan kode
        switch (productCode) {
            case "SGS":
                return 12999999;
            case "IPX":
                return 5725300;
            case "AAE":
                return 9999999;
            default:
                return 0;
        }
    }

    // Method untuk menghitung diskon harga
    private int calculateDiscountPrice(int totalPrice) {
        // Implementasi perhitungan diskon harga
        return (totalPrice >= 10000000) ? 100000 : 0;
    }

    // Method untuk menghitung diskon member
    private int calculateDiscountMember(int totalPrice, String memberType) {
        // Implementasi perhitungan diskon member berdasarkan tipe member
        switch (memberType) {
            case "Gold":
                return (int) (totalPrice * 0.10);
            case "Silver":
                return (int) (totalPrice * 0.05);
            case "Biasa":
                return (int) (totalPrice * 0.02);
            default:
                return 0;
        }
    }

    // Method untuk berbagi detail transaksi
    private void shareTransaction() {
        // Ambil data transaksi dari TextView
        String productName = textViewProductName.getText().toString();
        String totalPay = textViewTotalPay.getText().toString();

        // Buat pesan yang akan dibagikan
        String message = productName + "\n" + "Melakukan transaksi sebesar " + totalPay + " pada Najwa AppMob Store";

        // Buat intent untuk berbagi pesan
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        // Mulai aktivitas untuk berbagi
        startActivity(Intent.createChooser(shareIntent, "Bagikan Detail Transaksi"));
    }

    // Method untuk mengonversi harga menjadi format rupiah
    private String formatToRupiah(int price) {
        // Membuat instance NumberFormat untuk format rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        // Mengonversi harga menjadi format rupiah
        return formatRupiah.format(price);
    }
}
