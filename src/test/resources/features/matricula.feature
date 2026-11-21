# language: es
# Examen T1 - Pregunta 1 (6 puntos)
# Alumno D=7: i202610006 (Pedro Salas) | Curso D=7: BD5410 | Pago sin cuerpo | Debe fallar: matricularse en IA5513
# IMPORTANTE: ejecutar con la API recien reiniciada (datos frescos de data.sql); el Escenario de la regla
# que debe fallar se apoya en el estado que deja el escenario de "Matricularse y pagar".
Característica: Matrícula del alumno

  Antecedentes:
    Dado que Pedro inicia sesion con el usuario "i202610006" y la clave "Cibertec2026"

  Escenario: Cotizar el curso
    Cuando Pedro cotiza el curso "BD5410"
    Entonces el codigo de respuesta es 200
    Y los creditos de la cotizacion son 4
    Y el subtotal de la cotizacion es 480,0
    Y el descuento de la cotizacion es 0
    Y el total de la cotizacion es 480,0

  Escenario: Matricularse y pagar el curso
    Cuando Pedro se matricula en el curso "BD5410"
    Entonces el codigo de respuesta es 201
    Y el estado de la matricula es "REGISTRADA"
    Y el monto de la matricula es 480,0
    Y la respuesta cumple el esquema "matricula.json"
    Cuando Pedro paga su matricula sin indicar metodo de pago
    Entonces el codigo de respuesta es 200
    Y el estado de la matricula es "PAGADA"
    Y la matricula tiene fecha de pago
    Cuando Pedro consulta sus pagos
    Entonces el codigo de respuesta es 200
    Y el pago de la matricula aparece en la lista de pagos
    Y el metodo de pago registrado por defecto es "EFECTIVO"
    Y el numero de operacion del pago tiene el formato "OP-[0-9]{6}"

  Escenario: Regla de negocio que debe fallar
    Cuando Pedro intenta matricularse en el curso "IA5513"
    Entonces el codigo de respuesta es 409
    Y el codigo de error es "SIN_VACANTES"
    Y la respuesta cumple el esquema "error.json"
