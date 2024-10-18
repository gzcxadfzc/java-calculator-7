package calculator;

import calculator.arithmeticUnit.ExpressionExecutor;

import java.util.Set;

public class SeparatorExpressionCalculator {
    private static final Separator COMMA_SEPARATOR = new Separator(",");
    private static final Separator COLON_SEPARATOR = new Separator(":");
    private final OperatorMap operatorMap = OperatorMap.getInstance();
    private final Separators separators;
    private final Display display;

    public SeparatorExpressionCalculator(Display display) {
        this.separators = new Separators(Set.of(COMMA_SEPARATOR, COLON_SEPARATOR));
        this.display = display;
        this.operatorMap.registerSeparatorToOperator(COMMA_SEPARATOR, OperatorEnum.PLUS);
        this.operatorMap.registerSeparatorToOperator(COLON_SEPARATOR, OperatorEnum.PLUS);
    }

    public void operate() {
        String input = getInputFromConsole();
        Expression expression = parseToExpression(input);
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(operatorMap);
        int result = expressionExecutor.calculate(expression);
        display.showResult(result);
    }

    private Expression parseToExpression(String input) {
        registerCustomSeparators(input);
        ExpressionValidator validator = new ExpressionValidator(separators);
        ExpressionParser parser = new ExpressionParser(validator, separators);
        return parser.parse(input);
    }

    private void registerCustomSeparators(String input) {
        CustomSeparatorManager manager = new CustomSeparatorManager(input);
        boolean hasCustomSeparator = manager.hasCustomSeparator();
        if (hasCustomSeparator) {
            Separator customSeparator = manager.getCustomSeparator();
            separators.add(customSeparator);
            operatorMap.registerSeparatorToOperator(customSeparator, OperatorEnum.PLUS);
        }
    }

    private String getInputFromConsole() {
        return CalculatorInputConsole.readLine();
    }
}
