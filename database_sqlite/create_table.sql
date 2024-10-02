
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
                         id integer PRIMARY KEY,
                         fecha TEXT DEFAULT (datetime('now', 'localtime')),
                         total NUMERIC
);

CREATE TABLE venta_producto(
                               id_venta integer REFERENCES venta(id),
                               codigo_barras TEXT REFERENCES producto(codigo_barras),
                               precio_venta NUMERIC,
                               total NUMERIC,
                               cantidad INTEGER
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
);


CREATE TABLE historial_costos(
                                 Id integer,
                                 codigo_barras TEXT references producto(codigo_barras) ON DELETE CASCADE ,
                                 costo_compra NUMERIC,
                                 cantidad integer,
                                 estado boolean,
                                 fecha TEXT DEFAULT (datetime('now', 'localtime')),
                                 PRIMARY KEY (Id,codigo_barras)
);

CREATE TABLE historial_precios(
                                  Id integer PRIMARY KEY,
                                  codigo_barras TEXT references producto(codigo_barras) ON DELETE CASCADE ,
                                  precio_venta integer,
                                  fecha TEXT DEFAULT (datetime('now','localtime')),
                                  PRIMARY KEY (Id,codigo_barras)
);