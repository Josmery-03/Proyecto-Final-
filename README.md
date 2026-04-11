# Space Invaders - Proyecto Final

## 1. Descripción del proyecto

Este proyecto consiste en la implementación de un juego tipo Space Invaders utilizando JavaFX, donde el jugador controla una nave en la parte inferior de la pantalla y debe eliminar oleadas de enemigos antes de que lo alcancen o pierda todas sus vidas.

El juego incluye sistema de disparos, enemigos dinámicos, niveles y aumento de dificultad progresivo.

## 2. Funcionalidades implementadas

* Estructura inicial del proyecto
* Pantalla de menú inicial
* Navegación del menú a la pantalla del juego
* Pantalla del juego implementada
* Implementación del game loop (AnimationTimer)
* Movimiento del jugador con teclado (izquierda/derecha)
* Disparo de proyectiles con la barra espaciadora
* Generación de enemigos en formación
* Movimiento de enemigos lateral con descenso progresivo
* Disparo aleatorio de enemigos
* Sistema de colisiones (balas vs enemigos y jugador)
* Sistema de vidas
* Sistema de puntaje
* Sistema de niveles (incremento de dificultad)
* Pantalla de Game Over
* Opción de reinicio del juego

## 3. Requisitos previos

* Java JDK 17 o superior
* JavaFX SDK
* IDE (Visual Studio Code, IntelliJ, NetBeans o Eclipse)

## 4. Cómo ejecutar el proyecto

* Abrir el proyecto en un IDE
* Configurar JavaFX (VM options):

```
--module-path "ruta/javafx/lib" --add-modules javafx.controls,javafx.fxml
```

* Ejecutar la clase Main

## 5. Estructura del proyecto

* app:

  * Main: clase principal que inicia la aplicación
  * MenuController: controlador del menú
  * GameApp: manejo de la escena del juego y lógica principal
  * Player: lógica del jugador
  * Enemy: lógica de los enemigos
  * Bullet: manejo de los disparos
  * GameLoop: bucle principal del juego

* resources:

  * imagenes utilizadas en el juego

## 6. Decisiones de diseño

Se decidió utilizar FXML para el menú inicial y código Java para la lógica del juego.

Además, se separaron las entidades principales (jugador, enemigos y disparos) en clases independientes para mejorar la organización del código y facilitar futuras mejoras.

Se implementó un game loop personalizado utilizando AnimationTimer para controlar la actualización continua del juego.

## 7. Autor

Josmery Bueno - 1000-4445
2026

