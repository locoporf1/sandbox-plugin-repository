package org.locoporf1.idea.sandbox.plugin.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.locoporf1.idea.sandbox.plugin.model.Sandbox;

public class MyApplicationServiceImpl implements MyApplicationService {

  private static final String SERVER_HOST = "sandboxserver-env.eba-tije36bi.us-east-1.elasticbeanstalk.com";//"localhost";
  private static final Integer SERVER_PORT = 80;//8080;
  private static final String SERVER_SCHEMA = "http";
  private static final String SERVER_CONTEXT_PATH = "";//"/sandbox-server";
  private static final Pattern SANDBOX_NAME_PATTERN = Pattern.compile("\\w+\\s(\\d+).*");
  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  public MyApplicationServiceImpl() {
    httpClient = new HttpClient();
    httpClient.setHostConfiguration(getHostConfiguration());
    objectMapper = new ObjectMapper();
  }

  @Override
  public Optional<List<Sandbox>> getList() throws IOException {
    Optional<List<Sandbox>> result = Optional.empty();
    Sandbox[] sandboxes = null;
    HttpMethod httpMethod = getListMethod();
    int i = httpClient.executeMethod(httpMethod);
    if (i == 200) {
      String responseBodyAsString = httpMethod.getResponseBodyAsString();
      sandboxes = objectMapper.readValue(responseBodyAsString, Sandbox[].class);
      result = Optional.of(Arrays.asList(sandboxes));
    }
    System.out.println("STATUS=" + i);
    return result;
  }

  @Override
  public Optional<Sandbox> request(Sandbox sandbox) throws IOException {
    Optional<Sandbox> result = Optional.empty();
    HttpMethod httpMethod = getRequestMethod(sandbox);
    int i = httpClient.executeMethod(httpMethod);
    if (i == 200) {
      String responseBodyAsString = httpMethod.getResponseBodyAsString();
      result = Optional.of(objectMapper.readValue(responseBodyAsString, Sandbox.class));
    }
    return result;
  }

  @Override
  public String getUsername() {
    return System.getProperty("user.name");
  }

  @Override
  public String getSandboxLabel(Sandbox sandbox) {
    String text = sandbox.getName();
    return sandbox.isFree() ? text : text + " - " + formatOwner(sandbox);
  }

  private HostConfiguration getHostConfiguration() {
    HostConfiguration result = HostConfiguration.ANY_HOST_CONFIGURATION;
    result.setHost(SERVER_HOST, SERVER_PORT, SERVER_SCHEMA);
    return result;
  }

  private HttpMethod getListMethod() {
    return new GetMethod(SERVER_CONTEXT_PATH + "/list");
  }

  private HttpMethod getRequestMethod(Sandbox sandbox) {
    HttpMethod result = new PutMethod(SERVER_CONTEXT_PATH + "/request");
    NameValuePair[] params = new NameValuePair[2];
    params[0] = new NameValuePair("name", getUsername());
    params[1] = new NameValuePair();
    params[1].setName("sandbox");
    Matcher matcher = SANDBOX_NAME_PATTERN.matcher(sandbox.getName());
    if (matcher.matches()) {
      params[1].setValue(matcher.group(1));
    }
    result.setQueryString(params);
    return result;
  }

  private String formatOwner(Sandbox sandbox) {
    String user = MyApplicationService.getInstance().getUsername();
    return user.equals(sandbox.getOwner()) ? "ME" : sandbox.getOwner();
  }

}
