package ru.netology.web.test;

import com.codeborne.selenide.Condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;
import ru.netology.web.data.DataGenerator;

class ReplanDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }
        public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldPlanAndRepPlanMeeting() {

        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(validUser.getCity);
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id='date'] input").press(Keys.BACK_SPACE);
        String datePlanning = generateDate(4, "dd.MM.yyy");
        $("[data-test-id='date'] input").setValue(datePlanning);
        $("[data-test-id='name'] input").setValue(validUser.getName);
        $("[data-test-id='phone'] input").setValue(validUser.getPhone);
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".notification__content")
                .shouldBe(Condition.visible, ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + datePlanning));


    }
}
