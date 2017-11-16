package jp.teamdecode.symbol;

public class BuiltinTypeSymbol extends Symbol {

    public BuiltinTypeSymbol(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return  name ;
    }
}
