package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import java.util.Map;

public class CalificarMatricula implements Task {

    private final double nota;

    public CalificarMatricula(double nota) {
        this.nota = nota;
    }

    public static CalificarMatricula conLaNota(double nota) {
        return new CalificarMatricula(nota);
    }

    @Override
    @Step("{0} registra la nota #nota en la matricula objetivo")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        Long idMatricula = actor.recall("idMatriculaObjetivo");
        actor.attemptsTo(
                Patch.to("/api/matriculas/{id}/nota")
                        .with(req -> req.header("Authorization", "Bearer " + token)
                                .pathParam("id", idMatricula)
                                .contentType(ContentType.JSON)
                                .body(Map.of("nota", nota)))
        );
    }
}
