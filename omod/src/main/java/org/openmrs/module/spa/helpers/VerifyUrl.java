package org.openmrs.module.spa.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.context.Context;

import static org.openmrs.module.spa.utils.SpaModuleUtils.GLOBAL_PROPERTY_FRONTEND_WHITELIST_URLS;

public class VerifyUrl {

    public String getWhitelistUrls() {
        String whiteListUrls;
        whiteListUrls = Context.getAdministrationService().getGlobalProperty(GLOBAL_PROPERTY_FRONTEND_WHITELIST_URLS);
        System.out.println(whiteListUrls);
        if (StringUtils.isBlank(whiteListUrls)) {
            whiteListUrls = GLOBAL_PROPERTY_FRONTEND_WHITELIST_URLS;
        }
        return whiteListUrls;
    }

    // :::::::::::::::::::::::::::::::: BEGIN ::::::::::::::::::::::::::://
    public boolean urlValidate(String _url) {

        String[] temp = getStringUrls();
        System.out.println(temp[0]);

        boolean pass = true;
        // test for both _regex expression and absolute string cases.
        if ((regexTest(_url) == true) || (stringTest(_url) == true)) {
            pass = true;
        } else {
            pass = false;
        }

        System.out.println("the function " + pass);
        return pass;
    }

    // :::::::::::::::::::::::::: Main functions :::::::::::::::::::::://

    // REGEX TEST
    public boolean regexTest(String testUrl) {
        String urlToTest = testUrl;
        String[] whiteListRegexUrl = getStringUrls();
        boolean returnValue = false;
        System.out.println("regex gives test " + returnValue);

        for (String s : whiteListRegexUrl) {
            Pattern p = Pattern.compile(s);
            boolean matches = false;
            Matcher m = p.matcher(urlToTest);
            matches = m.matches();
            if (matches == true) {
                returnValue = true;
                break;
            }
            if (matches == false) {
                returnValue = false;
            }
        }

        return returnValue;
    }

    // ABSOLUTE STRING TEST
    public boolean stringTest(String _testUrl) {

        boolean returnValue = false;
        boolean temp_value = false;
        String[] whiteListStringUrl = getStringUrls();
        for (String s : whiteListStringUrl) {
            temp_value = s.equalsIgnoreCase(_testUrl);
            if (temp_value == true) {
                System.out.println("String test success");
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

    // ::::::::::::::::::: Helper functions :::::::::::::::::::::::::://

    // SEPERATE THE INPUT

    public String[] getStringUrls() {
        return splitUrl(getWhitelistUrls());

    }

    // SPLIT THE INPUT STRING
    public String[] splitUrl(String url) {
        String[] splitName = url.split(",");
        return splitName;
    }

}