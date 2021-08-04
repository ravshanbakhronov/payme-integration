package com.example.paymeintegration.config;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {
    private final Environment env;

    public RestTemplateConfig(Environment env) {
        this.env = env;
    }

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = null;
        CloseableHttpClient httpClient = null;
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            if (env.getProperty("spring.profiles.active").equalsIgnoreCase("default")) {
                httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy())
                        .setSSLSocketFactory(csf)
                        .build();
                // If locale add proxy
            } else {
                // When production is prepared, this is used
                httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy())
                        .setSSLSocketFactory(csf)
                        .build();
            }

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);

            restTemplate = new RestTemplate(requestFactory);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(env.getProperty("payme-host")));

        return restTemplate;
    }


}
