package calculator.lexicalParser;

import calculator.operator.Separator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CustomSeparatorParserTest {
    @ParameterizedTest
    @DisplayName("//로 시작하고 \\n으로 끝나는 문자열 안에 커스텀 구분자가 있는지 확인한다")
    @MethodSource("generateCustomSeparatorStringData")
    void testCanParse(String inputString, boolean expected) {
        CustomSeparatorParser customSeparatorParser = CustomSeparatorParser.getInstance();
        boolean actual = customSeparatorParser.canParse(inputString);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> generateCustomSeparatorStringData() {
        return Stream.of(
                Arguments.of("//d\\n", true),
                Arguments.of("//\\n", false),
                Arguments.of("/\\n", false),
                Arguments.of("d\\n", false),
                Arguments.of("//d", false),
                Arguments.of("//customSeparator\\n", true),
                Arguments.of("// \\n", false),
                Arguments.of("a//d\\n", false)
        );
    }

    @ParameterizedTest
    @DisplayName("//와 \\n 사이의 커스텀 구분자를 반환하는지 확인")
    @MethodSource("generateValidCustomSeparatorData")
    void testParse(String inputString, Separator expectedCustomSeparator) {
        CustomSeparatorParser customSeparatorParser = CustomSeparatorParser.getInstance();

        Separator actualSeparator = customSeparatorParser.parse(inputString);

        assertThat(actualSeparator).isEqualTo(expectedCustomSeparator);
    }

    static Stream<Arguments> generateValidCustomSeparatorData() {
        return Stream.of(
                Arguments.of("//d\\n", Separator.of("d")),
                Arguments.of("//customSeparator\\n", Separator.of("customSeparator"))
        );
    }

    @ParameterizedTest
    @DisplayName("//로시작하고 \n으로 끝나는 문자열 제거하는지 확인")
    @MethodSource("generateCustomSeparatorDeclarationExpression")
    void testRemoveCustomSeparator(String inputString, String expected) {
        CustomSeparatorParser customSeparatorParser = CustomSeparatorParser.getInstance();

        String actual = customSeparatorParser.removeCustomSeparatorDeclaration(inputString);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> generateCustomSeparatorDeclarationExpression() {
        return Stream.of(
                Arguments.of("//d\\n", ""),
                Arguments.of("//customSeparator\\n1:2", "1:2")
        );
    }
}