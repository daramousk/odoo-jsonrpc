package fields;

class BaseField {

    private String name;
    private String type;
    private String string;
    private int size;
    private boolean required;
    private boolean readonly;
    private String help;
    private String states;
    private Object value;

    public BaseField(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        this.name = name;
        this.type = type;
        this.string = string;
        this.size = size;
        this.required = required;
        this.readonly = readonly;
        this.help = help;
        this.states = states;
    }

    public abstract void setValue(Object value) {
        this.value = value; // TODO mark it as dirty
    }

    public Object getValue() { // TODO make these abstract, make this class unusable
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    // TODO what does store do? do we need it?
}