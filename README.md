# Editor de Texto - Analizador Léxico y Sintáctico

Este proyecto es un entorno de desarrollo construido en Java que integra un editor de texto con herramientas de análisis léxico y sintáctico. Fue desarrollado como proyecto académico para la cátedra de Compiladores (C1113) en la Universidad Dr. Rafael Belloso Chacín (URBE).

## Características Principales
La aplicación permite gestionar múltiples archivos simultáneamente mediante un sistema de pestañas. Incluye opciones de edición clásicas. En cuanto al procesamiento de código, cuenta con un módulo de análisis léxico que identifica tokens, clasifica caracteres, aísla comentarios y lleva un conteo detallado de los elementos. Además, incorpora un módulo de análisis sintáctico capaz de leer la estructura del texto para validar su gramática.

## Tecnologías Utilizadas
El editor está desarrollado íntegramente en Java utilizando la biblioteca Swing para la interfaz gráfica. El núcleo del análisis de código funciona gracias a **JFlex** para la generación autómata del analizador léxico y **Java CUP** para la construcción del analizador sintáctico.

---

## Instrucciones de Ejecución

### Opción 1: Ejecutable
Para probar la aplicación sin necesidad de compilar el código fuente, puede utilizar el archivo ejecutable. Solo debe descargar el archivo `.exe` (o el `.jar` ejecutable) ubicado en la carpeta de distribución del repositorio y hacerle doble clic. 

> **Nota:** Es requisito indispensable tener instalado el entorno de ejecución de Java (JRE 1.8 o superior) en su sistema operativo.

### Opción 2: Desde el código fuente
El proyecto está configurado de forma nativa para **Eclipse IDE**, aunque el código fuente es compatible con otros entornos (como IntelliJ IDEA o NetBeans) si se configuran las dependencias manualmente. Para abrirlo y correrlo en Eclipse, siga estos pasos:

1. Clone o descargue este repositorio en su equipo local.
2. Abra Eclipse y diríjase al menú superior **File > Import...**
3. Despliegue la carpeta **General**, seleccione **Existing Projects into Workspace** y presione **Next**.
4. Haga clic en **Browse...**, seleccione la carpeta principal del proyecto que acaba de descargar y presione **Finish**.
5. Asegúrese de que Eclipse haya reconocido las librerías internas en el Build Path.
6. Diríjase a la carpeta `src`, busque la clase EditorVisual y ejecútela como una aplicación Java.

---

## Equipo de Desarrollo
Proyecto diseñado y desarrollado por **Edymar Chacin**.
