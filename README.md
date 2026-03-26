# Editor de Texto - Analizador Léxico y Sintáctico

Este proyecto es un entorno de desarrollo construido en Java que integra un editor de texto con herramientas de análisis léxico y sintáctico. Fue desarrollado como proyecto académico para la cátedra de Compiladores (C1113) en la Universidad Dr. Rafael Belloso Chacín (URBE).

## Características Principales
La aplicación permite gestionar múltiples archivos simultáneamente mediante un sistema de pestañas. Incluye opciones de edición clásicas y personalización de la interfaz. En cuanto al procesamiento de código, cuenta con un módulo de análisis léxico que identifica tokens, clasifica caracteres, aísla comentarios y lleva un conteo detallado de los elementos. Además, incorpora un módulo de análisis sintáctico capaz de leer la estructura del texto para validar su gramática.

## Tecnologías Utilizadas
El editor está desarrollado íntegramente en Java utilizando la biblioteca Swing para la interfaz gráfica. El núcleo del análisis de código funciona gracias a JFlex para la generación autómata del analizador léxico y Java CUP para la construcción del analizador sintáctico.

## Instrucciones de Ejecución
Para probar la aplicación sin necesidad de compilar el código fuente, puede utilizar el archivo ejecutable. Solo debe descargar el archivo `.exe` (o el `.jar` ejecutable) ubicado en la carpeta de distribución del repositorio y hacerle doble clic. Es requisito indispensable tener instalado el entorno de ejecución de Java (JRE 1.8 o superior) en su sistema operativo.

## Equipo de Desarrollo
Proyecto diseñado y desarrollado por Edymar Chacin.