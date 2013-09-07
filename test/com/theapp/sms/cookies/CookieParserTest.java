package com.theapp.sms.cookies;

import com.theapp.sms.utils.Utils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CookieParserTest {

    @Test
    public void shouldParseCookie() {
        List<String> cookieSet = new ArrayList<String>();
        cookieSet.add("adMask=adMask; Expires=Sat, 24-Aug-2013 12:50:12 GMT");
        cookieSet.add("LastLoginCookie=\"24-08-2013-901-17:12-901-Mozilla FireFox 3.0.5-901-115.184.110.103-901-19-08-2012\"; Version=1; Max-Age=7200");
        cookieSet.add("JSESSIONID=NN~29B75DFDAB1A51F08DFD1AF77006CF06.8514; Path=/");

        Cookies cookies = CookieParser.parse(cookieSet);
        assertThat(cookies.size(), is(3));
        assertThat(cookies.get("LastLoginCookie"), notNullValue());

        List<String> cookieSet1 = new ArrayList<String>();
        cookies = CookieParser.parse(cookieSet1);
        assertThat(cookies.size(), is(0));
    }
}
