package fields;

import javax.swing.text.html.HTMLDocument;

public class Html extends BaseField {

    public Html(String name, String type, String string, int size, boolean required, boolean readonly, String help,
            String states) {
        super(name, type, string, size, required, readonly, help, states);
    }

    public void setValue(HTMLDocument value) {
        // TODO
    }

    public HTMLDocument getValue() {
        // TODO
    }
}