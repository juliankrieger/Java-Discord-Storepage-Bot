package structs;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.validator.routines.UrlValidator;

public class Store implements Serializable {
    private String name;
    private String url;

    public Store(String name, String url){
        if(name.trim().length() == 0) {
            throw new IllegalArgumentException("Store name must not be empty!");
        }

        this.name = name;
        this.url = url;
    }

    public String toString(){
        return String.format("------ %s \t %s", name, url.toString());
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public boolean isOnline() {
        UrlValidator urlValidator = new UrlValidator();

        HttpURLConnection connection = null;
        try {


            if (urlValidator.isValid(this.url)) {
                URL u = new URL(this.url);

                connection = (HttpURLConnection) u.openConnection();
                connection.setInstanceFollowRedirects(false);

                int responseCode = connection.getResponseCode();

                if ((responseCode == HttpURLConnection.HTTP_MOVED_PERM
                        || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                        || responseCode == HttpURLConnection.HTTP_SEE_OTHER
                        || responseCode == HttpURLConnection.HTTP_BAD_GATEWAY
                        || responseCode == HttpURLConnection.HTTP_BAD_METHOD
                        || responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT
                        || responseCode == HttpURLConnection.HTTP_FORBIDDEN
                        || responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR
                        || responseCode == HttpURLConnection.HTTP_NOT_FOUND)) {
                    return false;
                }

            }
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void crossOut(){
        this.name = "~~" + this.name + "~~";
        this.url = "~~" + this.url + "~~";
    }
}
