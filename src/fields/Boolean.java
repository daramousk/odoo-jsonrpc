package fields;

public class Boolean extends BaseField {

    private boolean value;

    public Boolean(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        super(name, type, string, size, required, readonly, help, states);
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue(boolean value) {
        return this.value;
    }

}