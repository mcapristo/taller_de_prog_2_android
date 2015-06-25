package grupo3.tallerprogramacion2.mensajero.activity;

import android.test.ActivityInstrumentationTestCase2;
import grupo3.tallerprogramacion2.mensajero.exceptions.ExceptionsHandle;

public class ExceptionHandlerTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity loginActivity;

    public ExceptionHandlerTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        loginActivity = getActivity();
    }

    public void testWrongUsername() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 1);
        assertEquals("Username inválido", handler.getDialogTitle(1));
    }

    public void testWrongPassword() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 2);
        assertEquals("Password inválido", handler.getDialogTitle(2));
    }

    public void testWrongToken() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 3);
        assertEquals("Token inválido", handler.getDialogTitle(3));
    }

    public void testMessageFailed() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 4);
        assertEquals("Error al envíar mensaje", handler.getDialogTitle(4));
    }

    public void testWrongProfile() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 5);
        assertEquals("No existe el perfil de usuario", handler.getDialogTitle(5));
    }

    public void testExistentUser() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 6);
        assertEquals("Usuario existente", handler.getDialogTitle(6));
    }

    public void testMissingPassword() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 7);
        assertEquals("Falta password", handler.getDialogTitle(7));
    }

    public void testMissingUsername() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 8);
        assertEquals("Falta username", handler.getDialogTitle(8));
    }

    public void testInvalidJson() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 9);
        assertEquals("Json inválido", handler.getDialogTitle(9));
    }

    public void testConnectionError() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, 1001);
        assertEquals("Error de conexión", handler.getDialogTitle(1001));
    }

    public void testUnknownError() {
        ExceptionsHandle handler = new ExceptionsHandle(loginActivity, -1);
        assertEquals("Error desconocido", handler.getDialogTitle(-1));
    }
}
