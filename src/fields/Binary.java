package fields;

public class Binary extends BaseField {

    private String value;

    public Binary(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        super(name, type, string, size, required, readonly, help, states);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}