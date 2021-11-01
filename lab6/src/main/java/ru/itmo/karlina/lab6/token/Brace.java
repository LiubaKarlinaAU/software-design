package ru.itmo.karlina.lab6.token;

import ru.itmo.karlina.lab6.visitor.TokenVisitor;

public abstract class Brace implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public static final Brace LEFT = newBrace("LEFT");
    public static final Brace RIGHT = newBrace("RIGHT");

    private static Brace newBrace(String str) {
        return new Brace() {
            @Override
            public String toString() {
                return str;
            }
        };
    }
}
