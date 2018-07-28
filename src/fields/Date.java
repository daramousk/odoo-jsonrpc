package fields;

public class Date extends BaseField {

    public Date(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        super(name, type, string, size, required, readonly, help, states);
    }

    public Date getValue() {
        return super.getValue();
    }

    public void setValue(Date date) {
        super.setValue(date);
    }

}