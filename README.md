# **Tarea 03 - Juan Manuel Muñoz Moreno**  
## **Pokédex**  

### **Introducción**  
Aplicación que simula el funcionamiento de una Pokédex. Nos muestra una lista de 51 Pokémon (pudiendo aumentarse). Permite capturar Pokémon haciendo clic en ellos y ver sus estadísticas.  

- [Características principales](#características-principales)  
- [Tecnologías utilizadas](#tecnologías-utilizadas)  
- [Instrucciones de uso](#instrucciones-de-uso)  
- [Conclusiones del desarrollador](#conclusiones-del-desarrollador)  

---

### **Características principales**  

#### **Pokedex**  
Fragmento donde se muestra una cuadrícula con todos los Pokémon disponibles para capturar junto a su nombre.  
Nos permite capturarlos al hacer clic en alguno de ellos, pasando directamente a "Mis Pokémon", que funcionaría más o menos como el "PC de Bill" de los videojuegos de Pokémon.  

![image](https://github.com/user-attachments/assets/7ea5eae0-a8e0-41b4-84d4-016b22235238)  

#### **Mis Pokémon**  
Fragmento que muestra una lista lineal con todos los Pokémon que poseemos actualmente.  
- Muestra el nombre y los tipos del Pokémon.  
- Incluye un botón que permite liberarlos, siempre que la opción esté habilitada en **"Ajustes"**.  
- Al hacer clic en alguno de nuestros Pokémon, podemos ver sus detalles tal y como se verían en una Pokédex "original".  

![image](https://github.com/user-attachments/assets/ac6c69dd-631f-4650-bc6f-587f4f4343bd)  ![image](https://github.com/user-attachments/assets/967e129c-c974-4cc3-b92e-15d6f6c4d88c)


#### **Ajustes**  
Fragmento donde podemos configurar la aplicación:  
- **Idiomas:** Disponemos de dos (Inglés y Español).  
- **Switch:** Permite habilitar o deshabilitar la opción de liberar los Pokémon capturados.  
- **Acerca de:** Muestra la versión de la app y el nombre del desarrollador.  
- **Cerrar sesión:** Permite al usuario cerrar sesión de forma segura.  

![image](https://github.com/user-attachments/assets/502713df-3043-44b5-872b-560bd6397541)  

#### **Traductor**  
Esto no es una funcionalidad principal, sino más bien una clase que se implementó para solucionar un problema relacionado con la traducción de los tipos de Pokémon al español.  
- Utilizando **Retrofit**, se hicieron llamadas a la API de Google Translate para traducir cadenas de palabras.  
- Se creó esta clase como apoyo adicional.  

---

### **Tecnologías utilizadas**  
- `Firebase`  
- `Retrofit2`  
- `RecyclerView`  
- `translate.googleapis.com/`  
- `CardView`  
- `Glide`  

---

## **Instrucciones de uso**  

### **Notas importantes:**  
Este proyecto está desarrollado en **JAVA**, por lo que es fundamental tener esto en cuenta al descargarlo e importarlo en Android Studio.  

Para el correcto funcionamiento, es necesario instalar las siguientes dependencias en formato **Gradle**:  

#### **Cargar imágenes (Glide):**  
- `implementation ("com.github.bumptech.glide:glide:4.16.0");`  

#### **Analytics:**  
- `implementation("com.google.firebase:firebase-analytics")`  

#### **Gestión de login (Firebase):**  
- `implementation("com.google.firebase:firebase-auth:23.1.0")`  
- `implementation ("com.firebaseui:firebase-ui-auth:7.2.0")`  

#### **Gestión de colecciones (Firestore):**  
- `implementation("com.google.firebase:firebase-firestore")`  

#### **Llamadas a las APIs (pokeapi y Google Translate):**  
- `implementation ("com.squareup.retrofit2:retrofit:2.9.0")`  
- `implementation ("com.squareup.retrofit2:converter-gson:2.9.0")`  

---

### **Conclusiones del desarrollador**  
Ha sido un arduo camino el de programar esta aplicación. Hay que tener en cuenta muchos aspectos y, al mismo tiempo, simplificar los procesos. En ocasiones, tuve que volver al principio para avanzar y aclarar algunos apartados.  

Encuentro muy interesante el funcionamiento de Android Studio y las facilidades que ofrece para programar. Sin embargo, el desarrollo de la tarea en general me resultó difícil. Algunas partes, aunque tenía claro cómo iban, acababan fallando.  

Siempre hay algo que no está del todo bien, pero creo que con tiempo y práctica puedo optimizar el código mucho más. Seguramente podría hacer una única clase para los Pokémon, en lugar de dividirla en dos tipos para manejar los datos.  

El diseño de la interfaz sigue siendo un desafío para mí, ya que no consigo cuadrar las cosas como me gustaría a la primera. Entiendo que, al final, esto es cuestión de práctica, práctica y más práctica.  
