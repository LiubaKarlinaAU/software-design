package ru.itmo.karlina.lab6;

import ru.itmo.karlina.lab6.token.Token;
import ru.itmo.karlina.lab6.visitor.CalcVisitor;
import ru.itmo.karlina.lab6.visitor.ParserVisitor;
import ru.itmo.karlina.lab6.visitor.PrintVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "3 + 4 * 2 / (1 - 5)";
        System.out.println("Your input: " + input);

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.parse();

        System.out.println("Tokenizer work result: ");
        PrintVisitor printVisitor = new PrintVisitor(System.out);
        printVisitor.visit(tokens);

        ParserVisitor parserVisitor = new ParserVisitor();
        tokens = parserVisitor.visit(tokens);

        System.out.println("\nParser work result: ");
        printVisitor = new PrintVisitor(System.out);
        printVisitor.visit(tokens);

        CalcVisitor calcVisitor = new CalcVisitor();
        int expressionResult = calcVisitor.visit(tokens);
        System.out.println("\nExpression result = " + expressionResult);
    }
}
