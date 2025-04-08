import sys
import os
import matplotlib.pyplot as plt
import pandas as pd
import traceback
from datetime import datetime

def log_error(mensaje):
    """Registra errores en un archivo log."""
    script_dir = os.path.dirname(sys.executable) if getattr(sys, 'frozen', False) else os.path.dirname(os.path.abspath(__file__))
    log_path = os.path.join(script_dir, 'log.txt')
    timestamp = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    with open(log_path, 'a', encoding='utf-8') as f:
        f.write(f"[{timestamp}] ERROR: {mensaje}\n")

def generar_grafica(csv_file, tipo, threshold=1000, freq='M'):
    try:
        # Obtener la ruta del script o ejecutable
        script_dir = os.path.dirname(sys.executable) if getattr(sys, 'frozen', False) else os.path.dirname(os.path.abspath(__file__))
        absolute_csv_path = os.path.join(script_dir, csv_file)

        if not os.path.exists(absolute_csv_path):
            raise FileNotFoundError(f"El archivo {absolute_csv_path} no existe.")

        # Leer CSV
        data = pd.read_csv(absolute_csv_path)

        # Validar columna 'Fecha'
        if 'Fecha' not in data.columns:
            raise ValueError("El archivo CSV no contiene la columna 'Fecha'.")

        # Convertir a datetime y limpiar
        data['Fecha'] = pd.to_datetime(data['Fecha'], errors='coerce')
        data.dropna(subset=['Fecha'], inplace=True)
        data.sort_values('Fecha', inplace=True)

        # Re-muestreo si se supera el umbral
        if len(data) > threshold:
            data.set_index('Fecha', inplace=True)
            data = data.resample(freq).sum().reset_index()

        # Iniciar gráfica
        plt.figure(figsize=(10, 6))

        if tipo == "producto":
            for col, color, label in [('GananciaBruta', '#42B0FF', 'Ganancia Bruta'),
                                      ('GananciaNeta', '#32CD32', 'Ganancia Neta'),
                                      ('Costo', '#FF6347', 'Costo')]:
                if col in data.columns:
                    plt.plot(data['Fecha'], data[col], marker='o', linestyle='-', label=label, color=color)
                else:
                    log_error(f"La columna '{col}' no está presente en el archivo CSV.")

            plt.title('Ganancia Bruta, Ganancia Neta y Costo por Producto')

        elif tipo == "venta":
            if 'Ganancia' not in data.columns:
                raise ValueError("El archivo CSV no contiene la columna 'Ganancia'.")
            plt.plot(data['Fecha'], data['Ganancia'], color='#42B0FF', marker='o', linestyle='-', label='Ganancia')
            plt.title('Ganancia por Venta')

        else:
            raise ValueError(f"Tipo de gráfico no reconocido: '{tipo}'. Debe ser 'producto' o 'venta'.")

        # Etiquetas y formato
        plt.xlabel('Fecha')
        plt.ylabel('Valor')
        plt.xticks(rotation=20, ha="right")
        plt.legend()
        plt.tight_layout()

        # Guardar gráfico
        graph_dir = os.path.join(script_dir, 'graph')
        os.makedirs(graph_dir, exist_ok=True)
        plt.savefig(os.path.join(graph_dir, 'grafica.png'))
        plt.close()

    except Exception as e:
        log_error(traceback.format_exc())
        print("Ocurrió un error. Revisa el archivo 'log.txt' para más detalles.")

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Uso: python script.py <archivo.csv> <tipo: producto|venta>")
    else:
        csv_file = sys.argv[1]
        tipo = sys.argv[2]
        generar_grafica(csv_file, tipo)