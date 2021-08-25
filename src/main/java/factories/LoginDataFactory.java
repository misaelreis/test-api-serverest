package factories;

import pojo.LoginPojo;

public class LoginDataFactory {

    public static LoginPojo loginSuccess(){
        LoginPojo login = new LoginPojo();
        login.setEmail("misael@qa.com.br");
        login.setPassword("teste");
        return login;
    }

    public static LoginPojo loginWithoutEmail(){
        LoginPojo login = new LoginPojo();
        login.setPassword("teste");
        return login;
    }

    public static LoginPojo loginWithoutPassword(){
        LoginPojo login = new LoginPojo();
        login.setEmail("misael@qa.com.br");
        return login;
    }
}
