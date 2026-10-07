package com.example.calculatorapp;

import java.math.BigDecimal;
import java.math.MathContext;

public class ExpressionEvaluator {
        private final String input;
        private int pos = 0;

        private ExpressionEvaluator(String input) {
            this.input = input;       // store the string in the field
        }


    public static String evaluate(String expression) {
        ExpressionEvaluator parser = new ExpressionEvaluator(expression);  // build the parser
        System.out.println(parser);
        BigDecimal result = parser.parseExpression();                      // start at the top
        if (parser.pos != parser.input.length()) {
            throw new IllegalArgumentException("Unexpected character");    // leftover text = bad input
        }
        return result.stripTrailingZeros().toPlainString();                // number → String
    }



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
