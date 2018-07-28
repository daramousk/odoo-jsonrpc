package fields;

import java.util.HashMap;

public class Fields {

    private static HashMap<String, Object> TYPES_TO_FIELDS = new HashMap<>();

    static {
        TYPES_TO_FIELDS.put("binary", Binary.class);
        TYPES_TO_FIELDS.put("boolean", Boolean.class);
        TYPES_TO_FIELDS.put("char", Char.class);
        TYPES_TO_FIELDS.put("date", Date.class);
        TYPES_TO_FIELDS.put("datetime", DateTime.class);
        TYPES_TO_FIELDS.put("float", Float.class);
        TYPES_TO_FIELDS.put("html", Html.class);
        TYPES_TO_FIELDS.put("integer", Integer.class);
        TYPES_TO_FIELDS.put("many2many", Many2Many.class);
        TYPES_TO_FIELDS.put("many2one", Many2one.class);
        TYPES_TO_FIELDS.put("one2many", One2Many.class);
        TYPES_TO_FIELDS.put("reference", Reference.class);
        TYPES_TO_FIELDS.put("selection", Selection.class);
        TYPES_TO_FIELDS.put("text", Text.class);
    }

    public BaseField generate_field(String name, HashMap<String, String> data) {
        return new BaseField();
    }

}
