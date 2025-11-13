package aq.calculator;

public class Main {
    public static void main(String[] args) {
        CalculatorModel calculatorModel = new CalculatorModel();
        CalculatorController calculatorController = new CalculatorController(calculatorModel);
        calculatorController.launchCalculator();
    }
}
