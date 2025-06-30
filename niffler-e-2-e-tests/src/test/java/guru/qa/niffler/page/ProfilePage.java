package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    private final SelenideElement avatar = $(".MuiAvatar-root");
    private final SelenideElement uploadNewAvatarButton = $("[class*='MuiButtonBase-root'][role='button']");
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement userName = $("#username");
    private final SelenideElement saveChangeButton = $("button:contains('Save changes')");
    private final SelenideElement newCategoryInput = $("input[name='category']");
    private final SelenideElement archivedSwitcher = $("input[type='checkbox']");
    private final SelenideElement categories = $("div.MuiGrid-root.MuiGrid-container.css-17e75sl");

    public ProfilePage avatarShouldBeVisible() {
        avatar.shouldBe(visible);
        return this;
    }

    public ProfilePage uploadNewAvatar(File file) {
        uploadNewAvatarButton.uploadFile(file);
        return this;
    }


    public ProfilePage setName(String name) {
        nameInput.setValue(name);
        return this;
    }

    public ProfilePage shouldHaveName(String name) {
        nameInput.shouldHave(value(name));
        return this;
    }

    public ProfilePage shouldHaveUserName(String username) {
        this.userName.should(value(username));
        return this;
    }

    public ProfilePage clickSaveChangeButton() {
        saveChangeButton.click();
        return this;
    }

    public ProfilePage addCategory(String category) {
        newCategoryInput.setValue(category)
                .pressEnter();
        return this;
    }

    public ProfilePage showArchivedCategory() {
        archivedSwitcher.click();
        return this;
    }

    public ProfilePage shouldHaveCategory(String categoryName) {
        categories.shouldHave(text(categoryName));
        return this;
    }

}