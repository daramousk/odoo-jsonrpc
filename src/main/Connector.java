package main;

import java.net.MalformedURLException;

public class Connector {

    private String host;
    private int port;
    private int timeout;
    private float version;
    protected ProxyJson proxyJson;

    public Connector(String host, int port, int timeout, float version) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.version = version;
    }

    float getVersion() {
        return this.version;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public ProxyJson getProxyJson() throws MalformedURLException {
        return new ProxyJson(host, port, timeout, this.isSSLEnabled());
    }

    public boolean isSSLEnabled() {
        return false;
    }

    class ConnectorJSONRPC extends Connector {

        public ConnectorJSONRPC(String host, int port, int timeout, float version, boolean deserialize)
                throws MalformedURLException {
            super(host, port, timeout, version);
            this.proxyJson = this.getProxyJson();
        }

        public int getTimeout() {
            return this.proxyJson.getTimeout();
        }

        public void setTimeout(int timeout) {
            this.proxyJson.setTimeout(timeout);
        }
    }

    class ConnectorJSONRPCSSL extends ConnectorJSONRPC {

        public ConnectorJSONRPCSSL(String host, int port, int timeout, float version, boolean deserialize)
                throws MalformedURLException {
            super(host, port, timeout, version, deserialize);
            this.proxyJson = getProxyJson();
        }

        public boolean isSSLEnabled() {
            return true;
        }
    }

}
