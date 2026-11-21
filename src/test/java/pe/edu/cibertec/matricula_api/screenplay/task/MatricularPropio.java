package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class MatricularPropio implements Task {

    private final String curso;

    public MatricularPropio(String curso) {
        this.curso = curso;
    }

    public static MatricularPropio enElCurso(String curso) {
        return new MatricularPropio(curso);
    }

    @Override
    @Step("{0} se matricula en el curso #curso")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        String codigoAlumno = actor.recall("codigoAlumno");
        actor.attemptsTo(
                Post.to("/api/matriculas")
                        .with(req -> req.header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(Map.of("codigoAlumno", codigoAlumno, "codigoCurso", curso)))
        );
        Object id = SerenityRest.lastResponse().jsonPath().get("id");
        if (id != null) {
            actor.remember("matriculaId", Long.valueOf(id.toString()));
        }
    }
}
