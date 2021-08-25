package factories;

import com.github.javafaker.Faker;
import pojo.CreateUsersPojo;

public class UserDataFactory {
    Faker faker = new Faker();

    public CreateUsersPojo userAdm(){
        CreateUsersPojo user = new CreateUsersPojo();
        user.setAdministrador("true");
        user.setNome(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("teste");
        return user;
    }

    public CreateUsersPojo userWithoutTypeAdm(){
        CreateUsersPojo user = new CreateUsersPojo();
        user.setNome(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("teste");
        return user;
    }

    public CreateUsersPojo userWithoutName(){
        CreateUsersPojo user = new CreateUsersPojo();
        user.setAdministrador("true");
        user.setEmail(faker.internet().emailAddress());
        user.setPassword("teste");
        return user;
    }

    public CreateUsersPojo userWithoutPassword(){
        CreateUsersPojo user = new CreateUsersPojo();
        user.setNome(faker.name().fullName());
        user.setAdministrador("true");
        user.setEmail(faker.internet().emailAddress());
        return user;
    }

    public CreateUsersPojo userWithoutEmail(){
        CreateUsersPojo user = new CreateUsersPojo();
        user.setNome(faker.name().fullName());
        user.setAdministrador("true");
        user.setPassword("teste");
        return user;
    }
}
