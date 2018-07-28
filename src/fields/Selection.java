package fields;

public class Selection extends BaseField {

    public Selection(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        super(name, type, string, size, required, readonly, help, states);
    }

    public void setValue(String value) {

    }

    public String getValue() {
        // TODO value check here. Enum?
    }
}