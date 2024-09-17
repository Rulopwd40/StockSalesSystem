CREATE TABLE producto(
                         codigo_barras TEXT PRIMARY KEY,
                         nombre TEXT,
                         categoria integer REFERENCES categoria(id),
                         costo_compra NUMERIC,
                         precio_venta NUMERIC,
                         stock int

);

CREATE TABLE venta_producto(
                               id_venta integer REFERENCES venta(id),
                               codigo_barras TEXT REFERENCES producto(codigo_barras)
);

CREATE TABLE producto_fuera_de_stock(
                                        id integer PRIMARY KEY,
                                        nombre TEXT,
                                        precio_venta NUMERIC,
                                        cantidad INTEGER
);

CREATE TABLE detalle_venta(
                              id_venta integer REFERENCES venta(id),
                              id_producto integer REFERENCES producto_fuera_de_stock(id)
);

CREATE TABLE categoria (
                           id integer PRIMARY KEY,
                           categoria TEXT
)