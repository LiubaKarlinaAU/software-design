package ru.itmo.karlina.lab6.token;

import ru.itmo.karlina.lab6.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
