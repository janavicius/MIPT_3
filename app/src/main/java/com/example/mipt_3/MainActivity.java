package com.example.mipt_3;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private String input = "";
    private double firstValue = 0;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText.setGravity(Gravity.RIGHT);
        editText.setKeyListener(null);
        editText.setFocusable(false);

        Button[] buttons = new Button[]{
                findViewById(R.id.button_0),
                findViewById(R.id.button_1),
                findViewById(R.id.button_2),
                findViewById(R.id.button_3),
                findViewById(R.id.button_4),
                findViewById(R.id.button_5),
                findViewById(R.id.button_6),
                findViewById(R.id.button_7),
                findViewById(R.id.button_8),
                findViewById(R.id.button_9),
                findViewById(R.id.button_mc),
                findViewById(R.id.button_mr),
                findViewById(R.id.button_ms),
                findViewById(R.id.button_mPlus),
                findViewById(R.id.button_mMinus),
                findViewById(R.id.button_back),
                findViewById(R.id.button_ce),
                findViewById(R.id.button_c),
                findViewById(R.id.button_pm),
                findViewById(R.id.button_root),
                findViewById(R.id.button_divide),
                findViewById(R.id.button_perc),
                findViewById(R.id.button_mult),
                findViewById(R.id.button_1outx),
                findViewById(R.id.button_minus),
                findViewById(R.id.button_plus),
                findViewById(R.id.button_comma),
                findViewById(R.id.button_equal),
        };

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            int resId = getResources().getIdentifier("button_" + i, "string", getPackageName());
            if (resId != 0) {
                String buttonText = getResources().getString(resId);
                button.setText(buttonText);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String buttonText = button.getText().toString();
                    handleButtonClick(buttonText);
                }
            });
        }
    }

    private double memoryValue = 0.0;

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "+":
            case "-":
            case "*":
            case "/":
                handleOperatorClick(buttonText);
                break;
            case "=":
                if (input.startsWith("√")) {
                    String numberStr = input.substring(1); // Išimame √ simbolį
                    double value = Double.parseDouble(numberStr);
                    if (value >= 0) {
                        value = Math.sqrt(value);
                        editText.setText(String.valueOf(value));
                        input = String.valueOf(value);
                    } else {
                        editText.setText("Negalima traukti šaknies iš neigiamo skaičiaus");
                        input = "";
                    }
                } else {
                    calculateResult();
                }
                break;
            case "1/x":
                handleInverseClick();
                break;
            case "%":
                handlePercentClick();
                break;
            case "root":
                handleSqrtClick();
                break;
            case "MC":
                memoryValue = 0.0;
                break;
            case "MR":
                editText.setText(String.valueOf(memoryValue));
                input = String.valueOf(memoryValue);
                break;
            case "MS":
                memoryValue = Double.parseDouble(input);
                break;
            case "M+":
                memoryValue += Double.parseDouble(input);
                Log.d("Calculator", "Memory Value after M+: " + memoryValue);
                break;
            case "M-":
                memoryValue -= Double.parseDouble(input);
                Log.d("Calculator", "Memory Value after M-: " + memoryValue);
                break;
            case "back":
                handleBackspace();
                break;
            case "CE":
                clearEntry();
                break;
            case "C":
                clearAll();
                break;
            case "+-":
                handlePlusMinus();
                break;
            default:
                input += buttonText.replace(",", ".");
                editText.setText(input);
                break;
        }
    }

    private void handleOperatorClick(String newOperator) {
        if (!input.isEmpty()) {
            if (!operator.isEmpty()) {
                calculateResult();
            } else {
                firstValue = Double.parseDouble(input);
            }
            operator = newOperator;
            input = "";
        }
    }

    private void calculateResult() {
        if (!input.isEmpty()) {
            double secondValue = Double.parseDouble(input);
            switch (operator) {
                case "+":
                    firstValue += secondValue;
                    break;
                case "-":
                    firstValue -= secondValue;
                    break;
                case "*":
                    firstValue *= secondValue;
                    break;
                case "/":
                    if (secondValue != 0) {
                        firstValue /= secondValue;
                    } else {
                        editText.setText("Cannot devide from 0");
                    }
                    break;
            }
            editText.setText(String.valueOf(firstValue));
            input = "";
            operator = "";
        }
    }

    private void handleBackspace() {
        String text = editText.getText().toString();
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            editText.setText(text);
            editText.setSelection(text.length());
            input = text;
        }
    }

    private void clearEntry() {
        input = "";
        editText.setText("");
    }

    private void clearAll() {
        input = "";
        firstValue = 0;
        operator = "";
        editText.setText("");
    }

    private void handleInverseClick() {
        if (!input.isEmpty()) {
            double value = Double.parseDouble(input);
            if (value != 0) {
                value = 1 / value;
                editText.setText(String.valueOf(value));
                input = String.valueOf(value);
            } else {
                // division from 0
            }
        }
    }

    private void handlePercentClick() {
        if (!input.isEmpty()) {
            double value = Double.parseDouble(input);
            value /= 100;
            editText.setText(String.valueOf(value));
            input = String.valueOf(value);
        }
    }

    private void handleSqrtClick() {
        if (!input.isEmpty()) {
            double value = Double.parseDouble(input);
            if (value >= 0) {
                value = Math.sqrt(value);
                editText.setText(String.valueOf(value));
                input = String.valueOf(value);
            } else {
                // square root cannot handle negative numbbers
            }
        }
    }

    private void handlePlusMinus() {
        if (!input.isEmpty()) {
            double value = Double.parseDouble(input);
            value = -value;
            editText.setText(String.valueOf(value));
            input = String.valueOf(value);
        }
    }
}

