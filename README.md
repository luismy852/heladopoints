<h1>🍦 HeladoPoints - Sistema de Gestión de Lealtad </h1>

Esta solución automatiza la extracción de datos desde documentos físicos (facturas) y su transformación en registros estructurados de lealtad. El sistema integra Google Cloud Vision AI para el reconocimiento óptico de caracteres (OCR) y garantiza la seguridad de la información mediante una capa de validación criptográfica (Hashing), diseñada específicamente para mitigar el fraude y la duplicidad de registros en entornos transaccionales.

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

## Arquitectura y Flujo de Validación (Backend Focus)

El sistema no solo procesa imágenes; implementa un pipeline de validación diseñado para garantizar la consistencia de los datos:

- Optimización de Carga (Client-Side): Validación de payload en el frontend para asegurar integridad de archivos (límite de 10MB) y reducir latencia en el servidor.

- Capa de Idempotencia (Anti-Fraude): Implementación de una lógica de Hashing que genera un identificador único por factura. Antes de procesar, el sistema consulta la persistencia para mitigar el registro duplicado de transacciones.

- Procesamiento de Datos no Estructurados (OCR): Integración con el motor de Google Cloud Vision para la extracción de texto. Se aplica un algoritmo de filtrado y búsqueda de patrones (Pattern Matching) para identificar ítems específicos y valores transaccionales.

- Consistencia Transaccional: Actualización atómica del balance de puntos del usuario en la base de datos PostgreSQL, asegurando que la operación se complete correctamente bajo estándares ACID.
