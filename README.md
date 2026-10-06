# 🛡️ ScanSafe — Escáner Inteligente de Toxicidad y Seguridad

[![Android](https://img.shields.io/badge/Platform-Android%2024%2B-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-purple.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-blue.svg)](https://developer.android.com/jetpack/compose)
[![Version](https://img.shields.io/badge/Version-1.0.0%20(Build%20100)-orange.svg)]()
[![License](https://img.shields.io/badge/License-MIT-teal.svg)]()

> **ScanSafe** es una aplicación móvil nativa para Android de inspección inmediata diseñada para responder a la pregunta fundamental: **¿Esto es dañino o no es dañino?**
>
> Escanea cualquier planta (mata), alimento, hongo, sustancia química de limpieza, cosmético o producto del hogar mediante la cámara en tiempo real, selector de fotografías o texto, y te entrega un informe toxicológico exhaustivo con niveles de peligro para personas y mascotas, síntomas clave y protocolos de primeros auxilios.

---

## 👨‍💻 Información del Desarrollador y Lanzamiento

* **Desarrollador Principal:** Luis Miguel & VigilSafe Labs
* **Organización:** VigilSafe Studios
* **Versión Oficial:** `1.0.0 (Build 100)`
* **Fecha de Lanzamiento:** 2026
* **Identificador de Aplicación (`applicationId`):** `com.aistudio.scansafe.tkvbr`

---

## ✨ Características Principales

### 1. 📷 Escáner Visual Multimodal (Cámara en Vivo & Galería)
- **CámaraX Integrada:** Visor de escaneo en vivo con retícula táctil animada y barrido de láser verde.
- **Detección Instantánea:** Reconocimiento de plantas, matas venenosas, hongos y envases.
- **Soporte Flash & Cambio de Cámara:** Control de linterna para entornos oscuros y cambio entre cámara frontal y trasera.
- **Selector de Fotos:** Analiza cualquier foto tomada previamente o captura de pantalla.
- **Muestras Rápidas:** Carrusel de acceso veloz a muestras comunes (*Mata de Adelfa, Lejía, Monstera, Sosa Cáustica, Amanita, Chocolate*).

### 2. ⚠️ Diagnóstico Completo: ¿Es dañino o no es dañino?
- **Cartel de Veredicto Enfadado:** Clasificación clara e inequívoca:
  - 🟢 **SEGURO / NO TÓXICO** (0 - 20 pts)
  - 🟡 **PRECAUCIÓN / MODERADO** (21 - 50 pts)
  - 🟠 **DAÑINO / TÓXICO** (51 - 80 pts)
  - 🔴 **PELIGRO CRÍTICO / VENENOSO** (81 - 100 pts)
- **Indicador Circular de Toxicidad (Gauge 0-100%):** Medición cuantitativa con transición animada.
- **Matriz de Riesgo:**
  - 👤 **Peligro en Humanos y Niños**
  - 🐾 **Peligro en Mascotas (Perros y Gatos)**
  - 🧪 **Compuestos Activos y Toxinas**
  - 🩺 **Síntomas de Intoxicación**
  - 🚑 **Primeros Auxilios Inmediatos** (con advertencia de *qué NO hacer*, ej. nunca provocar vómito con cáusticos).
  - 🛡️ **Consejos de Manipulación Segura**

### 3. 🔍 Catálogo Toxicológico Integrado
- Más de 20 especies de plantas/matas, productos químicos caseros, alimentos y cosméticos precargados para consulta instantánea sin conexión (*Offline-First*).
- Filtrado por categorías (*Plantas y Matas, Químicos del Hogar, Alimentos y Hongos, Cosméticos, Fauna*).
- Búsqueda en tiempo real por nombre común, nombre científico o toxina.

### 4. 🧪 Analizador de Fórmulas e Ingredientes
- Pega o escribe listas de ingredientes químicos de envases para detectar automáticamente parabenos, formaldehído, sosa cáustica, sulfatos o hipoclorito sódico.

### 5. 💾 Historial de Análisis Local (Room Database)
- Almacenamiento seguro en base de datos local SQLite con Room.
- Marcado de favoritos y función de vaciado de historial.

### 6. 🚨 Centro de Emergencias Toxicológicas 24h
- Acceso telefónico directo con un solo toque al Instituto Nacional de Toxicología (+34 91 562 04 20), Poison Help internacional (1-800-222-1222) y servicios de ambulancia (112 / 911).
- Guías interactivas paso a paso para quemaduras con lejía, ingesta de plantas venenosas y envenenamiento animal.

---

## 🎨 Diseño y Experiencia de Usuario (Anti "AI-Slop")

- **Botones con Rebote Háptico (`BouncyButton`):** Micro-interacciones con animación por resortes (`spring()`) que responden al tacto del usuario.
- **Sistema de Color Bio-Emerald & Obsidian:** Paleta intencional inspirada en laboratorios botánicos y bioseguridad (`#10B981`, `#0B130E`, `#EF4444`).
- **Respeto a Insets y Edge-to-Edge:** Totalmente compatible con barras de gestos modernas de Android (`enableEdgeToEdge()` y `WindowInsets.navigationBars`).
- **Iconografía Oficial Material 3:** Iconos claros y reconocibles.

---

## 🛠️ Arquitectura Técnica

```
com.example/
├── MainActivity.kt               # Contenedor raíz con barra de navegación M3
├── model/
│   └── ScanModels.kt             # Modelos de dominio (HazardLevel, ScanResultItem)
├── data/
│   ├── ScanKnowledgeBase.kt      # Enciclopedia botánica y química precargada
│   ├── SavedScanEntity.kt        # Entidad Room para SQLite
│   ├── ScanDao.kt                # Acceso a base de datos Room
│   └── ScanSafeDatabase.kt       # Base de datos local
├── service/
│   └── GeminiScanService.kt      # Análisis multimodal con Gemini AI + Fallback
├── ui/
│   ├── theme/                    # Colores y tipografía Material 3
│   ├── components/               # Botones animados, velocímetro de toxicidad, retícula
│   └── screens/
│       ├── ScanScreen.kt         # Visor CameraX en vivo y disparador animado
│       ├── DetailScreen.kt       # Reporte detallado de seguridad
│       ├── CatalogScreen.kt      # Buscador enciclopédico
│       ├── AnalyzerScreen.kt     # Evaluador de fórmulas químicas
│       ├── HistoryScreen.kt      # Historial persistente
│       └── EmergencyScreen.kt    # Líneas telefónicas y primeros auxilios
```

---

## 🚀 Compilación y Ejecución

1. Clonar el repositorio.
2. Abrir el proyecto en **Android Studio** (o AI Studio).
3. (Opcional) Configurar tu clave de API de Gemini en `.env`:
   ```bash
   GEMINI_API_KEY=tu_api_key_aqui
   ```
4. Ejecutar la tarea de Gradle:
   ```bash
   gradle assembleDebug
   ```

---

## 📄 Licencia

Este proyecto fue desarrollado por **Luis Miguel & VigilSafe Labs** bajo la licencia MIT.
