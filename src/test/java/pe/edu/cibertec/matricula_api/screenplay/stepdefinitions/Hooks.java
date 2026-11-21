package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    private static final String ESTUDIANTE = "Alejandro Horna Quezada";
    private static final String CODIGO = "I202502617";
    private static final ZoneId LIMA = ZoneId.of("America/Lima");
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Before
    public void beforeScenario(Scenario scenario) {
        OnStage.setTheStage(new OnlineCast());

        ZonedDateTime ahora = ZonedDateTime.now(LIMA);

        System.out.println("===============================================");
        System.out.println("       EVALUACIÓN T1 - PRUEBAS DE SOFTWARE");
        System.out.println("===============================================");
        System.out.println("Estudiante : " + ESTUDIANTE);
        System.out.println("Código     : " + CODIGO);
        System.out.println("Fecha      : " + ahora.format(FECHA));
        System.out.println("Hora       : " + ahora.format(HORA));
        System.out.println("Escenario  : " + scenario.getName());
        System.out.println("===============================================");
    }
}
