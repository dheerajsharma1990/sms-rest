package com.theapp.sms.actions;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.theapp.sms.connection.ConnectionFactory;
import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.params.Params;
import com.theapp.sms.params.ParamsBuilder;
import com.theapp.sms.processors.CaptchaCombined;
import com.theapp.sms.processors.CaptchaImage;
import com.theapp.sms.processors.CaptchaReader;
import com.theapp.sms.processors.LoginAuthentication;
import com.theapp.sms.response.Response;
import com.theapp.sms.response.LoginResponse;
import com.theapp.sms.utils.URLS;

@Path("/by2")
public class By2 {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String welcome() {
        return "<html> " + "<title>" + "Welcome to 160by2 API" + "</title>"
                + "<body><h1>" + "Welcome to 160by2 API" + "</body></h1>"
                + "</html> ";
    }

    @POST
    @Path("/login")
    @Produces("application/xml")
    public String authenticateLogin(@FormParam("username") String username,
                                    @FormParam("password") String password) {

        System.out.println("Signing in with username[" + username + "] and password[" + password + "].");
        Params params = ParamsBuilder.loginParamBuilder().withUsername(username).withPassword(password).build();
        LoginAuthentication loginAuthentication = new LoginAuthentication(URLS.LOGIN_URL, params);
        Response response = loginAuthentication.getResponse();
        LoginResponse loginResponse = new LoginResponse(response);
        return loginResponse.getXmlResponse();
    }

    @POST
    @Path("/forgotPassword")
    @Produces("application/xml")
    public String forgotPassword(@FormParam("mobile") String mobile,
                                 @FormParam("captcha") String captcha) {
        System.out.println("Forgot password for mobile[" + mobile + "] with captcha[" + captcha + "].");

        return null;
    }

    @GET
    @Path("/forgotCaptcha")
    @Produces("application/xml")
    public String forgotCaptcha() {
        /*CaptchaCombined captchaCombined = new CaptchaCombined(URLS.FORGOT_PASSWORD_URL);
        return captchaCombined.getResponse().getCaptchaImageResponse();*/
        ConnectionFactory.testConnection();
        return "blah";
    }

}
