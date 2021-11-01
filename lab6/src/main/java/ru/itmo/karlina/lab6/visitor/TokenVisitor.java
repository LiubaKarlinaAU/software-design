package ru.itmo.karlina.lab6.visitor;

import ru.itmo.karlina.lab6.token.Brace;
import ru.itmo.karlina.lab6.token.Operation;
import ru.itmo.karlina.lab6.token.NumberToken;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(Operation token);

    void visit(Brace token);
}
