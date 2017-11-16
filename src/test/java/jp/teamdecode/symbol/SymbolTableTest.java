package jp.teamdecode.symbol;

import org.junit.Test;

public class SymbolTableTest {

    @Test
    public void toStringTest() throws Exception {
        SymbolTable table = new SymbolTable();
        table.define(new VarSymbol("a", table.lookup("INTEGER")));
        table.define(new VarSymbol("b", table.lookup("INTEGER")));
        table.define(new VarSymbol("c", table.lookup("REAL")));
        System.out.println(table);
    }

}