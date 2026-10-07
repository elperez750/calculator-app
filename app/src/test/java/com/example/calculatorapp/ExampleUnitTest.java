package com.example.calculatorapp;

import static org.junit.Assert.assertEquals;

import com.google.firebase.firestore.util.Assert;

import org.junit.Test;

public class ExampleUnitTest {

    // RUNG 1: numbers
    @Test public void number()      { assertEquals("42",   ExpressionEvaluator.evaluate("42")); }
    @Test public void decimal()     { assertEquals("3.02", ExpressionEvaluator.evaluate("3.02")); }

    // RUNG 2: + and -
    @Test public void add()         { assertEquals("5",  ExpressionEvaluator.evaluate("2+3")); }
    @Test public void chainedMinus(){ assertEquals("3",  ExpressionEvaluator.evaluate("10-4-3")); }

    // RUNG 3: * and /
    @Test public void precedence()  { assertEquals("14", ExpressionEvaluator.evaluate("2+3*4")); }
    @Test public void twoChunks()   { assertEquals("26", ExpressionEvaluator.evaluate("2*3+4*5")); }
    @Test public void divide()      { assertEquals("2.5",ExpressionEvaluator.evaluate("10/4")); }

    // RUNG 4: brackets
    @Test public void bracket()     { assertEquals("20", ExpressionEvaluator.evaluate("(2+3)*4")); }
    @Test public void nested()      { assertEquals("12", ExpressionEvaluator.evaluate("2*(3+(4-1))")); }
    @Test public void assignment()  { assertEquals("5.025", ExpressionEvaluator.evaluate("(1+3.02)*5/4")); }

    // RUNG 5: errors
    @Test(expected = ArithmeticException.class)
    public void divideByZero()      { ExpressionEvaluator.evaluate("5/0"); }

    @Test public void oneThird() { assertEquals("0.3333333333333333", ExpressionEvaluator.evaluate("1/3")); }

    @Test(expected = IllegalArgumentException.class)
    public void missingBracket()    { ExpressionEvaluator.evaluate("(2+3"); }
}