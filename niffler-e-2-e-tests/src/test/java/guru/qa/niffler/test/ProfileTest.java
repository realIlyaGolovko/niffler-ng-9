package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.UserExtension;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.UserModel;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class, UserExtension.class, CategoryExtension.class})
public class ProfileTest {

    private final Config CFG = Config.getInstance();

    @User
    @Category(isArchived = true)
    @Test
    void archiveCategoryShouldPresentInCategoryList(UserModel createdUser, CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .fillLoginPage(createdUser.username(), createdUser.password())
                .submit()
                .checkThatPageLoaded();

        Selenide.open(CFG.profileUrl(), ProfilePage.class)
                .showArchivedCategory()
                .shouldHaveCategory(category.name());
    }

    @User
    @Category
    @Test
    void activeCategoryShouldPresentInCategoryList(UserModel createdUser, CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .fillLoginPage(createdUser.username(), createdUser.password())
                .submit()
                .checkThatPageLoaded();

        Selenide.open(CFG.profileUrl(), ProfilePage.class)
                .shouldHaveCategory(category.name());
    }
}
