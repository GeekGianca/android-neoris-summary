---
## Android Neoris Financial Summary

[![Android CI/CD](https://github.com/GeekGianca/android-neoris-summary/actions/workflows/build.yaml/badge.svg)](https://github.com/GeekGianca/android-neoris-summary/actions/workflows/build.yaml)
  
# Aplicación Android modular construida con MVVM, Jetpack, y buenas prácticas SOLID.


## 📂 Módulos y su Propósito

### 📁 app/
Este es el módulo principal de Android. Contiene:

- **manifests/**: Archivo `AndroidManifest.xml` para declarar actividades, permisos, etc.
- **kotlin+java/**: Código fuente de la interfaz de usuario (UI), incluyendo `Activities`, `Fragments` y `ViewModels`.
- **res/**: Recursos de UI como layouts, strings, drawables, etc.

Este módulo es el punto de entrada y orquesta la interacción entre las capas de la arquitectura (View ↔ ViewModel ↔ UseCases).

---

#### 📁 data/
Contiene la implementación concreta de los repositorios, `Room`, `DataSources` (locales y remotos), y mapeadores de entidades.

> **Responsabilidad**: Acceder y manipular datos desde distintas fuentes (DB, API, etc.) y transformarlos al modelo de dominio.

#### 📁 domain/
Esta capa representa la lógica del negocio.

- Define los **casos de uso** (`UseCases`) como unidades aisladas de lógica.
- Define los **contratos (interfaces)** de los repositorios que serán implementados en `data/`.

> **Responsabilidad**: No depende de Android ni de ninguna librería externa. Es completamente testeable y reutilizable.

#### 📁 model/
Contiene las clases modelo utilizadas en la capa de dominio y presentación.

> **Responsabilidad**: Definir modelos puros (`data classes`) que pueden ser usados en cualquier capa sin lógica adicional.

#### 📁 network/
Encapsula toda la configuración y servicios de red (`Retrofit`, interceptores, DTOs).

> **Responsabilidad**: Manejar las comunicaciones con servicios externos mediante HTTP y mapear respuestas.

#### 📁 shared/
Contiene utilidades compartidas como extensiones (`extensions`), constantes, helpers, validadores, etc.

> **Responsabilidad**: Proveer herramientas genéricas reutilizables en toda la app.

---

## 📐 Principios Aplicados

### ✅ MVVM (Model-View-ViewModel)
Separación clara de responsabilidades:
- **Model**: Lógica de negocio y entidades (`domain/`, `data/`).
- **View**: UI (`Activities`, `Fragments` en `app/`).
- **ViewModel**: Intermediario entre UI y lógica de negocio.

---

### ✅ SOLID Principles

- **S - Single Responsibility**: Cada clase tiene una única responsabilidad (por ejemplo, los `UseCases` solo hacen una cosa).
- **O - Open/Closed**: Las clases están abiertas a extensión pero cerradas a modificación (uso de interfaces).
- **L - Liskov Substitution**: Se respetan contratos entre clases base e hijas (interfaces en `domain/`).
- **I - Interface Segregation**: Interfaces específicas por cada caso de uso, no interfaces monolíticas.
- **D - Dependency Inversion**: La capa de dominio depende de abstracciones, no implementaciones concretas.

---

## 💉 Inyección de Dependencias

- Dagger + Hilt.
- Se inyectan `Repositories`, `UseCases` y `ViewModels` en sus respectivas capas, facilitando el testeo unitario y desacoplamiento.

---

## 📌 Ventajas de esta Arquitectura

- Separación clara de responsabilidades
- Alta escalabilidad y mantenibilidad
- Alto grado de testeabilidad
- Reutilización de lógica en múltiples pantallas
- Independencia de frameworks (por parte de `domain/`)

---

## 🧪 Testing

- **Domain**: 100% testeable sin dependencia de Android.
- **ViewModel**: Testeable con `JUnit` + `Turbine` o `MockK`.
- **Data**: Testeable con fakes o mocks de `Room` y servicios.
- **Ejecutar:** SummaryBalanceTest en el modulo core:data "com.example.neoris.data.SummaryBalanceTest"

---

