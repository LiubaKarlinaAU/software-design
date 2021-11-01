package ru.itmo.karlina.lab6.visitor;

import ru.itmo.karlina.lab6.token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> result;
    private final Stack<Token> stack;

    public ParserVisitor() {
        result = new ArrayList<>();
        stack = new Stack<>();
    }

    @Override
    public void visit(NumberToken token) {
        result.add(token);
    }

    @Override
    public void visit(Operation token) {
        while (!stack.isEmpty()) {
            Token peek = stack.peek();
            if (peek.equals(Operation.MULTIPLY) || peek.equals(Operation.DIVIDE)) {
                result.add(stack.pop());
            } else {
                break;
            }
        }
        stack.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token.equals(Brace.LEFT)) {
            stack.add(token);
            return;
        }
        // Right Brace
        while (!stack.isEmpty() && !stack.peek().equals(Brace.LEFT)) {
            result.add(stack.pop());
        }
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("Wrong expression");
        }
        stack.pop();
    }

    public List<Token> visit(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
}
