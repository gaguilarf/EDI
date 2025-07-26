# Ecosistema Digital Integrado (EDI) - UNSA 🎓✨

![Estado](https://img.shields.io/badge/estado-en%20desarrollo-green)
![Licencia](https://img.shields.io/badge/licencia-MIT-blue)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-blueviolet)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-0073CF)

> 🚀 Un Ecosistema Digital Inteligente diseñado para centralizar, optimizar y potenciar a la comunidad de la Universidad Nacional de San Agustín (UNSA).

EDI es una aplicación móvil nativa para Android, construida con tecnologías modernas para ser intuitiva y actuar como el sistema nervioso central de la vida universitaria, conectando talento y simplificando el acceso a la información.

## 📋 Tabla de Contenidos

1.  [Visión del Proyecto](#-visión-del-proyecto)
2.  [🌟 Características Principales](#-características-principales)
3.  [🛠️ Stack Tecnológico](#️-stack-tecnológico)
4.  [📱 Vistazo a la App](#-vistazo-a-la-app)
5.  [🚀 Puesta en Marcha (Instalación)](#-puesta-en-marcha-instalación)

## 🎯 Visión del Proyecto

El Ecosistema Digital Integrado (EDI) nace con el objetivo de transformar la interacción dentro de la UNSA. Buscamos romper los silos de información y las barreras para la colaboración, creando un entorno digital unificado donde cada miembro de la comunidad pueda encontrar oportunidades, información relevante y equipos para innovar.

## 🌟 Características Principales

EDI cumple una doble función esencial para dinamizar la vida universitaria:

### 🧠 **1. Hub de Información Institucional Inteligente**

Centraliza y procesa toda la comunicación oficial de la universidad para que nunca te pierdas de nada importante.

* **Scraping Automatizado:** Recopila noticias, comunicados y eventos de las fuentes oficiales de la UNSA en tiempo real.
* **Procesamiento de Lenguaje Natural (NLP):** Aplica algoritmos para **resumir** textos largos, **clasificar** el contenido por categorías (académico, cultural, deportivo) y **etiquetar** temas clave.
* **Búsqueda Semántica:** Encuentra lo que necesitas no solo por palabras clave, sino por el significado detrás de tu consulta.

### 🤝 **2. Motor de Conexión de Talentos**

Fomenta la colaboración y la creación de proyectos multidisciplinarios, convirtiendo ideas en realidad.

* **Perfiles de Usuario Enriquecidos:** Cada usuario puede detallar sus habilidades, intereses, proyectos pasados y áreas de conocimiento.
* **Algoritmo de Matching Avanzado:** Sugiere perfiles complementarios para formar equipos de proyectos, startups o grupos de estudio.
* **Espacios de Proyecto:** Permite crear y gestionar equipos, asignar tareas y comunicarse dentro de la plataforma.

## 🛠️ Stack Tecnológico

Este proyecto está construido 100% en **Kotlin** y aprovecha el ecosistema moderno de desarrollo de Android. El backend para el scraping y NLP se gestiona por separado.

* **Lenguaje Principal:** Kotlin
* **Interfaz de Usuario (UI):**
  * `Jetpack Compose`: Framework declarativo para construir la UI nativa.
  * `Material 3`: Para implementar los componentes de diseño de Material Design.
  * `Navigation Compose`: Para gestionar la navegación entre pantallas.
* **Manejo de Red (Networking):**
  * `Retrofit`: Cliente HTTP para consumir APIs RESTful de manera segura.
  * `Gson`: Para la serialización y deserialización de objetos JSON.
* **Asincronía:**
  * `Kotlin Coroutines`: Para manejar operaciones en segundo plano de forma eficiente y no bloquear la UI.
* **Inyección de Dependencias:**
  * `Hilt`: Para simplificar la inyección de dependencias en toda la aplicación, integrado con Jetpack.
* **Arquitectura y Ciclo de Vida:**
  * `ViewModel`: Para almacenar y gestionar datos relacionados con la UI de forma consciente del ciclo de vida.
  * `Lifecycle KTX`: Extensiones para manejar el ciclo de vida de los componentes.
* **Testing:**
  * `JUnit`: Para pruebas unitarias.
  * `Espresso`: Para pruebas de interfaz de usuario (UI tests).

## 📱 Vistazo a la App

Un vistazo a las pantallas clave que componen la experiencia del usuario en EDI.

| Splash & Login | Conexión & Noticias |
| :---: | :---: |
| ![Pantalla de Splash](https://raw.githubusercontent.com/gaguilarf/EDI/master/assets/splash.jpg) | ![Pantalla de Conexión de Talentos](https://raw.githubusercontent.com/gaguilarf/EDI/master/assets/conecta.jpg) |
| **Splash e Inicio de Sesión** | **Conexión de Talentos y Noticias** |
| ![Pantalla de Login](https://raw.githubusercontent.com/gaguilarf/EDI/master/assets/login.jpg) | ![Pantalla de Noticias](https://raw.githubusercontent.com/gaguilarf/EDI/master/assets/noticias.jpg) |

## 🚀 Puesta en Marcha (Instalación)

Para obtener una copia del proyecto, simplemente clona este repositorio en tu máquina local usando Git.

```bash
# Clona el repositorio
git clone https://github.com/gaguilarf/EDI.git
