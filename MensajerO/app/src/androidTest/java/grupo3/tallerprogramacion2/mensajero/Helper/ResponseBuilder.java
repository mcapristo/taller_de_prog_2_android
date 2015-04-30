package grupo3.tallerprogramacion2.mensajero.Helper;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResponseBuilder {

    public static HttpResponse getExpectedResponse(String jsonResponse) {
        HttpResponse response
                = new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
        List<Header> headers = defaultHeaders();
        response.setHeaders(headers.toArray(new Header[0]));
        response.setLocale(Locale.getDefault());
        try {
            response.setEntity(createEntity(jsonResponse));
        } catch (UnsupportedEncodingException e) {
            response.setEntity(null);
        }

        return response;
    }

    private static List<Header> defaultHeaders() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd mmm yyyy HH:mm:ss zzz");
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Date", dateFormat.format(new Date())));
        // Data below is header info of my server
        headers.add(new BasicHeader("Server","Apache/1.3.42 (Unix) mod_ssl/2.8.31 OpenSSL/0.9.8e"));
        return headers;
    }

    private static HttpEntity createEntity(String jsonResponse) throws UnsupportedEncodingException{
        return new StringEntity(jsonResponse);
    }

}
