package jp.teamdecode.symbol;

public abstract class Symbol {
    protected final String name;
    protected final Symbol type;

    public Symbol(String name) {
        this(name, null);
    }

    public Symbol(String name, Symbol type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "<" + name + ":" + type + ">";
    }
}
