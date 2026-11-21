package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.Map;

public class LosPagos {

    public static Question<Boolean> contienePagoDeLaMatricula(long idMatricula) {
        return Question.about("si la lista de pagos contiene el pago de la matricula " + idMatricula)
                .answeredBy(actor -> buscarPorIdMatricula(idMatricula) != null);
    }

    public static Question<String> metodoDelPagoDeLaMatricula(long idMatricula) {
        return Question.about("el metodo del pago de la matricula " + idMatricula)
                .answeredBy(actor -> {
                    Map<String, Object> pago = buscarPorIdMatricula(idMatricula);
                    return pago == null ? null : String.valueOf(pago.get("metodo"));
                });
    }

    public static Question<String> numeroDeOperacionDelPagoDeLaMatricula(long idMatricula) {
        return Question.about("el numero de operacion del pago de la matricula " + idMatricula)
                .answeredBy(actor -> {
                    Map<String, Object> pago = buscarPorIdMatricula(idMatricula);
                    return pago == null ? null : String.valueOf(pago.get("numeroOperacion"));
                });
    }

    private static Map<String, Object> buscarPorIdMatricula(long idMatricula) {
        List<Map<String, Object>> pagos = SerenityRest.lastResponse().jsonPath().getList("$");
        for (Map<String, Object> pago : pagos) {
            if (Long.valueOf(pago.get("idMatricula").toString()) == idMatricula) {
                return pago;
            }
        }
        return null;
    }
}
