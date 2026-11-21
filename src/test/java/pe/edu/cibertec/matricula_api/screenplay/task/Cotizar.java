package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.List;
import java.util.Map;

public class Cotizar implements Task {

    private final List<String> cursos;

    public Cotizar(List<String> cursos) {
        this.cursos = cursos;
    }

    public static Cotizar losCursos(List<String> cursos) {
        return new Cotizar(cursos);
    }

    public static Cotizar elCurso(String curso) {
        return new Cotizar(List.of(curso));
    }

    @Override
    @Step("{0} cotiza los cursos #cursos")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        String codigoAlumno = actor.recall("codigoAlumno");
        actor.attemptsTo(
                Post.to("/api/matriculas/cotizacion")
                        .with(req -> req.header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(Map.of("codigoAlumno", codigoAlumno, "cursos", cursos)))
        );
    }
}
