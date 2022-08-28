package scooter.model;

public class CourierGenerator extends CourierFieldsGenerator {

    public static Courier getDefault() {
        return new Courier(
                "Serpent",
                "1234",
                "Crawly");
    }

    public static Courier getRandom() {
        return new Courier(
                getRandomLogin(),
                getRandomPassword(),
                getRandomFirstName()
        );
    }

    public static Courier getDublicateLogin(Courier courier) {
        return new Courier(
                courier.getLogin(),
                getRandomPassword(),
                getRandomFirstName()
        );
    }

    public static Courier getEmptyLogin() {
        return new Courier(
                "",
                getRandomPassword(),
                getRandomFirstName()
        );
    }

    public static Courier getWrongLogin(Courier courier) {
        return new Courier(
                CourierFieldsGenerator.getRandomLogin(),
                courier.getPassword(),
                courier.getFirstName()
        );
    }


    public static Courier getEmptyPassword() {
        return new Courier(
                getRandomLogin(),
                "",
                getRandomFirstName()
        );
    }

    public static Courier getWrongPassword(Courier courier) {
        return new Courier(
                courier.getLogin(),
                CourierFieldsGenerator.getRandomPassword(),
                courier.getFirstName()
        );
    }


    public static String getClientJsonWithoutLoginField() {
        String json = "{ \"password\" : \""
                + getRandomPassword()
                + "\""
                + " , \"firstName\" : \""
                + getRandomFirstName()
                + "\" } ";
        return json;
    }

    public static String getCredentialsJsonWithoutLoginField(Courier courier) {
        String json = "{ \"password\" : \""
                + courier.getPassword()
                + "\" } ";
        return json;
    }

    public static String getClientJsonWithoutPasswordField() {
        String json = "{ \"login\" : \""
                + getRandomLogin()
                + "\""
                + " , \"firstName\" : \""
                + getRandomFirstName()
                + "\" } ";

        return json;
    }

    public static String getCredentialsJsonWithoutPasswordField(Courier courier) {
        String json = "{ \"login\" : \""
                + courier.getLogin()
                + "\" } ";
        return json;
    }


}
