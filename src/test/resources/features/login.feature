@regresion @login
Feature: Autenticacion en QA Lab Store
  Como usuario de la aplicación
  Quiero iniciar sesión
  Para acceder a la lista de productos

  @happy @NTT-0001
  Scenario: Login con credenciales validas
    Given que estoy en la pagina de login
    When ingreso correo "rreyesdo@emeal.nttdata.com" y clave "rreyes2002"
    Then debo ver al catalogo

  @unhappy @NTT-0002
  Scenario Outline: Rechazar credenciales invalidas
    Given que estoy en la pagina de login
    When ingreso correo "<usuario>" y clave "<clave>"
    Then debo ver un error que contiene "<mensaje>"

    Examples:
      | usuario            | clave      | mensaje                 |
      | rreyes@nttdata.com | rreyes2002 | Error de autenticación. |
      | rreyesdo@nttdata   | wrong_pass | Formato no válido.      |