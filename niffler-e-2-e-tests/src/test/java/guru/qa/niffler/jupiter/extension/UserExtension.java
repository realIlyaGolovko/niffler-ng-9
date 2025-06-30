package guru.qa.niffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserModel;
import guru.qa.niffler.page.RegisterPage;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

public class UserExtension implements BeforeEachCallback, ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(
            UserExtension.class
    );
    private final Faker faker = new Faker();
    private static final String DEFAULT_PASS = "123";
    private final Config CFG = Config.getInstance();

    @Override
    public void beforeEach(ExtensionContext context){

        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(),
                User.class).ifPresent(
                anno -> {
                    final String userName = anno.name().isBlank() ? faker.name().username() : anno.name();
                    createUser(userName, DEFAULT_PASS);
                    UserModel userModel = new UserModel(
                            userName, DEFAULT_PASS
                    );
                    context.getStore(NAMESPACE).put(context.getUniqueId(),
                            userModel
                    );
                }
        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserModel.class);
    }

    @Override
    public UserModel resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), UserModel.class);
    }

    //TODO: создавать через DB
    private void createUser(String userName, String password) {
        Selenide.open(CFG.registerUrl(), RegisterPage.class)
                .fillRegisterPage(userName, password)
                .signUp();
        Selenide.closeWebDriver();
    }
}
