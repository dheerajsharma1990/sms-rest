package com.theapp.sms.utils;

import org.testng.annotations.Test;

import com.theapp.sms.exceptions.TheAppException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UtilsTest {
	
	public static final String USERNAME = "9999770595";
	public static final String PASSWORD = "luckyman@qwe";

	@Test
	public void shouldGetEmptyString() {
		assertThat(Utils.isEmpty(null), is(true));
		assertThat(Utils.isEmpty(" "), is(true));
		assertThat(Utils.isEmpty("blah"), is(false));
		assertThat(Utils.isEmpty(" blah"), is(false));
	}

	
	@Test
	public void shouldTestResponseCodeCorrectly(){
		boolean valid1 = Utils.isValidCode(200);
		boolean valid2 = Utils.isValidCode(302);
		boolean valid3 = Utils.isValidCode(500);
		boolean valid4 = Utils.isValidCode(404);
		
		assertThat(valid1,is(true));
		assertThat(valid2,is(true));
		assertThat(valid3,is(false));
		assertThat(valid4,is(false));
	}

}
