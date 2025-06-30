package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitPasswordInput = $("input[name='passwordSubmit']");
    private final SelenideElement signUpButton = $("button[type='submit']");
    private final SelenideElement loginPageLink = $("a.form_sign-in");

    public RegisterPage shouldHaveErrorMessage(String errorMessage) {
        errorText(errorMessage).shouldBe(visible);
        return this;
    }

    public RegisterPage setUserName(String userName) {
        usernameInput.setValue(userName);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setSubmitPassword(String password) {
        submitPasswordInput.setValue(password);
        return this;
    }

    public RegisterPage fillRegisterPage(String userName, String password) {
        setUserName(userName);
        setPassword(password);
        setSubmitPassword(password);
        return this;
    }

    public RegisterPage signUp() {
        signUpButton.click();
        return this;
    }

    public LoginPage signIn() {
        signUp();
        loginPageLink.click();
        return new LoginPage();
    }

    private SelenideElement errorText(String text) {
        return $(byText(text));
    }
}