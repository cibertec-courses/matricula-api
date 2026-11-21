package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pe.edu.cibertec.matricula_api.screenplay.questions.ElHistorial;
import pe.edu.cibertec.matricula_api.screenplay.task.CalificarMatricula;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarHistorial;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarMatriculas;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CalificacionStepDefinitions {

    @And("{word} obtiene el id de la matricula del curso {string}")
    public void obtieneElIdDeLaMatriculaDelCurso(String nombre, String curso) {
        theActorCalled(nombre).attemptsTo(ConsultarMatriculas.delCurso(curso));
    }

    @And("{word} usa la matricula que obtuvo {word}")
    public void usaLaMatriculaQueObtuvo(String destino, String origen) {
        Long id = theActorCalled(origen).recall("idMatriculaObjetivo");
        theActorCalled(destino).remember("idMatriculaObjetivo", id);
    }

    @When("{word} registra la nota {double} en la matricula objetivo")
    public void registraLaNotaEnLaMatriculaObjetivo(String nombre, double nota) {
        theActorCalled(nombre).attemptsTo(CalificarMatricula.conLaNota(nota));
    }

    @When("{word} consulta su historial academico")
    public void consultaSuHistorialAcademico(String nombre) {
        theActorCalled(nombre).attemptsTo(ConsultarHistorial.propio());
    }

    @And("la condicion del curso {string} del historial es {string}")
    public void laCondicionDelCursoDelHistorialEs(String codigoCurso, String condicion) {
        theActorCalled("Rosa").should(seeThat(ElHistorial.condicionDelCurso(codigoCurso), equalTo(condicion)));
    }

    @And("los creditos aprobados del historial son {int}")
    public void losCreditosAprobadosDelHistorialSon(int creditos) {
        theActorCalled("Rosa").should(seeThat(ElHistorial.creditosAprobados(), equalTo(creditos)));
    }

    @And("el promedio ponderado del historial es {double}")
    public void elPromedioPonderadoDelHistorialEs(double promedio) {
        assertThat(ElHistorial.promedioPonderado().answeredBy(theActorCalled("Rosa")), is(closeTo(promedio, 0.01)));
    }
}
