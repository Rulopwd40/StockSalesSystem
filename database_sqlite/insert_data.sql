

INSERT INTO categoria (id, categoria) VALUES
                                          (1, 'Papelería'),
                                          (2, 'Librería'),
                                          (3, 'Arte');

INSERT INTO producto (codigo_barras, nombre, categoria, costo_compra, precio_venta, stock) VALUES
                                                                                               ('1234567890123', 'Cuaderno A4', 1, 2.5, 5.0, 150),
                                                                                               ('9876543210987', 'Lápiz HB', 1, 0.15, 0.5, 500),
                                                                                               ('1239874563210', 'Bolígrafo Azul', 1, 0.3, 1.0, 300),
                                                                                               ('6543219876543', 'Libro de Matemáticas', 2, 15.0, 25.0, 50),
                                                                                               ('7891236547891', 'Agenda 2024', 2, 3.0, 7.0, 100)