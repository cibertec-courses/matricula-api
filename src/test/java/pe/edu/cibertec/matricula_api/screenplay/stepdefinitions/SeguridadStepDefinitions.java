package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import pe.edu.cibertec.matricula_api.screenplay.questions.ElCodigoDeError;
import pe.edu.cibertec.matricula_api.screenplay.task.CerrarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarAlumnos;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarPerfil;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.RenovarToken;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SeguridadStepDefinitions {

    private final String baseUrl = EnvironmentSpecificConfiguration
            .from(SystemEnvironmentVariables.currentEnvironmentVariables())
            .getProperty("restapi.baseurl");

    @Given("que {word} inicia sesion de seguridad con el usuario {string} y la clave {string}")
    public void queIniciaSesionDeSeguridadConElUsuarioYLaClave(String nombre, String usuario, String clave) {
        Actor actor = theActorCalled(nombre).whoCan(CallAnApi.at(baseUrl));
        actor.attemptsTo(IniciarSesion.conUsuario(usuario, clave));
    }

    @When("{word} renueva sus tokens")
    public void renuevaSusTokens(String nombre) {
        theActorInTheSpotlight().attemptsTo(RenovarToken.conSuRefreshToken());
    }

    @When("{word} reutiliza el refresh token anterior")
    public void reutilizaElRefreshTokenAnterior(String nombre) {
        theActorInTheSpotlight().attemptsTo(RenovarToken.reutilizandoElRefreshTokenAnterior());
    }

    @When("{word} consulta su perfil")
    public void consultaSuPerfil(String nombre) {
        theActorInTheSpotlight().attemptsTo(ConsultarPerfil.conSuToken());
    }

    @When("{word} cierra su sesion")
    public void cierraSuSesion(String nombre) {
        theActorInTheSpotlight().attemptsTo(CerrarSesion.suSesion());
    }

    @When("{word} consulta la lista de alumnos")
    public void consultaLaListaDeAlumnos(String nombre) {
        theActorInTheSpotlight().attemptsTo(ConsultarAlumnos.todos());
    }

    @And("el access token nuevo es distinto al anterior")
    public void elAccessTokenNuevoEsDistintoAlAnterior() {
        String anterior = theActorInTheSpotlight().recall("accessTokenAnterior");
        String nuevo = theActorInTheSpotlight().recall("accessToken");
        assertThat(nuevo, org.hamcrest.Matchers.not(equalTo(anterior)));
    }

    @And("el refresh token nuevo es distinto al anterior")
    public void elRefreshTokenNuevoEsDistintoAlAnterior() {
        String anterior = theActorInTheSpotlight().recall("refreshTokenAnterior");
        String nuevo = theActorInTheSpotlight().recall("refreshToken");
        assertThat(nuevo, org.hamcrest.Matchers.not(equalTo(anterior)));
    }

    @When("{word} intenta iniciar sesion con el usuario {string} y la clave incorrecta estos intentos:")
    public void intentaIniciarSesionConLaClaveIncorrectaEstosIntentos(String nombre, String usuario, DataTable tabla) {
        Actor actor = theActorCalled(nombre).whoCan(CallAnApi.at(baseUrl));
        List<Map<String, String>> filas = tabla.asMaps(String.class, String.class);
        for (Map<String, String> fila : filas) {
            actor.attemptsTo(IniciarSesion.conUsuario(usuario, "ClaveIncorrecta123"));
            int statusEsperado = Integer.parseInt(fila.get("status"));
            String codigoEsperado = fila.get("codigo");
            assertThat(SerenityRest.lastResponse().statusCode(), equalTo(statusEsperado));
            assertThat(ElCodigoDeError.delUltimoIntento().answeredBy(actor), equalTo(codigoEsperado));
        }
    }

    @When("{word} intenta iniciar sesion con el usuario {string} y su clave correcta {string}")
    public void intentaIniciarSesionConSuClaveCorrecta(String nombre, String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.conUsuario(usuario, clave));
    }
}
