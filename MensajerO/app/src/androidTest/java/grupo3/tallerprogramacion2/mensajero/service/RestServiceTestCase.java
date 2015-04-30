package grupo3.tallerprogramacion2.mensajero.service;

import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;

import junit.framework.TestCase;

import org.apache.http.HttpResponse;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Map;

import grupo3.tallerprogramacion2.mensajero.Helper.ResponseBuilder;
import grupo3.tallerprogramacion2.mensajero.activity.LoginActivity;
import grupo3.tallerprogramacion2.mensajero.dto.UserDTO;
import grupo3.tallerprogramacion2.mensajero.service.impl.RestServiceImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RestServiceTestCase extends TestCase {

    private RestService restService =  RestServiceImpl.getInstance();
    private String JSON_LOGIN_RESPONSE_OK = "{result:'OK', username:'mcapristo', name:'Matias', token:'ABC123123'}";
    private HttpStack httpStack;

    @Override
    public void setUp() {
        httpStack = mock(HttpStack.class);
    }

    @Test
    public void testLoginResponseComplete() throws Exception {
        when(httpStack.performRequest(any(Request.class), any(Map.class))).then(new Answer<HttpResponse>() {
            @Override
            public HttpResponse answer(InvocationOnMock invocation) throws Throwable {
                return ResponseBuilder.getExpectedResponse(JSON_LOGIN_RESPONSE_OK);
            }
        });
        LoginActivity loginActivity = mock(LoginActivity.class);
        ArgumentCaptor<UserDTO> argument = ArgumentCaptor.forClass(UserDTO.class);
        verify(loginActivity).processLoginResponse(argument.capture());

        restService.login("mcapristo", "123456", new LoginActivity());

        assertEquals("mcapristo", argument.getValue().getUsername());
        assertEquals("Matias", argument.getValue().getName());
        assertEquals("ABC123123", argument.getValue().getToken());
    }



}
