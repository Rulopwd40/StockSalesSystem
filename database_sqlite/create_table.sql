
CREATE TABLE producto(
                         codigo_barras TEXT PRIMARY KEY ,
                         nombre TEXT,
                         categoria INTEGER DEFAULT NULL,
                         costo_compra NUMERIC,
                         precio_venta NUMERIC,
                         costo_inicial INTEGER references historial_precios(id) ,
                         stock int,
                         FOREIGN KEY (categoria) REFERENCES categoria(id) ON DELETE SET NULL

);

CREATE TABLE venta(
                      id BIGINT PRIMARY KEY,
                      fecha TEXT DEFAULT CURRENT_TIMESTAMP,
                      total NUMERIC,
                      estado boolean
);

CREATE TABLE venta_producto(
                               id_venta BIGINT REFERENCES venta(id),
                               codigo_barras TEXT REFERENCES producto(codigo_barras),
                               precio_venta NUMERIC,
                               costo_compra NUMERIC,
                               descuento NUMERIC,
                               cantidad INTEGER,
                               total NUMERIC

);


CREATE TABLE producto_fuera_de_stock(
                                        id BIGINT PRIMARY KEY,
                                        id_venta integer references venta(id),
                                        nombre TEXT,
                                        precio_venta NUMERIC,
                                        cantidad INTEGER,
                                        descuento NUMERIC
);


CREATE TABLE detalle_venta(
                              id_venta BIGINT REFERENCES venta(id),
                              id_producto BIGINT REFERENCES producto_fuera_de_stock(id)
);

CREATE TABLE categoria (
                           id integer PRIMARY KEY,
                           categoria TEXT
);


CREATE TABLE historial_costos(
                                 Id INTEGER PRIMARY KEY AUTOINCREMENT,
                                 codigo_barras TEXT REFERENCES producto(codigo_barras) ON DELETE CASCADE,
                                 costo_compra NUMERIC,
                                 cantidad INTEGER,
                                 estado BOOLEAN,
                                 fecha TEXT DEFAULT (datetime('now', 'localtime'))
);

CREATE TABLE historial_precios(
                                  Id INTEGER PRIMARY KEY AUTOINCREMENT ,
                                  codigo_barras TEXT references producto(codigo_barras) ON DELETE CASCADE ,
                                  precio_venta integer,
                                  fecha TEXT DEFAULT (datetime('now','localtime'))
);

