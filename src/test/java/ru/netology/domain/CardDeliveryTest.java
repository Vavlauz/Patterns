package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.openqa.selenium.Keys.chord;
import static ru.netology.domain.DataGenerator.*;

public class CardDeliveryTest {

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");

    }


    @Test
    void shouldSendForm() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(5, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate));

    }

    @Test
    void fieldTownEmpty() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").val("");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Поле обязательно')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldsAllEmpty() {
        $("[data-test-id='city'] input").val("");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").val("");
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Поле обязательно')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldTownWrong() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").val("Вадим");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Доставка в выбранный город недоступна')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldTownWrong2() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(3, 1);
        $("[data-test-id='city'] input").val("Novosibirsk");
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Доставка в выбранный город недоступна')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataEmpty() {
        RequestInfo requestInfo = generateRequestCard("ru");
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Неверно введена дата')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataCurrent() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(-3, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataWrong() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(-1, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldDataTrue() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameEmpty() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("");
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Поле обязательно')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameTrue() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Вадим");
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameTrue2() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков-Щедрин");
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate), ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameWrong() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Салтыков_Щедрин");
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldFamilyAndNameWrong2() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").val("Vadim");
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldPhoneEmpty() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").val("");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Поле обязательно для заполнения')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldPhoneWrong() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").val("+7999333666");
        $("[data-test-id=agreement]>.checkbox__box").click();
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear, ofSeconds(15));

    }

    @Test
    void fieldCheckBoxNotValue() {
        RequestInfo requestInfo = generateRequestCard("ru");
        String planningDate = generateDate(4, 1);
        $("[data-test-id='city'] input").setValue(requestInfo.getCity());
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.DELETE, planningDate);
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $(withText("Запланировать")).click();
        $x("//*[contains(text(),'Я соглашаюсь с условиями обработки и использования моих персональных данных')]").should(appear, ofSeconds(15));

    }

    @Test
    void shouldSubmitComplexRequest() {
        RequestInfo requestInfo = generateRequestCard("ru");
        $("[data-test-id='city'] input").val("ке");
        $(".input__menu").find(withText("Кемерово")).click();
        $("[placeholder='Дата встречи']").sendKeys(chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate dateOfMeeting = LocalDate.now().plusDays(7);
        String dateOfMeetingFormatted = dateOfMeeting.format(ofPattern("dd.MM.yyyy"));
        if (startDate.getMonthValue() != dateOfMeeting.getMonthValue()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(String.valueOf(dateOfMeeting.getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue(requestInfo.getName());
        $("[data-test-id='phone'] input").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]>.checkbox__box").click();
        $("button>.button__content").click();
        $("[class='notification__content']").shouldHave(text("Встреча успешно запланирована на " + dateOfMeetingFormatted), ofSeconds(15));

    }

    @Test
    void shouldRegister() {
        RequestInfo requestInfo = generateRequestCard("ru");
        $("[data-test-id=city] input").setValue(requestInfo.getCity());
        String firstDate = generateDate(0, 4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstDate);
        $("[name='name']").setValue(requestInfo.getName());
        $("[name='phone']").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .should(appear, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на  " + firstDate));
        String secondDate = generateDate(5, 4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondDate);
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .should(appear, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на  " + secondDate));
    }

}
