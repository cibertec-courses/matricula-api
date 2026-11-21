package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ConsultarAlumnos implements Task {

    public static ConsultarAlumnos todos() {
        return new ConsultarAlumnos();
    }

    @Override
    @Step("{0} consulta la lista de alumnos")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        actor.attemptsTo(
                Get.resource("/api/alumnos")
                        .with(req -> req.header("Authorization", "Bearer " + token))
        );
    }
}
