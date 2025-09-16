# Memorama Kotlin

¡Bienvenido a **Memorama Kotlin**! Este repositorio contiene un juego de memoria (Memorama) desarrollado en Kotlin para Android. Aquí encontrarás una estructura de proyecto bien organizada y recursos gráficos listos para usar.

---

## Arquitectura del Proyecto

El proyecto sigue una arquitectura modular y clara, pensada para facilitar el mantenimiento, la escalabilidad y la comprensión del código. Las principales capas y componentes son:

- **UI (Interfaz de Usuario):** Incluye las actividades, fragmentos y layouts XML responsables de la interacción con el usuario.
- **Lógica de Negocio:** Incluye las clases que gestionan las reglas del juego, el manejo de puntajes y la lógica de emparejamiento de cartas.
- **Datos:** Incluye recursos estáticos como imágenes, textos y configuraciones, así como la persistencia de datos si aplica.

La organización de carpetas facilita la navegación y la localización rápida de cualquier componente del juego.

---

## Enfoque en la Carpeta `drawable`

La carpeta [`drawable`](./app/src/main/res/drawable) es uno de los componentes más importantes del proyecto. Aquí se almacenan todos los recursos gráficos utilizados en el juego, como:

- Imágenes de las cartas del Memorama (anverso y reverso)
- Fondos personalizados
- Iconos y botones gráficos
- Otros recursos visuales necesarios para la experiencia de usuario

**¿Por qué es importante la carpeta drawable?**
- Permite personalizar el aspecto del juego fácilmente, solo reemplazando o agregando imágenes.
- Facilita la reutilización de recursos gráficos en diferentes partes del proyecto.
- Todos los recursos en `drawable` están optimizados para su uso en dispositivos Android.

---

## ¿Cómo empezar?

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/SmithAlon/memorama_kotlin.git
   ```
2. **Abre el proyecto en Android Studio.**
3. **Ejecuta la aplicación** en un emulador o dispositivo físico.

---

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas agregar nuevas imágenes al juego, simplemente añádelas a la carpeta `drawable` y actualiza la lógica correspondiente. Si quieres mejorar la arquitectura, abre un Pull Request y describe tus cambios.

---

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más información.

---

**Desarrollado por [SmithAlon](https://github.com/SmithAlon)**
