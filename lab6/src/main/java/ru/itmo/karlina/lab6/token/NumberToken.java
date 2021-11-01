package ru.itmo.karlina.lab6.token;

import ru.itmo.karlina.lab6.visitor.TokenVisitor;

public class NumberToken implements Token {
    public NumberToken(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    private final int number;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NUMBER(" + number + ')';
    }
}
