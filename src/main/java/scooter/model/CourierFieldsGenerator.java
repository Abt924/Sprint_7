package scooter.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;


public class CourierFieldsGenerator {
    static final int LOGIN_MIN=2;
    static final int LOGIN_MAX=16;
    static final int PASSWORD_MIN=8;
    static final int PASSWORD_MAX=16;
    static final int FIRST_NAME_MIN=1;
    static final int FIRST_NAME_MAX=30;


    public static String getRandomLogin() {
        return RandomStringUtils.random(
                RandomUtils.nextInt(LOGIN_MIN,LOGIN_MAX+1), true, true);
    }

    public static String getRandomPassword() {
        return RandomStringUtils.random(
                RandomUtils.nextInt(PASSWORD_MIN, PASSWORD_MAX+1), true, true);
    }

    public static String getRandomFirstName() {
        return RandomStringUtils.random(
                RandomUtils.nextInt(FIRST_NAME_MIN, FIRST_NAME_MAX+1), true, true);
    }

}
