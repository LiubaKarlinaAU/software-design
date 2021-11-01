package ru.itmo.karlina.lab6.visitor;

import ru.itmo.karlina.lab6.token.Brace;
import ru.itmo.karlina.lab6.token.Operation;
import ru.itmo.karlina.lab6.token.NumberToken;
import ru.itmo.karlina.lab6.token.Token;

import java.util.List;
import java.util.Stack;

import static ru.itmo.karlina.lab6.token.Operation.*;

public class CalcVisitor implements TokenVisitor {
    private final Stack<Integer> stack;

    public CalcVisitor() {
        stack = new Stack<>();
    }

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getNumber());
    }

    @Override
    public void visit(Operation token) {
        int b = getNumber();
        int a = getNumber();
        stack.add(token.proceedOperation(a, b));
    }

    private int getNumber() {
        if (stack.isEmpty()) {
            throwWrongExpression();
        }
        return stack.pop();
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalArgumentException("There should not be any braces.");
    }

    public int visit(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
        if (stack.isEmpty()) {
            return 0;
        }
        int result = stack.pop();
        if (stackIsNotEmpty()) {
            throwWrongExpression();
        }
        return result;
    }

    private boolean stackIsNotEmpty() {
        return !stack.isEmpty();
    }

    private void throwWrongExpression() {
        throw new IllegalArgumentException("Wrong expression");
    }
}
