package com.example.budiass;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etPrice, etUsage;
    private RadioGroup rgPetrolType;
    private SwitchMaterial switchBudi;
    private Button btnCalculate;
    private MaterialCardView cardResult;
    private TextView tvTotalCost, tvRebate, tvPayable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Binding Views
        etPrice = findViewById(R.id.etPrice);
        etUsage = findViewById(R.id.etUsage);
        rgPetrolType = findViewById(R.id.rgPetrolType);
        switchBudi = findViewById(R.id.switchBudi);
        btnCalculate = findViewById(R.id.btnCalculate);
        cardResult = findViewById(R.id.cardResult);
        tvTotalCost = findViewById(R.id.tvTotalCost);
        tvRebate = findViewById(R.id.tvRebate);
        tvPayable = findViewById(R.id.tvPayable);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCost();
            }
        });
    }

    private void calculateCost() {
        String priceStr = etPrice.getText().toString();
        String usageStr = etUsage.getText().toString();

        if (priceStr.isEmpty() || usageStr.isEmpty()) {
            Toast.makeText(this, "Sila masukkan harga dan penggunaan", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        double usage = Double.parseDouble(usageStr);

        // Langkah 1: Total petrol cost = fuel usage x petrol price
        double totalCost = usage * price;
        double rebate = 0.0;

        // Langkah 2: BUDI rebate = fuel usage x subsidy rate (RM1.99/l) *for RON95 only
        int selectedPetrol = rgPetrolType.getCheckedRadioButtonId();
        boolean isRon95 = (selectedPetrol == R.id.rbRon95);
        boolean isEligible = switchBudi.isChecked();

        if (isRon95 && isEligible) {
            rebate = usage * 1.99;
        }

        // Langkah 3: Total saving / Final Payable
        double finalPayable = totalCost - rebate;

        // Elakkan nilai negatif jika rebat melebihi kos
        if (finalPayable < 0) finalPayable = 0;

        // Tunjuk Result Card dan set text
        cardResult.setVisibility(View.VISIBLE);
        tvTotalCost.setText(String.format("RM %.2f", totalCost));
        tvRebate.setText(String.format("- RM %.2f", rebate));
        tvPayable.setText(String.format("RM %.2f", finalPayable));
    }

    // Setup Navigation Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_home) {
            // Already on home
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}