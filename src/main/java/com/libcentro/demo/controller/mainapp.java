package com.libcentro.demo.controller;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.libcentro.demo.model.producto;
import com.libcentro.demo.model.venta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 1. Registro de productos
- Ingreso de datos del producto (nombre, descripción, código de barras, etc.)
- Asignación de categoría (artículos escolares, papelería, etc.)
- Ingreso de costo de compra y precio de venta
2. Control de stock
- Actualización de stock al registrar una venta
- Alerta de stock bajo (opcional)
3. Emisión de tickets de venta
- Generación de ticket de venta con datos del producto, precio y cantidad vendida
- Impresión de ticket (opcional)
4. Actualización de precios
- Ingreso de porcentaje de aumento o disminución de precio
- Actualización de precios de todos los productos o seleccionados
5. Reportes
- Generación de reportes de ventas, stock y precios
 */
@SpringBootApplication
public class mainapp {

	public static void main(String[] args) {
		SpringApplication.run(mainapp.class, args);
		List<producto> testInventario = new ArrayList<>();
		List<venta> testVentas = new ArrayList<>();
		Scanner c = new Scanner(System.in);
		DataBaseConnection();

		System.out.println("Testing, launching the application...");
		System.out.println("If u read this message, means mainapp.java is running correctly");
		registrarProducto(c, testInventario);
		cargarMasiva(testInventario);
		/*
		 * 
		 * String s = c.nextLine();
		 * System.out.println(s);
		 */
		muestreo(testInventario, testVentas);
	}

	private static void DataBaseConnection() {
		String url = "jdbc:sqlite:sample.db";

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				Statement stmt = conn.createStatement();
				// Crear una tabla
				stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT NOT NULL)");
				// Insertar datos
				stmt.execute("INSERT INTO users (name) VALUES ('John Doe')");
				System.out.println("Conexión establecida y datos insertados.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void muestreo(List<producto> testInventario, List<venta> testVentas) {
		for (producto d : testInventario) {
			System.out.println(
					d.id + " Producto: " + d.nombre + " Precio Venta: " + d.precio_venta + " Stock: " + d.stock);
		}
	}

	private static void cargarMasiva(List<producto> testInventario) {
		producto producto1 = new producto();
		// Set the properties of the product
		producto1.id = 1;
		producto1.nombre = "Manzana";
		producto1.descripcion = "Fruta";
		producto1.costo_compra = 2.50f;
		producto1.precio_venta = 4.50f;
		producto1.stock = 10;
		producto1.codigo_barras = "123456789012";

		producto producto2 = new producto();
		producto2.id = 2;
		producto2.nombre = "Manzana";
		producto2.descripcion = "Fruta";
		producto2.costo_compra = 2.50f;
		producto2.precio_venta = 4.50f;
		producto2.stock = 10;
		producto2.codigo_barras = "987654321098";

		testInventario.add(producto2);
		testInventario.add(producto1);
	}

	public void menu() {
		System.out.println("=====================TESTING==========================");
		System.out.println("Eliga una opción: ");
		System.out.println("1.Registrar producto");
		System.out.println("2.Control de stock");
		System.out.println("3.Emitir ticket");
		System.out.println("4.Actualizar precios");
		System.out.println("5.Reportes");
		System.out.println("9.Salir");
		System.out.println("======================================================");
	}

	public static void registrarProducto(Scanner c, List<producto> inv) {
		Random rnd = new Random();
		int rId = rnd.nextInt(0, 99);
		// hay dos formas de hacerlo, lo hago de la forma más entendible.
		System.out.println("======================================================");
		System.out.println("Ingrese el nombre del producto");
		String nombre_producto = c.nextLine();
		System.out.println("Ingrese la descripción del producto");
		String descripcion_producto = c.nextLine();
		System.out.println("Ingrese el coste de compra del producto");
		float coste_compra = (float) c.nextFloat();
		System.out.println("Ingrese el precio de venta del producto");
		float precio_venta = (float) c.nextFloat();
		System.out.println("Ingrese el stock del producto");
		int stock = c.nextInt();
		System.out.println("Ingrese el Código de barras del producto");
		String codigo_barras = c.nextLine();

		// ACA CREO EL OBJETO P QUE ES DE TIPO PRODUCTO
		producto p = new producto(rId, nombre_producto, descripcion_producto, coste_compra, precio_venta, stock,
				codigo_barras);
		// Añado p a la lista con el add
		inv.add(p);
	}
}
