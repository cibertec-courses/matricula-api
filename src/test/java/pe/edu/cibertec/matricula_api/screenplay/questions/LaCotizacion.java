package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public class LaCotizacion {

    public static Question<Integer> creditos() {
        return Question.about("los creditos de la cotizacion")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getInt("creditos"));
    }

    public static Question<Double> subtotal() {
        return Question.about("el subtotal de la cotizacion")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getDouble("subtotal"));
    }

    public static Question<Integer> descuento() {
        return Question.about("el descuento de la cotizacion")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getInt("descuento"));
    }

    public static Question<Double> total() {
        return Question.about("el total de la cotizacion")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getDouble("total"));
    }
}
