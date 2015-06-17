package grupo3.tallerprogramacion2.mensajero.dto;

import org.json.JSONObject;

import java.util.HashMap;

public class UserDTO extends BaseDTO {

    private String username;
    private String name;
    private String password;
    private String token;
    private String profileImage;
    private String location;
    private double latitud;
    private double longitud;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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
        if (this.profileImage != "") params.put("profileImage", this.profileImage);
        if (this.latitud != 0) params.put("latitud", String.valueOf(this.latitud));
        if (this.longitud != 0) params.put("longitud", String.valueOf(this.longitud));
        if (this.location != "") params.put("location", this.location);
        if (this.token != "") params.put("token", this.token);
        params.put("online", String.valueOf(this.online));
        return new JSONObject(params);
    }
}
