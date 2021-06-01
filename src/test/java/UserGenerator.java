import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserGenerator {

    private final Faker faker = new Faker(new Locale("ru"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    void setUpAll(UserData userData) {
        given()
                .spec(requestSpec)
                .body(userData)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public UserData generateActiveUser() {
        UserData userData = new UserData(
                faker.name().firstName(),
                faker.internet().password(),
                Status.active);
        setUpAll(userData);
        return userData;
    }

    public UserData generateBlockedUser() {
        UserData userData = new UserData(
                faker.name().firstName(),
                faker.internet().password(),
                Status.blocked);
        setUpAll(userData);
        return userData;
    }

    public UserData generateActiveUserInvalidLogin() {
        String password = faker.internet().password(); // сгенерированный пароль верный
        UserData userData = new UserData(
                faker.name().firstName(),
                password,
                Status.active);
        setUpAll(userData);
        return new UserData(faker.name().firstName(), password, Status.active);
    }

    public UserData generateActiveUserInvalidPassword() {
        String login = faker.name().firstName();
        UserData userData = new UserData(
                login,
                faker.internet().password(),
                Status.active);
        setUpAll(userData);
        return new UserData(login, faker.internet().password(), Status.active);
    }

    public UserData generateBlockedUserInvalidLogin() {
        String password = faker.internet().password();
        UserData userData = new UserData(
                faker.name().firstName(),
                password,
                Status.blocked);
        setUpAll(userData);
        return new UserData(faker.name().firstName(), password, Status.active);
    }

    public UserData generateBlockedUserInvalidPassword() {
        String login = faker.name().firstName();
        UserData userData = new UserData(
                login,
                faker.internet().password(),
                Status.blocked);
        setUpAll(userData);
        return new UserData(login, faker.internet().password(), Status.active);
    }

}