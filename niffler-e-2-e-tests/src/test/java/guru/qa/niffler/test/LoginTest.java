package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.UserExtension;
import guru.qa.niffler.model.UserModel;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class, UserExtension.class})
public class LoginTest {

    private static final Config CFG = Config.getInstance();

    @User
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin(UserModel createdUser) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .fillLoginPage(createdUser.username(), createdUser.password())
                .submit()
                .checkThatPageLoaded();
    }


    @User
    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials(UserModel createdUser) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .fillLoginPage(createdUser.username(), "badPassword")
                .submitWheExpectError()
                .shouldHaveErrorMessage("Неверные учетные данные пользователя")
                .shouldBeLoadedLoginPage();

    }
}
