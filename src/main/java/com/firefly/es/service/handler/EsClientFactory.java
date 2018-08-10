package com.firefly.es.service.handler;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
public class EsClientFactory {

    private static TransportClient client;

    private static String clusterName = "es";

    @Value("${es.service.ip}")
    private static String clusterIP;

    static {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .build();

        try {
            client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(clusterIP), 9300));


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }


    /**
     * 初始化失败
     *
     * @return
     */
    public static TransportClient getClient() {

        if (null == client) {
            if (createTransportClient()) {
                return client;
            }else {

                try {
                    throw new Exception("es transport client init Exception");
                } catch (Exception e) {
                    System.exit(0);
                }

            }
        }
        return client;
    }

    /**
     * create transport  client
     *
     * @return
     */
    private static synchronized boolean createTransportClient() {

        if (null == client) {
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)
                    .build();

            try {
                client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(clusterIP), 9300));
                return true;

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

}
