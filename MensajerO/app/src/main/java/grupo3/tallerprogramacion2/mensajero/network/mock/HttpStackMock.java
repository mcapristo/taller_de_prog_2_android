package grupo3.tallerprogramacion2.mensajero.network.mock;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import grupo3.tallerprogramacion2.mensajero.constants.UrlConstants;

/**
 * Fake {@link HttpStack} that returns the fake content using resource file in res/raw.
 */
public class HttpStackMock implements HttpStack {
    private static final String DEFAULT_JSON_RESPONSE = " {\"a\":1,\"b\":2,\"c\":3}";

    private static final int SIMULATED_DELAY_MS = 500;

    public HttpStackMock() {}

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> stringStringMap)
            throws IOException, AuthFailureError {
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        HttpResponse response
                = new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
        List<Header> headers = defaultHeaders();
        response.setHeaders(headers.toArray(new Header[0]));
        response.setLocale(Locale.getDefault());
        response.setEntity(createEntity(request));
        return response;
    }

    private List<Header> defaultHeaders() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd mmm yyyy HH:mm:ss zzz");
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Date", dateFormat.format(new Date())));
        // Data below is header info of my server
        headers.add(new BasicHeader("Server","Apache/1.3.42 (Unix) mod_ssl/2.8.31 OpenSSL/0.9.8e"));
        return headers;
    }

    /**
     * returns the fake content using resource file in res/raw. fake_res_foo.txt is used for
     * request to http://example.com/foo
     */
    private HttpEntity createEntity(Request request) throws UnsupportedEncodingException {
        return new StringEntity(this.getExpectedResponse(request.getUrl()));
    }

    private String getExpectedResponse (String url) {
        String result = DEFAULT_JSON_RESPONSE;
        if(url != null) {
            if(url.contains(UrlConstants.getLoginServiceUrl())) {
                result = "{result:'OK', username:'mcapristo', name:'Matias', token:'ABC123123'}";
            }
        }
        return result;
    }
}