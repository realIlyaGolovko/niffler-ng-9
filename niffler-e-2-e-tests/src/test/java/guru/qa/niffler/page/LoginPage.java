package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement loginHeader = $("h1.header");

    public LoginPage shouldHaveErrorMessage(String errorMessage) {
        errorText(errorMessage).shouldBe(visible);
        return this;
    }

    public LoginPage fillLoginPage(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        return this;
    }

    public MainPage submit() {
        submitButton.click();
        return new MainPage();
    }

    public LoginPage submitWheExpectError() {
        submitButton.click();
        return this;
    }

    public LoginPage shouldBeLoadedLoginPage() {
        loginHeader.shouldBe(visible);
        return this;
    }

    private SelenideElement errorText(String text) {
        return $(byText(text));
    }
}
