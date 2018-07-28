package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Models {

    private static final List<String> FIELDS_RESERVED = Arrays.asList("id", "ids", "__odoo__", "__osv__", "__data__",
            "env");

    private List<Integer> normalize_ids(Object ids) {
        return Arrays.asList();
    }

    private class IncrementalRecords {

    }

    private class MetaModel {

    }

    class Model { // TODO extends BaseModel

        public Model() {
        }

        public int getId() {
            return 0;
        }

        public List<Integer> getIds() {
            return Arrays.asList(0);
        }

        private Object browse(Object klass, Environment env, List<Integer> ids, Set<Object> from_record,
                Set<Object> iterated) {
            return new Object();
        }

        public Object browse(Object klass, List<Integer> ids) {
            return new Object();
        }

        public Object with_context(Object... args) {
            return new Object();
        }

        public Object with_env(Environment env) {
            return new Object();
        }

        private void init_values(HashMap<String, String> context) {

        }

        // TODO implement if necessary, __getattr__, __getitem__, __int__, __eq__,
        // __ne__, __repr__, __iter__, __nonzero__, __len__, __iadd__, __issub__
    }

}
