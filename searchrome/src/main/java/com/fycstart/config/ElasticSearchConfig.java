package com.fycstart.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by 瓦力.
 */
@Configuration
public class ElasticSearchConfig {
    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.cluster.name}")
    private String esName;

    @Bean
    public RestHighLevelClient esClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", this.esName)
                .put("client.transport.sniff", true)
                .build();

        InetAddress address = InetAddress.getByName(esHost);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(address, esPort);

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(inetSocketAddress.getHostName(), 9200, "http")));
        //new HttpHost("localhost", 9201, "http")));
        return client;
    }
}
