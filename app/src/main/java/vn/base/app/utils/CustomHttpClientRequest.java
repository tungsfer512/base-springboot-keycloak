package vn.base.app.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.HttpMethod;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomHttpClientRequest {

    HttpMethod method;
    URIBuilder uriBuilder;
    List<Header> headers;

    public CustomHttpClientRequest(HttpMethod method, String uri) throws URISyntaxException {
        this.method = method;
        this.uriBuilder = new URIBuilder(uri);
        this.headers = new ArrayList<>();
    }

    public CustomHttpClientRequest(HttpMethod method, String uri, List<Header> headers) throws URISyntaxException {
        this.method = method;
        this.uriBuilder = new URIBuilder(uri);
        this.headers = headers;
    }

    public CustomHttpClientRequest(HttpMethod method, String uri, List<Header> headers, Map<String, String[]> params)
            throws URISyntaxException {
        this.method = method;
        this.uriBuilder = new URIBuilder(uri);
        this.headers = headers;
        if (params != null) {
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                for (String value : values) {
                    this.uriBuilder.addParameter(key, value);
                }
            }
        }
    }

    public void addQueryParam(String key, String value) {
        this.uriBuilder.addParameter(key, value);
    }

    public void addQueryParam(String key, String[] values) {
        for (String value : values) {
            this.uriBuilder.addParameter(key, value);
        }
    }

    public void addQueryParams(Map<String, String[]> extraParams) {
        if (extraParams != null) {
            for (Map.Entry<String, String[]> entry : extraParams.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                for (String value : values) {
                    this.uriBuilder.addParameter(key, value);
                }
            }
        }
    }

    public HttpResponse request() {
        try {
            URI uri = this.uriBuilder.build();
            CloseableHttpClient httpClient = SkipSSLClient.CreateHttpClient();
            HttpRequestBase httpRequest = null;
            if (this.method == HttpMethod.GET) {
                httpRequest = new HttpGet(uri);
            } else if (this.method == HttpMethod.POST) {
                httpRequest = new HttpPost(uri);
            } else if (this.method == HttpMethod.PUT) {
                httpRequest = new HttpPut(uri);
            } else if (this.method == HttpMethod.PATCH) {
                httpRequest = new HttpPatch(uri);
            } else if (this.method == HttpMethod.DELETE) {
                httpRequest = new HttpDelete(uri);
            } else if (this.method == HttpMethod.HEAD) {
                httpRequest = new HttpHead(uri);
            } else if (this.method == HttpMethod.OPTIONS) {
                httpRequest = new HttpOptions(uri);
            } else if (this.method == HttpMethod.TRACE) {
                httpRequest = new HttpTrace(uri);
            } else {
                throw new IllegalArgumentException("Invalid Method!!!");
            }
            if (headers != null) {
                for (Header header : this.headers) {
                    httpRequest.addHeader(header);
                }
            }
            httpRequest.removeHeaders("Content-Length");
            HttpResponse httpResponse;
            httpResponse = httpClient.execute(httpRequest);
            return httpResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpResponse request(HttpEntity entity) {
        try {
            URI uri = this.uriBuilder.build();
            CloseableHttpClient httpClient = SkipSSLClient.CreateHttpClient();
            HttpEntityEnclosingRequestBase httpRequest = null;
            if (this.method == HttpMethod.POST) {
                httpRequest = new HttpPost(uri);
            } else if (this.method == HttpMethod.PUT) {
                httpRequest = new HttpPut(uri);
            } else if (this.method == HttpMethod.PATCH) {
                httpRequest = new HttpPatch(uri);
            } else {
                throw new IllegalArgumentException("Invalid Method!!!");
            }
            if (headers != null) {
                for (Header header : this.headers) {
                    httpRequest.addHeader(header);
                }
            }
            httpRequest.removeHeaders("Content-Length");
            httpRequest.setEntity(entity);
            HttpResponse httpResponse;
            httpResponse = httpClient.execute(httpRequest);
            return httpResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
