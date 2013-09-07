package com.theapp.sms.params;

import com.theapp.sms.exceptions.TheAppException;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ParamsTest {

    @Test
    public void shouldGetCorrectParamString() throws TheAppException {
        Params params = ParamsBuilder.loginParamBuilder()
                .withUsername("9811237654")
                .withPassword("@1231~0").build();
        String query = params.getParamsAsString();
        assertThat(query.contains("username"), is(true));
        assertThat(query.contains("@"), is(false));
        assertThat(query.contains("pass"), is(true));
        assertThat(query.contains("%40"), is(true));
    }
}
