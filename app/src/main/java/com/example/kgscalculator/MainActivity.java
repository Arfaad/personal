package com.example.kgscalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout inputContainer;
    private Button addButton, calculateButton, resetButton;
    private TextView sumOfProductsView, sumOfFirstValuesView, finalResultView;
    private final ArrayList<EditText> firstValueInputs = new ArrayList<>();
    private final ArrayList<EditText> secondValueInputs = new ArrayList<>();
    private final ArrayList<TextView> productViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputContainer = findViewById(R.id.inputContainer);
        addButton = findViewById(R.id.addButton);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);
        sumOfProductsView = findViewById(R.id.sumOfProducts);
        sumOfFirstValuesView = findViewById(R.id.sumOfFirstValues);
        finalResultView = findViewById(R.id.finalResult);

        // Add initial input row
        addNewInputPair();

        addButton.setOnClickListener(v -> addNewInputPair());
        calculateButton.setOnClickListener(v -> calculateResults());
        resetButton.setOnClickListener(view -> {
            new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Reset Confirmation")
                    .setMessage("Are you sure you want to reset all fields?")
                    .setPositiveButton("Yes", (dialog, which) -> resetInputs())
                    .setNegativeButton("No", null)
                    .show();
        });

    }

    private void addNewInputPair() {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        EditText firstValue = new EditText(this);
        firstValue.setHint("కె.జి");
        firstValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        firstValue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        firstValueInputs.add(firstValue);

        EditText secondValue = new EditText(this);
        secondValue.setHint("రేటు");
        secondValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        secondValue.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        secondValueInputs.add(secondValue);

        TextView productView = new TextView(this);
        productView.setText("=");
        productView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        productViews.add(productView);

        rowLayout.addView(firstValue);
        rowLayout.addView(secondValue);
        rowLayout.addView(productView);
        inputContainer.addView(rowLayout);
    }

    private void calculateResults() {
        double sumOfProducts = 0.0;
        double sumOfFirstValues = 0.0;

        for (int i = 0; i < firstValueInputs.size(); i++) {
            try {
                double first = Double.parseDouble(firstValueInputs.get(i).getText().toString().trim());
                double second = Double.parseDouble(secondValueInputs.get(i).getText().toString().trim());

                double product = first * second;
                productViews.get(i).setText(String.format("%.2f", product));

                sumOfProducts += product;
                sumOfFirstValues += first;

            } catch (NumberFormatException e) {
                productViews.get(i).setText("Err");
                Toast.makeText(this, "Invalid input at row " + (i + 1), Toast.LENGTH_SHORT).show();
            }
        }

        sumOfProductsView.setText("రేటు: " + String.format("%.2f", sumOfProducts));
        sumOfFirstValuesView.setText("కె.జి: " + String.format("%.2f", sumOfFirstValues));

        if (sumOfFirstValues != 0) {
            double average = sumOfProducts / sumOfFirstValues;
            finalResultView.setText("Average: " + String.format("%.2f", average));
        } else {
            finalResultView.setText("Average: Error (Divide by Zero)");
        }
    }

    private void resetInputs() {
        inputContainer.removeAllViews();
        firstValueInputs.clear();
        secondValueInputs.clear();
        productViews.clear();

        sumOfProductsView.setText("రేటు: 0");
        sumOfFirstValuesView.setText("కె.జి: 0");
        finalResultView.setText("Average: 0");

        addNewInputPair();
    }
}