# HotelManager — Sistema de Gestión de Reservas Hoteleras

Aplicación de escritorio desarrollada en **Java** y **JavaFX** bajo la arquitectura **MVC (Modelo - Vista - Controlador)**, implementando 5 Estructuras de Datos Lineales (TAD) propias sin librerías de `java.util` y persistencia en archivos `.txt`.

**Integrantes:**
* Fernando Rojas
* Luis Cerón
* Juan Bonilla
* Juan Cadavid

---

## 1. Requisitos del Sistema (Qué debes tener instalado)

1. **Java Development Kit (JDK):** JDK 26 (o JDK 21 en adelante).
2. **JavaFX SDK:** Versión **25.0.5** (descargado desde Gluon).
   * *Ruta recomendada:* Descomprimirlo directamente en `C:\javafx-sdk-25.0.5` para que coincida con la configuración por defecto del proyecto.
3. **IDE:** **Apache NetBeans** (proyecto configurado como *Java with Ant*).
4. **Gluon Scene Builder** *(Opcional)*: Necesario únicamente si deseas abrir y editar visualmente los archivos `.fxml` de la carpeta `src/vista/`.

---

## 2. Instrucciones de Instalación y Configuración

1. **Descargar el proyecto:**
   Clona este repositorio o descárgalo como `.zip` y descomprímelo en tu computador.

2. **Abrir en NetBeans:**
   Abre **Apache NetBeans**, ve a **File -> Open Project...**, busca la carpeta `HotelManager` y ábrela.

3. **Configurar la ruta de JavaFX (solo si NetBeans marca error o tienes JavaFX en otra carpeta):**
   * Haz clic derecho sobre el proyecto **HotelManager** -> **Properties**.
   * **En Libraries -> Modulepath:** Elimina los archivos `.jar` que aparezcan con advertencia y haz clic en **+** para agregar los `.jar` ubicados dentro de tu carpeta `javafx-sdk-25.0.5\lib`.
   * **En Run -> VM Options:** Asegúrate de que esté exactamente la siguiente línea (ajustando la ruta entre comillas si guardaste JavaFX en otra ubicación):
     ```text
     --module-path "C:\javafx-sdk-25.0.5\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics
     ```
   * Haz clic en **OK**.

---

## 3. Cómo Ejecutar el Programa

1. Verifica que en la carpeta raíz del proyecto existan los archivos `habitaciones.txt`, `reservas.txt` y la carpeta `imagenes/` (`logo.png`, `img1.jpg`, `img2.jpg`, `img3.jpg`, etc.).
2. En NetBeans, haz clic en el botón verde **Run Project** (o presiona la tecla **F6**).
   * *Alternativa:* Haz clic derecho sobre `src/hotelmanager/HotelManager.java` -> **Run File**.

---

## 4. Arquitectura del Proyecto y Estructuras de Datos (TAD)

* **`modelo`:** Entidades (`Habitacion`, `Reserva`, `PersonalLimpieza`) y los 5 TAD propios implementados desde cero:
  * **Lista Doblemente Enlazada (`ListaDobleHabitaciones`):** Catálogo bidireccional de habitaciones y control de Check-Out.
  * **Cola FIFO (`ColaRecepcion`):** Atención de solicitudes de reserva en estricto orden de llegada.
  * **Pila LIFO (`PilaCancelaciones`):** Historial de solicitudes rechazadas con opción de reincorporar la última (*Deshacer*).
  * **Lista Simplemente Enlazada con Nodo Cabecera (`ListaSimpleHistorial`):** Registro consolidado de reservas confirmadas y cálculo de facturación.
  * **Lista Circular (`ListaCircularLimpieza`):** Rotación continua de turnos del personal de limpieza y mantenimiento.
* **`persistencia`:** Clase `GestorArchivos` para lectura y escritura en `habitaciones.txt`, `reservas.txt` y exportación de `reporte_facturas.txt`.
* **`vista`:** Interfaces gráficas diseñadas en FXML (`Mainview.fxml`, `RecepcionView.fxml`, `FacturacionView.fxml`, `CancelacionesView.fxml`, `LimpiezaView.fxml`).
* **`controlador`:** Controladores JavaFX que conectan los eventos de la interfaz con las operaciones de cada TAD.