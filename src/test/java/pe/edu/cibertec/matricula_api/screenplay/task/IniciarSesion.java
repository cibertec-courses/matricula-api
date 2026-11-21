package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class IniciarSesion implements Task {

    private final String usuario;
    private final String clave;

    public IniciarSesion(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public static IniciarSesion conUsuario(String usuario, String clave) {
        return new IniciarSesion(usuario, clave);
    }

    @Override
    @Step("{0} inicia sesion con el usuario #usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/auth/login")
                        .with(req -> req.contentType(ContentType.JSON)
                                .body(Map.of("username", usuario, "password", clave)))
        );
        String accessToken = SerenityRest.lastResponse().jsonPath().getString("accessToken");
        String refreshToken = SerenityRest.lastResponse().jsonPath().getString("refreshToken");
        actor.remember("accessToken", accessToken);
        actor.remember("refreshToken", refreshToken);
        actor.remember("codigoAlumno", usuario);
    }
}
