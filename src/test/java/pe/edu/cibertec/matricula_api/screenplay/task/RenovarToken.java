package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class RenovarToken implements Task {

    private final boolean conElRefreshTokenAnterior;

    public RenovarToken(boolean conElRefreshTokenAnterior) {
        this.conElRefreshTokenAnterior = conElRefreshTokenAnterior;
    }

    public static RenovarToken conSuRefreshToken() {
        return new RenovarToken(false);
    }

    public static RenovarToken reutilizandoElRefreshTokenAnterior() {
        return new RenovarToken(true);
    }

    @Override
    @Step("{0} renueva sus tokens")
    public <T extends Actor> void performAs(T actor) {
        String refreshToken = conElRefreshTokenAnterior
                ? actor.recall("refreshTokenAnterior")
                : actor.recall("refreshToken");

        actor.attemptsTo(
                Post.to("/api/auth/refresh")
                        .with(req -> req.contentType(ContentType.JSON)
                                .body(Map.of("refreshToken", refreshToken)))
        );

        if (!conElRefreshTokenAnterior) {
            String accessTokenAnterior = actor.recall("accessToken");
            String refreshTokenAnterior = actor.recall("refreshToken");
            String nuevoAccessToken = SerenityRest.lastResponse().jsonPath().getString("accessToken");
            String nuevoRefreshToken = SerenityRest.lastResponse().jsonPath().getString("refreshToken");
            if (nuevoAccessToken != null) {
                actor.remember("accessTokenAnterior", accessTokenAnterior);
                actor.remember("refreshTokenAnterior", refreshTokenAnterior);
                actor.remember("accessToken", nuevoAccessToken);
                actor.remember("refreshToken", nuevoRefreshToken);
            }
        }
    }
}
