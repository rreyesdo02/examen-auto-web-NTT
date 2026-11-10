@regresion @compras
Feature: Compras en QA Lab Store
  Como usuario de la aplicación
  Quiero seleccionar productos y agregarlos al carrito
  Para adquirir los productos de mi interés

  @happy-path @NTT-0003
  Scenario: Validar carrito en QA Lab Store
    Given que estoy en la pagina de login
    When ingreso correo "rreyesdo@emeal.nttdata.com" y clave "rreyes2002"
    Then debo ver al catalogo
    When navego a la categoría "Clothes" y subcategoría "Men"
    And agrego 2 unidades del primer producto al carrito
    Then valido en el popup la confirmación del producto agregado
    When finalizo la compra
    Then valido el título de la página del carrito
    And vuelvo a validar el cálculo de precios en el carrito

  @categoria_inexistente @NTT-0004
  Scenario: Intentar navegar a una categoría inexistente
    Given que estoy en la pagina de login
    When ingreso correo "rreyesdo@emeal.nttdata.com" y clave "rreyes2002"
    Then debo ver al catalogo
    When intento navegar a la categoría inexistente "Autos"
    And agrego 2 unidades del primer producto al carrito
    Then valido en el popup la confirmación del producto agregado
    When finalizo la compra
    Then valido el título de la página del carrito
    And vuelvo a validar el cálculo de precios en el carrito