package com.expositura.intake.rest.app.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@ComponentScan({"com.expositura"})
@Configuration
public class Config {

  @Value("${aws.accessKey:null}")
  private String awsAccessKey;
  
  @Value("${aws.region:null}")
  private String awsRegion;

  @Value("${aws.secretKey:null}")
  private String awsSecretKey;
  
  @Value("${aws.sessionToken:null}")
  private String awsSessionToken;

  @Value("${es.maxConnectTimeMilli:300000}")
  private int maxConnectTimeMilli;

  @Value("${es.maxSocketTimeMilli:72000000}")
  private int maxSocketTimeMilli;

  @Value("${ES_APIKEY}")
  private String esApiKey;
  
  @Value("${es.hosts}")
  private List<String> esHosts;
  
  @Value("${isLocal:false}")
  private Boolean isLocal;
  
  @Bean
  public ElasticsearchClient esClient() {
    return ElasticsearchClient.of(e -> e
        .hosts(convertToUriList(esHosts))
        .apiKey(esApiKey));
  }
  
  private List<URI> convertToUriList(List<String> hostStrings) {
    if (hostStrings == null || hostStrings.isEmpty()) {
      throw new IllegalStateException("No Elasticsearch host(s) have been configured");
    }
    final List<URI> uriList = new ArrayList<>();
    for (final String host : hostStrings) {
      try {
        uriList.add(new URI(host));
      } catch (final URISyntaxException ex) {
        throw new IllegalStateException("Encountered invalid Elasticsearch host value of '" + host + "'");
      }
    }
    return uriList;
  }
  
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return new ObjectMapper(new JsonFactoryBuilder()
            .streamReadConstraints(StreamReadConstraints.builder().maxStringLength(Integer.MAX_VALUE).build()).build())
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
  }

  @Bean
  public S3Client s3Client() {
    if (isLocal) {
      return S3Client.builder()
        .region(Region.of(awsRegion))
        .credentialsProvider(
          StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
        .build();
    } else {
      return S3Client.builder()
        .region(Region.of(awsRegion))
        .build();
    }
  }
}
