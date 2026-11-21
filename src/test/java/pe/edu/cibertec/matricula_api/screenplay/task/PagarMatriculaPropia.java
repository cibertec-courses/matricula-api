package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

public class PagarMatriculaPropia implements Task {

    public static PagarMatriculaPropia sinIndicarMetodo() {
        return new PagarMatriculaPropia();
    }

    @Override
    @Step("{0} paga su matricula sin indicar metodo")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("accessToken");
        Long id = actor.recall("matriculaId");
        actor.attemptsTo(
                Patch.to("/api/matriculas/{id}/pagar")
                        .with(req -> req.header("Authorization", "Bearer " + token).pathParam("id", id))
        );
    }
}
