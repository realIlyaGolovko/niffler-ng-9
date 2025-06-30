package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.jupiter.annotation.Registration;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.RegistrationExtension;
import guru.qa.niffler.jupiter.extension.UserExtension;
import guru.qa.niffler.model.RegistrationModel;
import guru.qa.niffler.model.UserModel;
import guru.qa.niffler.page.RegisterPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

//TODO: реализовать экстеншен для очистки таблицы с пользователями перед каждым тестом
@ExtendWith({BrowserExtension.class, RegistrationExtension.class, UserExtension.class})
public class RegistrationTest {
    public static final Config CFG = Config.getInstance();

    @Registration
    @Test
    void shouldRegisterNewUser(RegistrationModel registrationModel) {
        Selenide.open(CFG.registerUrl(), RegisterPage.class)
                .fillRegisterPage(registrationModel.username(), registrationModel.password())
                .signIn()
                .shouldBeLoadedLoginPage();
    }

    @User
    @Registration
    @Test
    void shouldNotRegisterUserWithExistingUserName(UserModel createdUser, RegistrationModel registrationModel) {
        Selenide.open(CFG.registerUrl(), RegisterPage.class)
                .fillRegisterPage(createdUser.username(), registrationModel.password())
                .signUp()
                .shouldHaveErrorMessage("Username `" + createdUser.username() + "` already exists");

    }

    @Registration
    @Test
    void shouldShowErrorWhenIfPasswordAndConfirmPasswordAreNotEquals(RegistrationModel registrationModel) {
        Selenide.open(CFG.registerUrl(), RegisterPage.class)
                .setUserName(registrationModel.username())
                .setPassword(registrationModel.password())
                .setSubmitPassword("some password")
                .signUp()
                .shouldHaveErrorMessage("Passwords should be equal");
    }

}
