package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Error;
import main.Odoo;

public class Database {

    private static final String URL_JSONRPC = "/jsonrpc";
    private static final String SERVICE_DATABASE = "db";
    private static final String METHOD_DUMP = "dump";
    private static final String METHOD_CHANGE_ADMIN_PASSWORD = "change_admin_password";
    private static final String METHOD_CREATE_DATABASE = "create_database";
    private static final String METHOD_DROP = "drop";
    private static final String METHOD_DUPLICATE_DATABASE = "duplicate_database";
    private static final String METHOD_LIST = "list";
    private static final String METHOD_RESTORE = "restore";

    private Odoo odoo;

    public static enum FORMAT_DUMP {
        ZIP, PG_DUMP
    }

    public Database(Odoo odoo) {
        this.odoo = odoo;
    }

    public JsonObject dump(String password, String database, FORMAT_DUMP format) throws Error {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder().add(password).add(database);
        if (this.odoo.getVersion() >= 9.0F) {
            arrayBuilder.add(format.toString());
        }
        return this.call(SERVICE_DATABASE, METHOD_DUMP, arrayBuilder.build());
    }

    public void change_admin_password(String oldPassword, String newPassword) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(oldPassword);
        arrayBuilder.add(newPassword);
        this.call(SERVICE_DATABASE, METHOD_CHANGE_ADMIN_PASSWORD, arrayBuilder.build());
    }

    public void create(String password, String database, boolean demo, String lang, String admin_password) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(password);
        arrayBuilder.add(database);
        arrayBuilder.add(demo);
        arrayBuilder.add(lang);
        arrayBuilder.add(admin_password);
        this.call(SERVICE_DATABASE, METHOD_CREATE_DATABASE, arrayBuilder.build());
    }

    public void drop(String password, String database) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(password);
        arrayBuilder.add(database);
        this.call(SERVICE_DATABASE, METHOD_DROP, arrayBuilder.build());
    }

    public void duplicate(String password, String oldDatabase, String newDatabase) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(password);
        arrayBuilder.add(oldDatabase);
        arrayBuilder.add(newDatabase);
        this.call(SERVICE_DATABASE, METHOD_DUPLICATE_DATABASE, arrayBuilder.build());
    }

    public JsonObject list() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        return this.call(SERVICE_DATABASE, METHOD_LIST, arrayBuilder.build());
    }

    public void restore(String password, String database, File dump, boolean copy) throws IOException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(password);
        arrayBuilder.add(database);
        arrayBuilder.add(Base64.getEncoder().encodeToString(Files.readAllBytes(dump.toPath())));
        arrayBuilder.add(copy);
        this.call(SERVICE_DATABASE, METHOD_RESTORE, arrayBuilder.build());
    }

    private JsonObject call(String service, String method, JsonArray args) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("service", service);
        objectBuilder.add("method", method);
        objectBuilder.add("args", args);
        return this.odoo.json(URL_JSONRPC, objectBuilder.build());
    }
}
