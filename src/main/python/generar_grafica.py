import sys
import matplotlib.pyplot as plt
import pandas as pd

def generar_grafica(csv_file, tipo):
    # Leer el archivo CSV
    data = pd.read_csv(csv_file)

    # Crear la gráfica
    plt.figure(figsize=(10, 6))

    if tipo == "producto":
        # Graficar la ganancia neta por producto
        plt.bar(data['Fecha'], data['GananciaNeta'], color='blue')
        plt.xlabel('Fecha')
        plt.ylabel('Ganancia Neta')
        plt.title('Ganancia Neta por Producto')

    elif tipo == "venta":
        # Graficar la ganancia neta por venta
        plt.bar(data['Fecha'], data['GananciaNeta'], color='green')
        plt.xlabel('Fecha')
        plt.ylabel('Ganancia Neta')
        plt.title('Ganancia Neta por Venta')

    # Guardar la gráfica como un archivo PNG
    plt.xticks(rotation=45, ha="right")  # Rotar las etiquetas de las fechas para mejor legibilidad
    plt.tight_layout()  # Ajustar el diseño
    plt.savefig('grafica.png')  # Guardar el archivo PNG

    # Cerrar la gráfica
    plt.close()

if __name__ == "__main__":
    csv_file = sys.argv[1]  # Primer argumento: archivo CSV
    tipo = sys.argv[2]      # Segundo argumento: tipo de gráfica
    generar_grafica(csv_file, tipo)