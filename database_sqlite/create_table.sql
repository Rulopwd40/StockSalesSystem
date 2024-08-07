CREATE TABLE producto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    descripcion TEXT,
    costo_compra REAL,
    precio_venta REAL,
    stock INTEGER,
    codigo_barras TEXT
);


CREATE TABLE venta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fecha TEXT NOT NULL,  -- Usamos TEXT para almacenar fechas en formato ISO 8601 (YYYY-MM-DD HH:MM:SS)
    total REAL
);

CREATE TABLE ventas_productos (
    ventas_id INTEGER,
    producto_id INTEGER,
    FOREIGN KEY (ventas_id) REFERENCES venta(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id),
    PRIMARY KEY (ventas_id, producto_id)
);
