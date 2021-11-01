package ru.itmo.karlina.lab6.token;

import ru.itmo.karlina.lab6.visitor.TokenVisitor;

import java.util.function.BiFunction;

public abstract class Operation implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public abstract int proceedOperation(int a, int b);

    public static final Operation PLUS = newOperation("PLUS", Integer::sum);
    public static final Operation MINUS = newOperation("MINUS", (a, b) -> a - b);
    public static final Operation MULTIPLY = newOperation("MULTIPLY", (a, b) -> a * b);
    public static final Operation DIVIDE = newOperation("DIVIDE", (a, b) -> a / b);

    private static Operation newOperation(String str, BiFunction<Integer, Integer, Integer> function) {
        return new Operation() {
            @Override
            public int proceedOperation(int a, int b) {
                return function.apply(a, b);
            }

            @Override
            public String toString() {
                return str;
            }
        };
    }
}
