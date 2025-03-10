import sys
import os
import matplotlib.pyplot as plt
import pandas as pd

def generar_grafica(csv_file, tipo):
    if getattr(sys, 'frozen', False):
        script_dir = os.path.dirname(sys.executable)
    else:
        script_dir = os.path.dirname(os.path.abspath(__file__))

    absolute_csv_path = os.path.join(script_dir, csv_file)

    if not os.path.exists(absolute_csv_path):
        raise FileNotFoundError(f"El archivo {absolute_csv_path} no existe.")

    data = pd.read_csv(absolute_csv_path)
    plt.figure(figsize=(10, 6))

    if tipo == "producto":
        plt.plot(data['Fecha'], data['GananciaBruta'], color='#42B0FF', marker='o', linestyle='-', label='Ganancia Bruta')
        plt.plot(data['Fecha'], data['GananciaNeta'], color='#32CD32', marker='o', linestyle='-', label='Ganancia Neta')
        plt.plot(data['Fecha'], data['Costo'], color='#FF6347', marker='o', linestyle='-', label='Costo')

        plt.xlabel('Fecha')
        plt.ylabel('Valor')
        plt.title('Ganancia Bruta, Ganancia Neta y Costo por Producto')

    elif tipo == "venta":
        plt.plot(data['Fecha'], data['Ganancia'], color='#42B0FF', marker='o', linestyle='-', label='Ganancia')
        plt.xlabel('Fecha')
        plt.ylabel('Ganancia')
        plt.title('Ganancia por Venta')

    plt.legend()
    plt.xticks(rotation=20, ha="right")
    plt.tight_layout()

    # Asegurarse de que la carpeta 'graph' existe
    graph_dir = os.path.join(script_dir, 'graph')
    if not os.path.exists(graph_dir):
        os.makedirs(graph_dir)

    # Guardar la gráfica en la carpeta 'graph'
    plt.savefig(os.path.join(graph_dir, 'grafica.png'))
    plt.close()

if __name__ == "__main__":
    csv_file = sys.argv[1]
    tipo = sys.argv[2]
    generar_grafica(csv_file, tipo)