package main;

import java.security.InvalidParameterException;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.JsonRpcClientException;

import services.Database;

public class Odoo {

    private static enum PROTOCOLS {
        JSONRPC, JSONRPCSSL
    }

    private static final String URL_LOGIN = "/web/session/authenticate";
    private static final String URL_LOGOUT = "/web/session/destroy";
    private static final String URL_JSONRPC = "/jsonrpc";
    private static final float VERSION_ELEVEN = 11.0F;

    private String host;
    private String protocol;
    private int port;
    private int timeout;
    private float version;
    private String opener;
    private Environment env;
    private String login;
    private String password;
    private Database database;
    private HashMap<String, Object> config;
    private Connector connector;

    public Odoo(String host, String protocol, int port, int timeout, float version, String opener) {
        this.host = host;
        if (protocol != PROTOCOLS.JSONRPC.toString() || protocol != PROTOCOLS.JSONRPCSSL.toString()) {
            throw new InvalidParameterException("Invalid Protocol");
        }
        this.protocol = protocol;
        this.port = port;
        this.timeout = timeout;
        this.version = version;
        this.opener = opener;
        // TODO depending on the protocol use a connector
    }

    public HashMap<String, Object> getConfig() {
        return this.config;
    }

    public float getVersion() {
        return this.connector.getVersion();
    }

    public Database getServiceDatabase() {
        return this.database;
    }

    public Environment env() {
        if (this.isUserLoggedIn()) {
            return this.env;
        }
        throw new JsonRpcClientException(0, "User is not logged in.", null);
    }

    public JsonObject json(String url, JsonObject params) throws JsonRpcClientException {
        JsonRpcClient client = new JsonRpcClient();
        JsonObject data = this.connector.proxy_json(url, params);
        if (data.containsKey("error")) {

        }
        return data;
    }

    public boolean isUserLoggedIn() {
        return this.env != null;
    }

    public void login(String database, String login, String password) throws JsonRpcClientException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("db", database);
        objectBuilder.add("login", login);
        objectBuilder.add("password", password);
        JsonObject response = this.json(URL_LOGIN, objectBuilder.build());
        int uid;
        try {
            uid = response.getInt("id");
        } catch (NullPointerException e) {
            throw new JsonRpcClientException(401, "Invalid Credentials", null);
        }
        JsonObject context = response.getJsonObject("result").getJsonObject("user_context");
        this.env = new Environment(this, database, uid, context);
        this.login = login;
        this.password = password;
    }

    public boolean logout() {
        if (this.env != null) {
            return false;
        }
        this.json(URL_LOGOUT, null);
        this.env = null;
        this.login = null;
        this.password = null;
        return true;
    }

    public JsonArray execute(String model, String method, JsonArray args) throws JsonRpcClientException {
        if (this.isUserLoggedIn() != true) {
            throw new JsonRpcClientException(0, "User is not logged in.", null);
        }
        args.add(this.env.getDatabase());
        args.add(this.env.getUid());
        args.add(this.password);
        args.add(model);
        args.add(method);
        JsonObject params = Json.createObjectBuilder().add("service", "object").add("method", "execute")
                .add("args", args).build();
        JsonObject response = this.json(URL_JSONRPC, params);
        return response.getJsonArray("result");
    }

    public JsonArray execute_kw(String model, String method, JsonArray args, JsonObject kwargs) {
        if (this.isUserLoggedIn() != true) {
            throw new JsonRpcClientException(0, "User is not logged in.", null);
        }
        args.add(this.env.getDatabase());
        args.add(this.env.getUid());
        args.add(this.password);
        args.add(model);
        args.add(method);
        args.add(kwargs);
        JsonObject params = Json.createObjectBuilder().add("service", "object").add("method", "execute_kw")
                .add("args", args).build();
        JsonObject response = this.json(URL_JSONRPC, params);
        return response.getJsonArray("result");
    }

    public JsonArray execute_workflow(String model, int record_id, String signal) {
        if (this.version > VERSION_ELEVEN) {
            throw new JsonRpcClientException(0, "Workflows have been removed for versions > 11.0", null);
        }
        if (this.isUserLoggedIn() == false) {
            throw new JsonRpcClientException(0, "User has not logged in", null);
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(this.env.getDatabase());
        arrayBuilder.add(this.env.getUid());
        arrayBuilder.add(this.password);
        arrayBuilder.add(model);
        arrayBuilder.add(signal);
        arrayBuilder.add(record_id);
        JsonObject params = Json.createObjectBuilder().add("service", "object").add("method", "exec_workflow")
                .add("args", arrayBuilder.build()).build();
        JsonObject response = this.json(URL_JSONRPC, params);
        return response.getJsonArray("result");
    }

    public void save(String name, String rcFile) {
        if (this.isUserLoggedIn() == false) {
            throw new JsonRpcClientException(0, "User has not logged in", null);
        }
        JsonObject data = Json.createObjectBuilder().add("type", this.getClass().getTypeName()).add("host", this.host)
                .add("protocol", this.protocol).add("port", this.port).add("timeout", this.timeout)
                .add("user", this.login).add("passwd", this.password).add("database", this.env.getDatabase()).build();
        Session.save(name, data, rcFile);
    }

    public Odoo load(Object klass, String name, String rcFile) {
        JsonObject data = Session.load();
        if (data.getString("type") != this.getClass().getName()) {
            throw new ClassFormatError("Session class is not of the proper type.");
        }
        Odoo odoo = new Odoo(data.getString("host"), data.getString("protocol"), data.getInt("port"),
                data.getInt("timeout"), 0.0F, null);
        odoo.login(data.getString("database"), data.getString("user"), data.getString("password"));
        return odoo;
    }

    public JsonArray list(String rcFile) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArray sessions = Session.getAll(rcFile);
        for (javax.json.JsonValue session : sessions) {
            if (session.getValueType().toString() == getClass().getName()) {
                builder.add(session);
            }
        }
        return builder.build();
    }

    public void remove(Object klass, String name, String rcFile) {
        JsonObject data = Session.get(rcFile);
        if (data.get("type").getClass() != this.getClass()) {
            throw new JsonRpcClientException(0, "Session class is not of the proper type.", null);
        }
        Session.remove(name, rcFile);
    }
}
