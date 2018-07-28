package main;

import java.net.MalformedURLException;
import java.net.URLConnection;

import javax.json.JsonObject;

import com.googlecode.jsonrpc4j.JsonRpcClient;

public class ProxyJson extends Proxy {

    public ProxyJson(String host, int port, int timeout, boolean sslEnabled) throws MalformedURLException {
        super(host, port, timeout, sslEnabled);
    }

    public JsonObject call(JsonObject params) throws Throwable {
        JsonRpcClient client = new JsonRpcClient();
        URLConnection conn = this.getUrl().openConnection();
        conn.connect();
        return client.invokeAndReadResponse("call", params, JsonObject.class, conn.getOutputStream(),
                conn.getInputStream());
    }

}
