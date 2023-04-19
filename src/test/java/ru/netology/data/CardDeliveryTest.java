package ru.netology.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;
class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $x("//input[@placeholder=\"Город\"]").val(validUser.getCity());
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $x("//*[contains(text(),'Успешно!')]").should(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").should(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $x("//input[@type=\"tel\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(secondMeetingDate);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id=replan-notification]").should(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(),'Перепланировать')]").click();
        $("[data-test-id=success-notification] .notification__content").should(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}