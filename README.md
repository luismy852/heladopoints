<h1>🍦 HeladoPoints - Sistema de Gestión de Lealtad </h1>

Aplicación web que permite a los usuarios transformar sus facturas de heladerías en puntos de lealtad mediante tecnología OCR.

## Características Principales (Features)
 - `Escaneo Inteligente`: Procesamiento de facturas mediante OCR para detectar productos válidos.
 - `Sistema de Puntos`: Acumulación automática de "Helado Points" por cada compra registrada.
 - `Validación de Errores`: Manejo detallado de excepciones (facturas duplicadas, imágenes pesadas, contenido no válido).

<h2>Tecnologías Utilizadas</h2>

Frontend: Angular 17+, CSS.

Backend: Java (Spring Boot).

Infraestructura: Docker & Docker Compose.

Despliegue: GitHub Pages.

## Autenticación con Spring Security & JWT

El sistema utiliza un esquema de seguridad stateless para proteger la integridad de los puntos de los usuarios:

- JWT (JSON Web Tokens): Implementado para manejar sesiones de usuario de forma segura sin sobrecargar el servidor.

- Encriptación: Uso de BCrypt para asegurar que las contraseñas nunca se almacenen en texto plano.

- Protección de Endpoints: Solo los usuarios autenticados pueden realizar peticiones de carga de facturas al motor de OCR.

## Flujo del Proceso OCR
No es solo subir una foto; hay una lógica detrás para evitar fraudes:

1. Validación de Imagen: El frontend verifica que no exceda los 10MB.
2. Prevención de Duplicados: Se verifica si la factura ya ha sido registrada previamente en la base de datos.
3. Escaneo de Contenido: El backend analiza el texto buscando productos de heladería válidos.
4. Asignación de Helado Points: Una vez validada, el saldo del usuario se actualiza al instante.
