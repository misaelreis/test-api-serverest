import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.car.*;
import tests.login.*;
import tests.product.*;
import tests.users.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CancelCarTest.class,
        ConcludeCarTest.class,
        CreateCarTest.class,
        ListCarTest.class,
        LoginTest.class,
        CreateProductTest.class,
        DeleteProductTest.class,
        ListProductTest.class,
        UpdateProductTest.class,
        CreateUserTest.class,
        DeleteUserTest.class,
        ListUserTest.class,
        UpdateUserTest.class,
})
public class Runner {

}
