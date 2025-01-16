import sys
import matplotlib.pyplot as plt
import pandas as pd

def generar_grafica(csv_file, tipo):
    data = pd.read_csv(csv_file)
    plt.figure(figsize=(10, 6))

    if tipo == "producto":
        # Graficamos las tres líneas
        plt.plot(data['Fecha'], data['GananciaBruta'], color='#42B0FF', marker='o', linestyle='-', label='Ganancia Bruta')
        plt.plot(data['Fecha'], data['GananciaNeta'], color='#32CD32', marker='o', linestyle='-', label='Ganancia Neta')
        plt.plot(data['Fecha'], data['Costo'], color='#FF6347', marker='o', linestyle='-', label='Costo')

        plt.xlabel('Fecha')
        plt.ylabel('Valor')
        plt.title('Ganancia Bruta, Ganancia Neta y Costo por Producto')
        plt.yticks(range(0, int(data[['GananciaBruta', 'GananciaNeta', 'Costo']].max().max()) + 10000, 10000))

    elif tipo == "venta":
        plt.plot(data['Fecha'], data['Ganancia'], color='#42B0FF', marker='o', linestyle='-', label='Ganancia')
        plt.xlabel('Fecha')
        plt.ylabel('Ganancia')
        plt.title('Ganancia por Venta')


    plt.legend()
    plt.xticks(rotation=0, ha="right")  # Rotar las etiquetas de las fechas para mejor legibilidad
    plt.tight_layout()  # Ajustar el diseño
    plt.savefig('graph/grafica.png')  # Guardar el archivo PNG


    plt.close()

if __name__ == "__main__":
    csv_file = sys.argv[1]  # Primer argumento: archivo CSV
    tipo = sys.argv[2]      # Segundo argumento: tipo de gráfica
    generar_grafica(csv_file, tipo)