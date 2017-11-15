package jp.teamdecode.symbol;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> table;

    public SymbolTable() {
        table = new HashMap<>();
        initBuiltins();
    }

    private void initBuiltins() {
        define(new BuildinTypeSymbol("INTEGER"));
        define(new BuildinTypeSymbol("REAL"));
    }

    public void define(Symbol symbol) {
        System.out.println("Define: " + symbol);
        table.put(symbol.name, symbol);
    }

    @Nullable
    public Symbol lookup(String name) {
        System.out.println("Lookup: " + name);
        return table.get(name);
    }

    @Override
    public String toString() {
        return "SymbolTable: "
                + table;
    }
}
