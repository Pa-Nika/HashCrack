package ru.nsu.panova.main.manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@ConditionalOnProperty(value = "interaction.interface", havingValue = "http", matchIfMissing = true)
public class RestTemplateConfig {

    private static final int REQUEST_TIMEOUT_MILLIS = 2 * 60 * 60 * 1000;

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        List<MediaType> types = new ArrayList<>();
        types.add(MediaType.APPLICATION_JSON);

        converter.setSupportedMediaTypes(types);
        return converter;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setWriteAcceptCharset(false);

        return stringConverter;
    }

    @Bean
    public RestTemplate restTemplate(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter, @Nullable RestTemplateLoggingInterceptor loggingInterceptor) {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(200);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(200);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(REQUEST_TIMEOUT_MILLIS))
                .build();

        CloseableHttpClient httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager).setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClientBuilder);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        configureMessageConverters(converters, mappingJackson2HttpMessageConverter);
        restTemplate.setMessageConverters(converters);
        //restTemplate.setErrorHandler(new CustomErrorHandler());
        if (loggingInterceptor != null) {
            restTemplate.setInterceptors(Collections.singletonList(loggingInterceptor));
        }
        return restTemplate;
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters,
                                           MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(stringHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter);
    }

}
