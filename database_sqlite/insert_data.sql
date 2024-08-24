-- Insertar múltiples productos
INSERT INTO producto (nombre, categoria, costo_compra, precio_venta, stock, codigo_barras) VALUES
('Producto 1', 'Descripción del Producto 1', 10.0, 15.0, 100, '1111111111111'),
('Producto 2', 'Descripción del Producto 2', 20.0, 25.0, 200, '2222222222222'),
('Producto 3', 'Descripción del Producto 3', 30.0, 35.0, 300, '3333333333333'),
('Producto 4', 'Descripción del Producto 4', 40.0, 45.0, 400, '4444444444444'),
('Producto 5', 'Descripción del Producto 5', 50.0, 55.0, 500, '5555555555555');

-- Insertar una nueva venta
INSERT INTO venta (fecha, total) 
VALUES ('2024-08-07 10:00:00', 75.0);

-- Asignar tres productos diferentes a la venta
INSERT INTO venta_producto (ventas_id, producto_id) VALUES
(1, 1),
(1, 2),
(1, 3);
