package com.theapp.sms.utils;

import com.theapp.sms.exceptions.TheAppException;
import com.theapp.sms.processors.CaptchaImage;
import com.theapp.sms.processors.CaptchaReader;
import com.theapp.sms.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.theapp.sms.utils.Utils.decodeImageData;

public class CaptchaGenerators {
    @Test
    public void setCookies() throws TheAppException, IOException {
        for (int i = 0; i < 4; i++) {
            CaptchaReader reader = new CaptchaReader(URLS.FORGOT_PASSWORD_URL);
            String captchaUrl = reader.getCaptchaUrl();
            CaptchaImage captchaImage = new CaptchaImage(captchaUrl);
            Response response = captchaImage.getResponse();
            writeCookie(response.getCookies().get("JSESSIONID"), "G:\\cookie" + i + ".txt");
            writeCaptcha(response.getCaptchaImage(), "G:\\captcha" + i + ".png");
        }
    }

    @Test
    public void write() throws IOException {
        writeCaptcha("iVBORw0KGgoAAAANSUhEUgAAAIwAAAAyCAIAAAABYqRsAAAH7klEQVR42u2ceVATVxjA39Rae-p02" +
                "hmrnelUq3_YsZ3RHjOdtk49yiGiVlFoAStqxWk9UASFigcWtVQ6RgWsKB0PLIcHyihUEDAGAcMZIEK4rwgJVziTQKDvZ" +
                "QtukvfChiQQcX-TYZJls-6-337f-95bfKCbRheOTgIdONoJyAi04WjVSwuOZgJNOKQEJAQacTQQeIJDTKAeR50OQDGInEAPjm" +
                "4CrGZzaAZ9anoJKHEoCLCazaQZDKjpx6Ei0IeD1Ww-zUC3uUlu-gmwms2tGeheJ7ZRSC2IbW5Ws2k1A62j9xDAnjfpOsdEM-lX4" +
                "0AzoNsjqcbeF6SjP7uaLTaaAT0JYtMlKb1ig5TVbA7NABYP2EpDf3HSRuB506w67zvwLejLuafRzG3SAafpA-tnqzplJtEMSAU7tr" +
                "on1YjPrWZFZRGUpDq0mm5XdcxlYOkkZXGWqaIZYAe9jQSwQ63xoblLyFee2KJa9wFs336nd3sPOfTwbjLRrNr6-YD1hB5xFWVXnnYL" +
                "alNGBpowmoHu3BFpogk7K0Wa23jmNEM3sHG1XvJwv2E1y6OOwz0V4ftQEEsbkOC91qaNZlCjQy2OOgLYKcJnTnObMEvhv7YjOaZF0oDU" +
                "VonkAc4oILyWDKtZVl81YDtR5Tgdvlf8vh6-kdVWmDZpg0pNqnBUayJ3n_f0drOe0Ld2moyzfZxpbr92Cl5dV-huJpo" +
                "VPrZw5x7OL_BnOy_O5EkblKkpJ1CBo9_2Jd3M0HLak7nmIWoIjJnmJ-KmR8nt5_erVkzpObCqUVzPRHNb_EWqEaBUcyR" +
                "tUFJSIiJQiqPqYRJKwW5zKLvV6fe6t38Jt3R6WTHXXKmXMdHcvf-7oRuu23eZJC2eueaGuhr4rX67l82UtIFQKHyMo5hAf" +
                "UwwCmpf-5JBxBFBaMuvKxhqhpThMCiaTa6512UGPYd3Hv6-rqqCoWZJ6k1UZWz7wkxJGxQSKMIBjUqCUOZtPLlLOIjMZxncUn" +
                "_5OBPNUo6HwnlGv82L8L7r8LYu5_Mo0wZFs_k0VxflSaI4vY7T4RW1nvRgqLkpmoNyycE1ZkraIB-HAEeBmrbdVvCEqv_hFAjyS" +
                "xNvNB90RPnB7UMmmpsPO2v1ZO1eVpRpg6K5BIcJNTdcDdFK4Po1tx9ZB_eXXg40U9IGOTRy9ZKnRv7D-1oN3bXp4yJe8rCahbx7_VYvqOxeKb91EcqWBLqjishtDnxvaDRjMaFm6RkfFEkBrgw1d-5aBPcX3ww3U9IG_EGyCGTTyeLDhqYbatu-gKHmiogTaH_PJZTsykuoJ2v87cd8Av-Hbza_Miq4PnhP656lcpeZpQkxBTiM0QwTOHyVcv-FUkX8NMkpT6p8rUiKHcNopgMyBsnE8UiTomvhKEe5f0J5bd_yGXRWeDuaieaC5DtUJIkiQ-BHaFfpMFXAS8nRREstvdyHX8_LzcnTQb9mbNKm0-7xje6IQhzqO7bRTAekqXlIIF2T0rP-8AIk3naU17w7V2EJ0LFhLkPNFaF-VBPI3D-Vr5kmSLjO10TLriAxTrnqrZadi6S-K1HP5zorW5McHEySNp3Hd28071vVt3wyvAl6V7_d4mMvio82SLPx0axfM-ByuQ8I8HQQ73OAjVV20jttkJqAjXBLOcdrWM25cVGyzfO7XGfDdkfT-3avis4FZGhC0lx64Q_4lWZPK7pyPg7dOO7c-JFuoMCXMO6KqTQbH836NYNUAvdxSLd-DS-vMDyQOwgvOanH4Z3eZa9lxsfq0Sy4Egyt1Ps58h5wM1KSav3XUS0liA4b8q1Hc-2RTaikDPx5SHkGAW3DGekw1nUNwY2PMjMYasb0zaaOZv2aAVeT-wQoc732b8Ar5CbdpessOBuASoAdi0iaeYkJypVvdjrPpD5S_1B5oHq8tduG-qg_mqU7FsKdH58_Sn1MI8AkadcGbICHqjnmzlQzrm82NJqN1Ay0rpOHg9SCXBy6goVnDqGebNsCukJRkAfqn73tU8kMHaHb6T24c0ZcDF2zFkySdvFf6jPxtDZGs8HRbLRmQD96OgHseZOuU9exKATVCyqbicJzRx5w78NXcYifcsUUuDEvInh4zakpqCy0mZhqOPTD5FwJVS2dJHOby01JHrHmIUZT89NxEkk19r4gHR17KtkJsbBD0u0Yqo9uZqI5P-osGmD9NM-YaE6_fR2mXLnD1LQ7sSPWTGc0NT-dccCmS1J6xQapHs2CuMhmj4VwkITqOvvXm3cuLowOY6hZFHYY5Sgv2xFHc3pyYqfLLHijZF-_NGLN9L55lDUDbKWhvzjJIWAmzQ0HnKCkylN7RhzNLVu_QnXHhSBjkrbxffOINQNswY6t7kk1opk0t3osxI5vSiM4Bml-4u-KUuufO4xM2sb3zSPWDHQHvUUEsOMs0rjMeM19yydjJeXzM5lHc2UYmiJp2mtvkqQ9VpqB1twRaaIJO11BmtuwEM2iG3-jWavN8_Oys8Y8aRujGWhNwWIna0mTu9gpQsvRLNu1GBuLFZGnRy1pm0Szxl8LYZ95kB6QlBGwHM2khFmUmz1q0WwSzejv7kiPC7HPFvU_pxpnmi0haaNpIdIj91oC2Ke8rGazagbm-C_trGbTa" +
                "gaWuXIBq5muGYzXBSrGk2bArkNi-ZoBuw6J5WsG7Doklq8ZsOuQWL5mwK5DYvmaAbsOieVrBuw6JJavGbDLzVi-5v8AWX7OHN41HR4AAAAASUVORK5CYII","G:\\image.png");
    }

    private void writeCaptcha(String imageData, String fileName) throws IOException {
        byte[] imageByteArray = decodeImageData(imageData);
        IOUtils.write(imageByteArray, new FileOutputStream(new File(fileName)));
    }

    private void writeCookie(String cookie, String fileName) throws IOException {
        IOUtils.write(cookie, new FileOutputStream(new File(fileName)));
    }
}
