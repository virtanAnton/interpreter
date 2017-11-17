package jp.teamdecode.symbol;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ScopedSymbolTable {
    private final Map<String, Symbol> table;
    private final int scopeLvl;
    private final String scopeName;
    private final ScopedSymbolTable enclosingScope;

    public ScopedSymbolTable(int scopeLvl, String scopeName) {
        this(scopeLvl, scopeName, null);
    }

    public ScopedSymbolTable(int scopeLvl, String scopeName, ScopedSymbolTable enclosingScope) {
        this.table = new HashMap<>();
        this.scopeLvl = scopeLvl;
        this.scopeName = scopeName;
        this.enclosingScope = enclosingScope;
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
        String header = "       ScopedSymbolTable \n" +
                "========================\n";
        StringBuilder sb = new StringBuilder(header);


        for (Map.Entry<String, Symbol> entry : table.entrySet()) {
            sb.append(String.format("%1$7s : %2$s", entry.getKey(), entry.getValue()));
            sb.append('\n');
        }

        return sb.toString();
    }
}
