package com.example.calculatorapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;



/**
 * Main screen of the calculator app.
 * <p>
 * Every key press appends to an expression string, which is evaluated by
 * {@link ExpressionEvaluator} when "=" is pressed. On launch, the display
 * shows the developer's last name and CWU ID until the first key is pressed.
 */
public class MainActivity extends AppCompatActivity {


    /** The expression the user has typed so far. Persists between key presses. */
    private StringBuilder expression = new StringBuilder();

    /** Large display line showing the current input, result, or error. */

    private TextView tvResult;

    /** Small display line above the result. Shows the CWU ID at startup. */

    private TextView tvExpression;


    /** True until the first key press, while the name and CWU ID are visible. */

    private boolean showingIntro = true;

    /** True when the last "=" failed, so the display shows "Error". */

    private boolean showingError = false;




    /**
     * Sets up the layout, connects the display views, and attaches one
     * click listener to every calculator key.
     *
     * @param savedInstanceState previously saved state, or null on first launch
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "onCreate called");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        tvExpression = findViewById(R.id.tvExpression);


        int[] ids = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnDot, R.id.btnPlus, R.id.btnMinus, R.id.btnTimes, R.id.btnDiv,
                R.id.btnOpen, R.id.btnClose, R.id.btnEqual, R.id.btnAc, R.id.btnC
        };

        for (int id : ids) {
            findViewById(id).setOnClickListener(v -> {
                String key = ((Button) v).getText().toString();
                onKeyPressed(key);
                updateDisplay();
            });
        }



            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }



    /**
     * Updates the expression based on the pressed key.
     * <ul>
     *   <li>"AC" clears the whole expression.</li>
     *   <li>"C" removes the last character.</li>
     *   <li>"=" evaluates the expression and replaces it with the result,
     *       or flags an error if the expression is invalid.</li>
     *   <li>Any other key is appended to the expression.</li>
     * </ul>
     * The first key press also hides the startup CWU ID text, and any
     * previous error is cleared.
     *
     * @param key the text on the pressed key, such as "7", "+", or "AC"
     */
    private void onKeyPressed(String key) {
        showingError = false;
        if (showingIntro) {
            showingIntro = false;
            tvExpression.setText("");   // what text makes it "disappear"?
        }
        if (key.equals("AC")) {

            expression.setLength(0);
        } else if (key.equals("C")) {
            if (expression.length() > 0) {

                expression.setLength(expression.length()-1);
            }
        } else if (key.equals("=")) {
            // leave this empty for now, we'll do it
            try{
                String result = ExpressionEvaluator.evaluate(expression.toString());
                expression.setLength(0);
                expression.append(result);

            }
            catch(ArithmeticException | IllegalArgumentException e) {
                showingError = true;
                expression.setLength(0);
            }

        } else {
            expression.append(key);
        }


    }




    /**
     * Makes the large display match the current state: "Error" after a
     * failed calculation, "0" when the expression is empty, or the
     * expression text otherwise.
     */
    private void updateDisplay() {
            if (showingError) {
                tvResult.setText("Error in the statement"); return;
            }
            if (expression.length() == 0) {
                tvResult.setText("0");
            } else {
                tvResult.setText(expression.toString());
            }
        }





}