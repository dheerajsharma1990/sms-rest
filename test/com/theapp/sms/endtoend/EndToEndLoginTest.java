package com.theapp.sms.endtoend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import com.theapp.sms.params.Params;
import com.theapp.sms.params.ParamsBuilder;
import org.testng.annotations.Test;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.utils.Constants;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EndToEndLoginTest {

    private final String SERVER_URL = "http://localhost:8080/theapp/by2/login";

    @Test
    public void shouldLoginSuccessfully() throws IOException, TheAppException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        String query = getLoginQuery();
        PrintWriter printer = new PrintWriter(new OutputStreamWriter(
                connection.getOutputStream()), true);
        printer.print(query);
        printer.flush();
        printer.close();

        String response = getResponse(connection);
        String RESPONSE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<response>"
                + "<loginSuccessful>true</loginSuccessful>"
                + "<messageSent>false</messageSent>"
                + "<exception></exception>"
                + "</response>";
        assertThat(response, is(RESPONSE));

    }

    @Test
    public void shouldNotLogin() throws IOException, TheAppException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        String query = getInvalidLoginQuery();
        PrintWriter printer = new PrintWriter(new OutputStreamWriter(
                connection.getOutputStream()), true);
        printer.print(query);
        printer.flush();
        printer.close();
        String response = getResponse(connection);
        assertThat(response, is("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<response>"
                + "<loginSuccessful>false</loginSuccessful>"
                + "<messageSent>false</messageSent>"
                + "<exception>"
                + Constants.INVALID_LOGIN + "</exception>" + "</response>"));

    }

    private String getLoginQuery() throws TheAppException {
        Params params = ParamsBuilder.loginParamBuilder()
                .withUsername("9999770595")
                .withPassword("luckyman@qwe").build();
        return params.getParamsAsString();
    }

    private String getInvalidLoginQuery() throws TheAppException {
        Params params = ParamsBuilder.loginParamBuilder()
                .withUsername("9999770595")
                .withPassword("luckyman").build();
        return params.getParamsAsString();
    }

    private String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

}
