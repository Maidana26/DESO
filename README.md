Sistema de Gestión Hotelera

Descripción
  - Este proyecto implementa un sistema de gestión hotelera siguiendo los casos de uso del Trabajo Práctico de Diseño de Sistemas.

Se implementaron los siguientes casos de uso:

  - CU01: Autenticar usuario
  - CU02: Buscar huésped
  - CU09: Dar de alta huésped
  - CU10: Modificar huésped
  - CU11: Dar de baja huésped

El sistema permite gestionar huéspedes mediante un flujo de inicio de sesión, alta, baja y búsqueda desde consola.

Requisitos del sistema

  - Java 11 o superior

  - Consola de comandos para la interacción con el usuario

  - Archivos de datos precargados en formato CSV

Estructura del proyecto
  - app                     # Clase principal Main.java
  - dao                     # Data Access Objects
  - exceptions              # Excepciones utilizadas en el proyecto
  - models                  # Clases de dominio (DTOs)
  - service                 # Lógica de negocio y gestores
  - ui                      # Interfaces de usuario por consola
  - util                    # Clases extras 

Se adjuntará el diagrama de clases utilizado en el diseño del sistema.

Archivos de datos

  - Completar con la ruta de tus archivos CSV en el sistema:

  - Ruta a ListaHuespedes.csv: data/ListaHuespedes.csv → Huéspedes precargados

  - Ruta a Conserjes.csv: data/conserjes.csv → Usuarios habilitados para login

  - Mantener los archivos en estas rutas es obligatorio para que el programa funcione correctamente.

Documentación Javadoc

  - Completar con la ruta de tu documentación Javadoc generada:

  - Ruta a index.html: target/reports/apidocs/index.html → Documentación completa del proyecto

  - Se recomienda abrir desde un navegador para consultar las clases, métodos y diagramas.

Ejecución

Para ejecutar el sistema:

  1.	Compilar el proyecto con javac o desde un IDE compatible con Java.

  2. Ejecutar la clase principal:

	  - java app.Main

  3. Flujo de la aplicación:

	  1. Se solicita inicio de sesión mediante usuario y contraseña (usuarios en Conserjes.csv).

	  2. Si la autenticación es correcta, se muestra el menú principal:

		  - Dar de alta huésped

		  - Dar de baja huésped

		  - Buscar huésped

		  - Salir

	  3. El usuario puede repetir acciones hasta elegir salir del sistema.

Consideraciones de diseño

  - La lógica de negocio se encuentra en service y es independiente de la lógica de presentación (ui).

  - El acceso a datos se implementa mediante el patrón DAO, usando archivos CSV.

  - Se implementaron al menos dos excepciones propias (exceptions) para manejo de errores.

  - Se utilizan Streams y expresiones lambda en al menos un caso sobre colecciones.

  - Se aplicaron dos patrones de diseño adicionales (detallados en la documentación).

  - El proyecto respeta los diagramas de secuencia y clases definidos por el grupo.

Notas

  - Antes de ejecutar, asegurarse de que las rutas de los archivos CSV y Javadoc sean correctas.

  - Revisar la documentación Javadoc para entender la relación entre clases, métodos y gestores.
