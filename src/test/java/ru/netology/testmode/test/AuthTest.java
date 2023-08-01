package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        SelenideElement form = $("form");
        form.$("input[name='login']").setValue(registeredUser.getLogin());
        form.$("input[name='password']").setValue(registeredUser.getPassword());
        form.$("[class='button__text']").click();
        $("h2").shouldBe(Condition.visible, Duration.ofSeconds(10)).shouldHave(Condition.exactText("Личный кабинет"));

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        SelenideElement form = $("form");
        form.$("input[name='login']").setValue(notRegisteredUser.getLogin());
        form.$("input[name='password']").setValue(notRegisteredUser.getPassword());
        form.$("[class='button__text']").click();
        $("[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(3)).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        SelenideElement form = $("form");
        form.$("input[name='login']").setValue(blockedUser.getLogin());
        form.$("input[name='password']").setValue(blockedUser.getPassword());
        form.$("[class='button__text']").click();
        $("[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(3)).shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        SelenideElement form = $("form");
        form.$("input[name='login']").setValue(wrongLogin);
        form.$("input[name='password']").setValue(registeredUser.getPassword());
        form.$("[class='button__text']").click();
        $("[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(7)).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }


    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        SelenideElement form = $("form");
        form.$("input[name='login']").setValue(registeredUser.getLogin());
        form.$("input[name='password']").setValue(wrongPassword);
        form.$("[class='button__text']").click();
        $("[class='notification__content']").shouldBe(Condition.visible, Duration.ofSeconds(7)).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

}
