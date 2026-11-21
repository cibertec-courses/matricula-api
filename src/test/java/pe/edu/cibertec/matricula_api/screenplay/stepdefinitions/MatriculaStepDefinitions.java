package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import pe.edu.cibertec.matricula_api.screenplay.questions.ElMonto;
import pe.edu.cibertec.matricula_api.screenplay.questions.LaCotizacion;
import pe.edu.cibertec.matricula_api.screenplay.questions.LaRespuesta;
import pe.edu.cibertec.matricula_api.screenplay.questions.LosPagos;
import pe.edu.cibertec.matricula_api.screenplay.task.Cotizar;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.MatricularPropio;
import pe.edu.cibertec.matricula_api.screenplay.task.PagarMatriculaPropia;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarPagos;

import java.util.regex.Pattern;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MatriculaStepDefinitions {

    private final String baseUrl = EnvironmentSpecificConfiguration
            .from(SystemEnvironmentVariables.currentEnvironmentVariables())
            .getProperty("restapi.baseurl");

    @Given("que {word} inicia sesion con el usuario {string} y la clave {string}")
    public void queInicaSesionConElUsuarioYLaClave(String nombre, String usuario, String clave) {
        Actor actor = theActorCalled(nombre).whoCan(CallAnApi.at(baseUrl));
        actor.attemptsTo(IniciarSesion.conUsuario(usuario, clave));
    }

    @When("{word} cotiza el curso {string}")
    public void cotizaElCurso(String nombre, String curso) {
        theActorInTheSpotlight().attemptsTo(Cotizar.elCurso(curso));
    }

    @When("{word} se matricula en el curso {string}")
    public void seMatriculaEnElCurso(String nombre, String curso) {
        theActorInTheSpotlight().attemptsTo(MatricularPropio.enElCurso(curso));
    }

    @When("{word} intenta matricularse en el curso {string}")
    public void intentaMatricularseEnElCurso(String nombre, String curso) {
        theActorInTheSpotlight().attemptsTo(MatricularPropio.enElCurso(curso));
    }

    @When("{word} paga su matricula sin indicar metodo de pago")
    public void pagaSuMatriculaSinIndicarMetodoDePago(String nombre) {
        theActorInTheSpotlight().attemptsTo(PagarMatriculaPropia.sinIndicarMetodo());
    }

    @When("{word} consulta sus pagos")
    public void consultaSusPagos(String nombre) {
        theActorInTheSpotlight().attemptsTo(ConsultarPagos.propios());
    }

    @And("los creditos de la cotizacion son {int}")
    public void losCreditosDeLaCotizacionSon(int creditos) {
        theActorInTheSpotlight().should(seeThat(LaCotizacion.creditos(), equalTo(creditos)));
    }

    @And("el subtotal de la cotizacion es {double}")
    public void elSubtotalDeLaCotizacionEs(double subtotal) {
        assertThat(LaCotizacion.subtotal().answeredBy(theActorInTheSpotlight()), is(closeTo(subtotal, 0.01)));
    }

    @And("el descuento de la cotizacion es {int}")
    public void elDescuentoDeLaCotizacionEs(int descuento) {
        theActorInTheSpotlight().should(seeThat(LaCotizacion.descuento(), equalTo(descuento)));
    }

    @And("el total de la cotizacion es {double}")
    public void elTotalDeLaCotizacionEs(double total) {
        assertThat(LaCotizacion.total().answeredBy(theActorInTheSpotlight()), is(closeTo(total, 0.01)));
    }

    @And("el monto de la matricula es {double}")
    public void elMontoDeLaMatriculaEsDouble(double monto) {
        assertThat(ElMonto.deLamaTricula().answeredBy(theActorInTheSpotlight()), is(closeTo(monto, 0.01)));
    }

    @And("el estado de la matricula es {string}")
    public void elEstadoDeLaMatriculaEs(String estado) {
        theActorInTheSpotlight().should(seeThat(LaRespuesta.campo("estado"), equalTo(estado)));
    }

    @And("la matricula tiene fecha de pago")
    public void laMatriculaTieneFechaDePago() {
        theActorInTheSpotlight().should(seeThat(LaRespuesta.campo("fechaPago"), notNullValue()));
    }

    @And("el metodo de pago registrado por defecto es {string}")
    public void elMetodoDePagoRegistradoPorDefectoEs(String metodo) {
        Long idMatricula = theActorInTheSpotlight().recall("matriculaId");
        assertThat(LosPagos.metodoDelPagoDeLaMatricula(idMatricula).answeredBy(theActorInTheSpotlight()),
                equalTo(metodo));
    }

    @And("el pago de la matricula aparece en la lista de pagos")
    public void elPagoDeLaMatriculaApareceEnLaListaDePagos() {
        Long idMatricula = theActorInTheSpotlight().recall("matriculaId");
        assertThat(LosPagos.contienePagoDeLaMatricula(idMatricula).answeredBy(theActorInTheSpotlight()), is(true));
    }

    @And("el numero de operacion del pago tiene el formato {string}")
    public void elNumeroDeOperacionDelPagoTieneElFormato(String patron) {
        Long idMatricula = theActorInTheSpotlight().recall("matriculaId");
        String numeroOperacion = LosPagos.numeroDeOperacionDelPagoDeLaMatricula(idMatricula)
                .answeredBy(theActorInTheSpotlight());
        assertThat(Pattern.matches(patron, numeroOperacion), is(true));
    }
}
