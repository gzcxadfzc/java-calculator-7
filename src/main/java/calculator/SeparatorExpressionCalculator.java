package calculator;

import calculator.expression.Expression;
import calculator.expression.ExpressionExecutor;
import calculator.io.Display;
import calculator.io.InputReceiver;
import calculator.lexicalParser.CustomSeparatorParser;
import calculator.lexicalParser.ExpressionParser;
import calculator.lexicalParser.ExpressionValidator;
import calculator.operator.OperatorEnum;
import calculator.operator.OperatorMap;
import calculator.operator.Separator;
import calculator.operator.Separators;

import java.util.Set;

public class SeparatorExpressionCalculator {
    private static final Separator COMMA_SEPARATOR = new Separator(",");
    private static final Separator COLON_SEPARATOR = new Separator(":");
    private final OperatorMap operatorMap = OperatorMap.getInstance();
    private final Display display;
    private final InputReceiver inputReceiver;
    private Separators separators;

    public SeparatorExpressionCalculator(InputReceiver inputReceiver, Display display) {
        this.separators = new Separators(Set.of(COMMA_SEPARATOR, COLON_SEPARATOR));
        this.display = display;
        this.inputReceiver = inputReceiver;
        this.operatorMap.registerSeparatorToOperator(COMMA_SEPARATOR, OperatorEnum.PLUS);
        this.operatorMap.registerSeparatorToOperator(COLON_SEPARATOR, OperatorEnum.PLUS);
    }

    public void operate() {
        String input = inputReceiver.readInput();
        Expression expression = parseToExpression(input);
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(operatorMap);
        int result = expressionExecutor.calculate(expression);
        display.showResult(result);
    }

    private Expression parseToExpression(String input) {
        CustomSeparatorParser manager = new CustomSeparatorParser(input);
        registerCustomSeparators(manager);
        String removeCustomSeparatorDeclaration = manager.removeCustomSeparatorDeclaration();
        ExpressionValidator validator = new ExpressionValidator(separators);
        ExpressionParser parser = new ExpressionParser(validator, separators);
        return parser.parse(removeCustomSeparatorDeclaration);
    }

    private void registerCustomSeparators(CustomSeparatorParser manager) {
        boolean hasCustomSeparator = manager.hasCustomSeparator();
        if (hasCustomSeparator) {
            Separator customSeparator = manager.getCustomSeparator();
            separators = separators.add(customSeparator);
            operatorMap.registerSeparatorToOperator(customSeparator, OperatorEnum.PLUS);
        }
    }
}
