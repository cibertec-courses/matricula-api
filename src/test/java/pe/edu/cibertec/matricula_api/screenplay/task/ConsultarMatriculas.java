package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.util.List;
import java.util.Map;

public class ConsultarMatriculas implements Task {

    private final String curso;

    public ConsultarMatriculas(String curso) {
        this.curso = curso;
    }

    public static ConsultarMatriculas delCurso(String curso) {
        return new ConsultarMatriculas(curso);
    }

    @Override
    @Step("{0} consulta sus matriculas del curso #curso")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        String codigoAlumno = actor.recall("codigoAlumno");
        actor.attemptsTo(
                Get.resource("/api/matriculas")
                        .with(req -> req.header("Authorization", "Bearer " + token)
                                .queryParam("alumno", codigoAlumno)
                                .queryParam("curso", curso))
        );
        List<Map<String, Object>> resultados = SerenityRest.lastResponse().jsonPath().getList("$");
        if (!resultados.isEmpty()) {
            Object id = resultados.get(0).get("id");
            actor.remember("idMatriculaObjetivo", Long.valueOf(id.toString()));
        }
    }
}
