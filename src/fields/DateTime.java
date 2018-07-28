package fields;

import java.time.LocalDateTime;

public class DateTime extends BaseField {

    public DateTime(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        super(name, type, string, size, required, readonly, help, states);
    }

    public void setValue(LocalDateTime datetime) {
        super.setValue(datetime);
    }

    public LocalDateTime getValue() {
        return super.getValue();
    }

}