package jp.teamdecode.symbol;

import org.junit.Test;

public class ScopedSymbolTableTest {

    @Test
    public void toStringTest() throws Exception {
        ScopedSymbolTable table = new ScopedSymbolTable(scopeLvl, scopeName, enclosingScope);
        table.define(new VarSymbol("a", table.lookup("INTEGER")));
        table.define(new VarSymbol("b", table.lookup("INTEGER")));
        table.define(new VarSymbol("c", table.lookup("REAL")));
        System.out.println(table);
    }

}