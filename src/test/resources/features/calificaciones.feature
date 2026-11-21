# language: es
Característica: Calificación de matrículas

  Antecedentes:
    Dado que Rosa inicia sesion con el usuario "i202610005" y la clave "Cibertec2026"
    Y Rosa obtiene el id de la matricula del curso "PI5620"

  Esquema del escenario: El docente registra la nota de la matricula pagada
    Dado que Docente inicia sesion con el usuario "D0001" y la clave "Cibertec2026"
    Y Docente usa la matricula que obtuvo Rosa
    Cuando Docente registra la nota <nota> en la matricula objetivo
    Entonces el codigo de respuesta es 200
    Cuando Rosa consulta su historial academico
    Entonces el codigo de respuesta es 200
    Y la respuesta cumple el esquema "historial.json"
    Y la condicion del curso "PI5620" del historial es "<condicion>"
    Y los creditos aprobados del historial son <creditosAprobados>
    Y el promedio ponderado del historial es <promedioPonderado>

    Ejemplos:
      | nota | condicion   | creditosAprobados | promedioPonderado |
      | 17   | APROBADO    | 13                 | 16,15              |
      | 12,5 | DESAPROBADO | 7                  | 14,08              |

  Escenario: Un docente no asignado al curso no puede calificar la matricula
    Dado que Docente2 inicia sesion con el usuario "D0002" y la clave "Cibertec2026"
    Y Docente2 usa la matricula que obtuvo Rosa
    Cuando Docente2 registra la nota 18 en la matricula objetivo
    Entonces el codigo de respuesta es 403
    Y el codigo de error es "ACCESO_DENEGADO"

  Escenario: No se puede calificar una matricula que no ha sido pagada
    Dado Rosa obtiene el id de la matricula del curso "PS5511"
    Y que Docente inicia sesion con el usuario "D0001" y la clave "Cibertec2026"
    Y Docente usa la matricula que obtuvo Rosa
    Cuando Docente registra la nota 15 en la matricula objetivo
    Entonces el codigo de respuesta es 409
    Y el codigo de error es "MATRICULA_NO_PAGADA"

  Escenario: Una nota escrita con punto decimal en vez de coma se interpreta mal y es rechazada
    Dado que Docente inicia sesion con el usuario "D0001" y la clave "Cibertec2026"
    Y Docente usa la matricula que obtuvo Rosa
    Cuando Docente registra la nota 12.5 en la matricula objetivo
    Entonces el codigo de respuesta es 400
    Y el codigo de error es "DATOS_INVALIDOS"
