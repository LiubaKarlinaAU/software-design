package ru.itmo.karlina.lab6.visitor;

import ru.itmo.karlina.lab6.token.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private final PrintStream stream;

    public PrintVisitor(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void visit(NumberToken token) {
        visitToken(token);
    }

    @Override
    public void visit(Operation token) {
        visitToken(token);
    }

    @Override
    public void visit(Brace token) {
        visitToken(token);
    }

    public OutputStream visit(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
        return stream;
    }

    private void visitToken(Token token) {
        stream.print(token.toString() + " ");
    }
}
