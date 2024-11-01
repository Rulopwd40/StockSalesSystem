# StockSalesSystem

**StockSalesSystem** es una aplicación de escritorio desarrollada en Java, diseñada específicamente para la gestión de inventario y ventas en una papelería o librería. Este sistema facilita el control de productos, seguimiento de ventas, actualización de precios y generación de reportes, brindando una solución completa para pequeñas y medianas empresas.

## Características

- **Gestión de Productos**: 
  - Registro de nuevos productos con detalles como nombre, descripción, código de barras, costo de compra y precio de venta.
  - Actualización de productos.
  - Actualización de stock al registrar una venta.
  - Alerta de stock bajo.

- **Gestión de Ventas**: 
  - Registro de ventas detalladas.
  - Emisión de tickets de venta con impresión.
  - Actualización automática del inventario tras cada venta.

- **Gestión de Precios**:
  - Modificación de precios por porcentaje (incremento o disminución) en diferentes rangos (general,por categoria).
  - Historial de precios de venta y costos de compra para rastrear cambios a lo largo del tiempo.

- **Reportes(WIP)**:
  - Generación de reportes de ventas, stock y precios.
  - Visualización de datos para una mejor toma de decisiones.

## Tecnologías Utilizadas

- **Backend**: Java con Spring Boot
- **Frontend**: Java Swing para la interfaz gráfica
- **ORM**: Hibernate para la persistencia de datos
- **Base de Datos**: SQLite
