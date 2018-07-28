package main;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.WeakHashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import main.Models.Model;

public class Environment<T> {

    private Odoo odoo;
    private String database;
    private int uid;
    private JsonObject context;
    private HashMap<String, Model> registry;
    private Set<Model> dirty;

    public Environment(Odoo odoo, String database, int uid, JsonObject context) {
        this.odoo = odoo;
        this.database = database;
        this.uid = uid;
        this.context = context;
        this.dirty = Collections.newSetFromMap(new WeakHashMap<Model, Boolean>());
    }

    public JsonObject getContext() {
        return this.context;
    }

    @Override
    public String toString() {
        return String.format("Environment(db=%s, uid=%s, context=%s)", this.database, this.uid,
                this.context.toString());
    }

    public String getDatabase() {
        return this.database;
    }

    public void commit() {

    }

    public void invalidate() {
        this.dirty.clear();
    }

    public String getLang() {
        return this.context.getString("lang");
    }

    public Model ref(String xmlId) {
        JsonArray params = Json.createArrayBuilder().add(xmlId).build();
        JsonArray result = this.odoo.execute("ir.model.data", "xmlid_to_res_model_res_id", params);
        return null;
    }

    public int getUid() {
        return this.uid;
    }

    public Models getUser() {
        return null;// TODO fix
    }

    public HashMap<String, Model> getRegistry() {
        return this.registry;
    }

    public Model get(String modelName) {
        if (this.registry.containsKey(modelName) == false) {
            this.registry.put(modelName, this.create_model_class(modelName));
        }
        return this.registry.get(modelName);
    }

    private Model create_model_class(String modelName) {
        String klassName = modelName.replace(".", "_");
        JsonArray result = this.odoo.execute(modelName, "fields_get", null);
// TODO continue
    }
    // TODO implement __call__, __contains__

}
