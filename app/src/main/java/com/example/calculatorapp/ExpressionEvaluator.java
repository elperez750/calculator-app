package com.example.calculatorapp;

import java.math.BigDecimal;
import java.math.MathContext;



/**
 * Evaluates arithmetic expressions such as "(1+3.02)*5/4" using recursive
 * descent parsing. Each method handles one level of operator precedence:
 * {@link #parseExpression()} handles + and -, {@link #parseTerm()} handles
 * * and /, and {@link #parseFactor()} handles numbers and brackets.
 * BigDecimal is used so decimal results are exact.
 */
public class ExpressionEvaluator {
        private final String input;
        private int pos = 0;



    /**
     * Creates a parser for the given expression.
     *
     * @param input the expression text to parse
     */
    private ExpressionEvaluator(String input) {
            this.input = input;       // store the string in the field
    }

    /**
     * Evaluates an expression and returns the result as plain text.
     *
     * @param expression the expression to evaluate, such as "2+3*4"
     * @return the result without trailing zeros, such as "14"
     * @throws ArithmeticException      if the expression divides by zero
     * @throws IllegalArgumentException if the expression is malformed
     */
    public static String evaluate(String expression) {
        ExpressionEvaluator parser = new ExpressionEvaluator(expression);  // build the parser
        BigDecimal result = parser.parseExpression();                      // start at the top
        if (parser.pos != parser.input.length()) {
            throw new IllegalArgumentException("Unexpected character");    // leftover text = bad input
        }
        return result.stripTrailingZeros().toPlainString();                // number → String
    }


    /**
     * Parses addition and subtraction, the lowest precedence level.
     * Gets values from {@link #parseTerm()} and combines them left to right.
     *
     * @return the value of the parsed expression
     */
    public BigDecimal parseExpression(){
            BigDecimal value = parseTerm();

            while(pos < input.length()) {
                char c = input.charAt(pos);
                if (c == '+') {
                    pos++;
                    value = value.add(parseTerm());
                }
                else if (c == '-'){
                    pos++;
                    value = value.subtract(parseTerm());
                }
                else {
                    break;
                }

            }
            return value;
    }



    /**
     * Parses multiplication and division. Gets values from
     * {@link #parseFactor()} and combines them left to right.
     *
     * @return the value of the parsed term
     * @throws ArithmeticException if dividing by zero
     */
    public BigDecimal parseTerm() {
        BigDecimal value = parseFactor();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c == '*') {
                pos++;
                value = value.multiply(parseFactor());
            }
            else if (c == '/') {
                pos++;
                value = value.divide(parseFactor(), MathContext.DECIMAL64);
            }
            else {
                break;
            }
        }
        return value;
    }


    /**
     * Parses a single value: either a number, or a bracketed expression.
     * A bracket restarts parsing with {@link #parseExpression()} and
     * expects a closing bracket.
     *
     * @return the value of the number or bracketed expression
     * @throws IllegalArgumentException if a number is missing or a bracket is not closed
     */
    public BigDecimal parseFactor() {
        int start = pos;

        if (pos < input.length() && input.charAt(pos) == '(') {
            pos++;                                   // skip the '('
            BigDecimal value = parseExpression();

            pos++;                                   // skip the ')'
            return value;
        }

        while (pos < input.length()
                && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
            pos++;
        }
        return new BigDecimal(input.substring(start, pos));
    }


}
