package grupo3.tallerprogramacion2.mensajero.dto;

import org.json.JSONObject;

import java.util.HashMap;

public class UserDTO extends BaseDTO {

    private String username;
    private String name;
    private String password;
    private String token;
    private boolean online;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isOnline(){
        return this.online;
    }

    public void setOnline(boolean online){
        this.online = online;
    }

    public JSONObject toJSONObject(){
        HashMap<String,String> params = new HashMap<String,String>();
        if (this.username != "") params.put("username", this.username);
        if (this.name != "") params.put("name", this.name);
        if (this.password != "") params.put("password", this.password);
        if (this.token != "") params.put("token", this.token);
        //TODO ver si hace falta online
        return new JSONObject(params);

    }
}
