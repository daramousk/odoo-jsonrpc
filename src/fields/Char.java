package fields;

public class Char extends BaseField {

    public Char(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        super(name, type, string, size, required, readonly, help, states);
    }

    public void setValue(String value) {
        super.setValue(value);
    }

    public String getValue() {
        return super.toString();
    }
}