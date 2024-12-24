import sys
import matplotlib.pyplot as plt
import pandas as pd

def generar_grafica(csv_file, tipo):
    data = pd.read_csv(csv_file)
    plt.figure(figsize=(10, 6))

    if tipo == "producto":
        plt.plot(data['Fecha'], data['GananciaNeta'], color='#42B0FF', marker='o', linestyle='-', label='Ganancia Neta')
        plt.xlabel('Fecha')
        plt.ylabel('Ganancia Neta')
        plt.title('Ganancia Neta por Producto')

    elif tipo == "venta":
        plt.plot(data['Fecha'], data['GananciaNeta'], color='#42B0FF', marker='o', linestyle='-', label='Ganancia Neta')
        plt.xlabel('Fecha')
        plt.ylabel('Ganancia Neta')
        plt.title('Ganancia Neta por Venta')

    plt.xticks(rotation=0, ha="right")  # Rotar las etiquetas de las fechas para mejor legibilidad
    plt.tight_layout()  # Ajustar el diseño
    plt.savefig('graph/grafica.png')  # Guardar el archivo PNG

    plt.close()

if __name__ == "__main__":
    csv_file = sys.argv[1]  # Primer argumento: archivo CSV
    tipo = sys.argv[2]      # Segundo argumento: tipo de gráfica
    generar_grafica(csv_file, tipo)