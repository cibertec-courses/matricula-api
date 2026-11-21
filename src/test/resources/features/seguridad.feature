# language: es
# Examen T1 - Pregunta 2 (6 puntos)
# Seguridad con Serenity Screenplay: rotacion de refresh token, logout, bloqueo de cuenta, acceso denegado.
# IMPORTANTE: ejecutar con la API recien reiniciada (datos frescos de data.sql); el alumno i202610004
# no debe tener intentos fallidos previos o el Escenario 3 fallara.
Característica: Seguridad de la API de matricula

  Escenario: Rotacion del refresh token
    Dado que Pedro inicia sesion de seguridad con el usuario "i202610006" y la clave "Cibertec2026"
    Cuando Pedro renueva sus tokens
    Entonces el codigo de respuesta es 200
    Y el access token nuevo es distinto al anterior
    Y el refresh token nuevo es distinto al anterior
    Cuando Pedro reutiliza el refresh token anterior
    Entonces el codigo de respuesta es 401
    Y el codigo de error es "REFRESH_REUTILIZADO"
    Cuando Pedro consulta su perfil
    Entonces el codigo de respuesta es 401
    Y el codigo de error es "TOKEN_INVALIDO"

  Escenario: Cierre de sesion
    Dado que Pedro inicia sesion de seguridad con el usuario "i202610006" y la clave "Cibertec2026"
    Cuando Pedro cierra su sesion
    Entonces el codigo de respuesta es 204
    Cuando Pedro consulta su perfil
    Entonces el codigo de respuesta es 401
    Y el codigo de error es "TOKEN_INVALIDO"

  Escenario: Bloqueo de cuenta por intentos fallidos
    Cuando Jorge intenta iniciar sesion con el usuario "i202610004" y la clave incorrecta estos intentos:
      | intento | status | codigo                 |
      | 1       | 401    | CREDENCIALES_INVALIDAS |
      | 2       | 401    | CREDENCIALES_INVALIDAS |
      | 3       | 401    | CREDENCIALES_INVALIDAS |
      | 4       | 401    | CREDENCIALES_INVALIDAS |
      | 5       | 423    | CUENTA_BLOQUEADA       |
    Cuando Jorge intenta iniciar sesion con el usuario "i202610004" y su clave correcta "Cibertec2026"
    Entonces el codigo de respuesta es 423
    Y el codigo de error es "CUENTA_BLOQUEADA"

  Escenario: Alumno sin permisos de administrador
    Dado que Pedro inicia sesion de seguridad con el usuario "i202610006" y la clave "Cibertec2026"
    Cuando Pedro consulta la lista de alumnos
    Entonces el codigo de respuesta es 403
    Y el codigo de error es "ACCESO_DENEGADO"
    Y la respuesta cumple el esquema "error.json"
