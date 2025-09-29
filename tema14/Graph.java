package tema14;

public class Graph {
    
    // Algoritmo de Floyd-Warshall que encuentra los caminos más cortos entre todos los pares de vértices
    public String Floyd(long [][] mAdy){
        int vertices = mAdy.length;                                      // Obtiene el número de vértices del grafo
        long matrizAdyacencia[][] = mAdy;                               // Matriz de adyacencia que se modificará durante el algoritmo
        String caminos[][] = new String[vertices][vertices];            // Matriz para almacenar los caminos óptimos encontrados
        String caminosaux[][] = new String[vertices][vertices];         // Matriz auxiliar para construir los caminos paso a paso
        String caminoRecorrido = "", cadena = "", caminitos = "";       // Variables para formatear la salida de resultados
        int i, j, k;                                                    // Variables de control para los bucles anidados
        float temp1, temp2, temp3, temp4, min;                          // Variables temporales para comparar distancias
        
        // Inicialización: establece todas las matrices de caminos como cadenas vacías
        for (i = 0; i < vertices; i++){                                 // Recorre todas las filas (vértices origen)
            for (j = 0; j < vertices; j++){                             // Recorre todas las columnas (vértices destino)
                caminos[i][j] = "";                                     // Inicializa matriz de caminos como vacía
                caminosaux[i][j] = "";                                  // Inicializa matriz auxiliar como vacía
            }
        }
        
        // Algoritmo principal de Floyd-Warshall: triple bucle anidado
        for (k = 0; k < vertices; k++){                                 // k: vértice intermedio a considerar en esta iteración
            for (i = 0; i < vertices; i++){                             // i: vértice origen
                for (j = 0; j < vertices; j++){                         // j: vértice destino
                    temp1 = matrizAdyacencia[i][j];                     // Distancia actual directa de i a j
                    temp2 = matrizAdyacencia[i][k];                     // Distancia de i al vértice intermedio k
                    temp3 = matrizAdyacencia[k][j];                     // Distancia del vértice intermedio k a j
                    temp4 = temp2 + temp3;                              // Distancia total pasando por k (i->k->j)
                    
                    // Encuentra la distancia mínima entre el camino directo y el que pasa por k
                    min = Math.min(temp1, temp4);                       // Compara distancia directa vs. distancia vía k
                    if (temp1 != temp4){                                // Solo actualiza si hay diferencia entre los caminos
                        if(min == temp4){                               // Si el camino vía k es más corto
                            caminoRecorrido = "";                       // Reinicia la cadena de camino
                            caminosaux[i][j] = k + "";                  // Guarda k como vértice intermedio para el camino i->j
                            // Construye recursivamente el camino completo pasando por k
                            caminos[i][j] = paths(i, k, caminosaux, caminoRecorrido) + (k + 1);
                        }
                    }
                    matrizAdyacencia[i][j] = (long) min;                // Actualiza la matriz con la distancia mínima encontrada
                }
            }
        }
        
        // Construcción de la cadena de salida con la matriz de distancias mínimas
        for (i = 0; i < vertices; i++){                                 // Recorre todas las filas de la matriz resultado
            for (j = 0; j < vertices; j++){                             // Recorre todas las columnas de la matriz resultado
                cadena = cadena + "[" + matrizAdyacencia[i][j] + "]";   // Formatea cada distancia entre corchetes
            }
            cadena = cadena + "\n";                                     // Agrega salto de línea al final de cada fila
        }

        // Construcción de la cadena con los caminos más cortos encontrados
        for (i = 0; i < vertices; i++){                                 // Recorre todos los vértices origen
            for (j = 0; j < vertices; j++){                             // Recorre todos los vértices destino
                if (matrizAdyacencia[i][j] != 1000000000){              // Verifica que exista un camino (no infinito)
                    if(i != j){                                         // Excluye el camino de un vértice a sí mismo
                        long costo = matrizAdyacencia[i][j];            // Obtiene el costo del camino más corto
                        if(caminos[i][j].equals("")){                   // Si no hay vértices intermedios (camino directo)
                            // Formatea la salida para camino directo
                            caminitos += "De (" + (i + 1) + " --->" + (j + 1) + ") irse por . . . (" 
                                        + (i + 1) + ", " + (j + 1) + ")"
                                        + " | Costo: " + costo + "\n";
                        } else {                                        // Si hay vértices intermedios en el camino
                            // Formatea la salida incluyendo los vértices intermedios
                            caminitos += "De (" + (i + 1) + " --->" + (j + 1) + ") irse por . . . (" 
                                        + (i + 1) + ", " 
                                        + (caminos[i][j])  + ", " 
                                        + (j + 1) + ")"
                                        + " | Costo: " + costo + "\n";
                        }
                    }
                }
            }
        }
        // Retorna la cadena formateada con la matriz de distancias y los caminos encontrados
        return "La matriz de caminos más cortos es:\n" + cadena + "\nLos diferentes caminos más cortos son:\n" + caminitos;
    }

    // Método recursivo que reconstruye el camino completo entre dos vértices
    public String paths(int i, int k, String[][] caminosaux, String caminoRecorrido){
        if (caminosaux[i][k].equals("")){                               // Caso base: no hay vértices intermedios entre i y k
            return "";                                                  // Retorna cadena vacía
        } else {
            // Caso recursivo: hay al menos un vértice intermedio
            // Construye recursivamente el camino desde i hasta el vértice intermedio almacenado en caminosaux[i][k]
            caminoRecorrido += paths(i, Integer.parseInt(caminosaux[i][k].toString()), caminosaux, caminoRecorrido) 
                            + (Integer.parseInt(caminosaux[i][k].toString()) + 1) + ",  ";
            return caminoRecorrido;                                     // Retorna el camino completo construido
        }
    }
    
    // Método utilitario que crea una copia profunda de una matriz
    public long[][] copiarMatriz(long[][] original) {
        int n = original.length;                                        // Obtiene el tamaño de la matriz original
        long[][] copia = new long[n][n];                               // Crea una nueva matriz del mismo tamaño
        for (int i = 0; i < n; i++) {                                   // Recorre todas las filas
            for (int j = 0; j < n; j++) {                               // Recorre todas las columnas
                copia[i][j] = original[i][j];                           // Copia cada elemento individualmente
            }
        }
        return copia;                                                   // Retorna la matriz copiada
    }
    
    // Método utilitario para mostrar una matriz de forma legible en consola
    public void imprimirMatriz(String titulo, long[][] matriz) {
        System.out.println("\n" + titulo);                             // Imprime el título de la matriz
        System.out.println("================================");        // Línea separadora decorativa
        for (int i = 0; i < matriz.length; i++) {                      // Recorre todas las filas de la matriz
            for (int j = 0; j < matriz[i].length; j++) {               // Recorre todas las columnas de la fila actual
                if (matriz[i][j] == 999999999) {                       // Si el valor representa infinito
                    System.out.print("[∞]\t");                         // Muestra el símbolo de infinito
                } else {                                               // Si es un valor numérico normal
                    System.out.print("[" + matriz[i][j] + "]\t");      // Muestra el valor entre corchetes
                }
            }
            System.out.println();                                      // Salto de línea al final de cada fila
        }
        System.out.println();                                          // Línea en blanco al final de la matriz
    }
    
    // Método principal que ejecuta todas las pruebas del algoritmo Floyd-Warshall
    public static void main(String[] args) {
        Graph graph = new Graph();                                      // Crea una instancia de la clase Graph
        
        // Cabecera del programa
        System.out.println("IMPLEMENTACIÓN DEL ALGORITMO DE FLOYD-WARSHALL");
        System.out.println("===============================================\n");
        
        // ========== PRUEBA 1: GRAFO DIRIGIDO ==========
        System.out.println("1. GRAFO 1 - MATRIZ DIRIGIDA");
        System.out.println("-----------------------------");
        
        // Definición de matriz de adyacencia dirigida (las conexiones tienen dirección específica)
        long[][] grafo1Dirigido = {
            {0, 3, 999999999, 7},                                       // Vértice 0: conecta a vértice 1 (peso 3) y vértice 3 (peso 7)
            {8, 0, 2, 999999999},                                       // Vértice 1: conecta a vértice 0 (peso 8) y vértice 2 (peso 2)
            {5, 999999999, 0, 1},                                       // Vértice 2: conecta a vértice 0 (peso 5) y vértice 3 (peso 1)
            {2, 999999999, 999999999, 0}                                // Vértice 3: conecta solo a vértice 0 (peso 2)
        };
        
        graph.imprimirMatriz("Matriz de adyacencia original (dirigida):", grafo1Dirigido);
        
        // Aplica Floyd-Warshall al grafo dirigido (necesita copia para no modificar el original)
        long[][] copiaGrafo1Dirigido = graph.copiarMatriz(grafo1Dirigido);
        String resultadoGrafo1Dirigido = graph.Floyd(copiaGrafo1Dirigido);
        System.out.println("RESULTADO FLOYD-WARSHALL GRAFO 1 DIRIGIDO:");
        System.out.println(resultadoGrafo1Dirigido);
        
        System.out.println("\n" + "=".repeat(80) + "\n");               // Separador visual entre secciones
        
        // ========== PRUEBA 2: GRAFO NO DIRIGIDO ==========
        System.out.println("2. GRAFO 1 - MATRIZ NO DIRIGIDA");
        System.out.println("-------------------------------");
        
        // Definición de matriz de adyacencia no dirigida (las conexiones son bidireccionales)
        long[][] grafo1NoDirigido = {
            {0, 3, 5, 7},                                               // Vértice 0: conecta bidireccional con 1(peso 3), 2(peso 5), 3(peso 7)
            {3, 0, 2, 999999999},                                       // Vértice 1: conecta bidireccional con 0(peso 3), 2(peso 2)
            {5, 2, 0, 1},                                               // Vértice 2: conecta bidireccional con 0(peso 5), 1(peso 2), 3(peso 1)
            {7, 999999999, 1, 0}                                        // Vértice 3: conecta bidireccional con 0(peso 7), 2(peso 1)
        };
        
        graph.imprimirMatriz("Matriz de adyacencia original (no dirigida):", grafo1NoDirigido);
        
        // Aplica Floyd-Warshall al grafo no dirigido
        long[][] copiaGrafo1NoDirigido = graph.copiarMatriz(grafo1NoDirigido);
        String resultadoGrafo1NoDirigido = graph.Floyd(copiaGrafo1NoDirigido);
        System.out.println("RESULTADO FLOYD-WARSHALL GRAFO 1 NO DIRIGIDO:");
        System.out.println(resultadoGrafo1NoDirigido);
        
        System.out.println("\n" + "=".repeat(80) + "\n");               // Separador visual entre secciones
        
        // ========== PRUEBA 3: GRAFO 2 ESPECÍFICO ==========
        System.out.println("3. GRAFO 2 - MATRIZ ESPECÍFICA");
        System.out.println("------------------------------");
        
        // Definición de la matriz específica proporcionada en el ejercicio
        long[][] grafo2 = {
            {0, 7, 9, 999999999, 999999999, 14},                        // Vértice 0: conecta a 1(7), 2(9), 5(14)
            {7, 0, 10, 15, 999999999, 999999999},                       // Vértice 1: conecta a 0(7), 2(10), 3(15)
            {9, 10, 0, 11, 999999999, 999999999},                       // Vértice 2: conecta a 0(9), 1(10), 3(11)
            {999999999, 15, 11, 0, 6, 999999999},                       // Vértice 3: conecta a 1(15), 2(11), 4(6)
            {999999999, 999999999, 999999999, 6, 0, 9},                 // Vértice 4: conecta a 3(6), 5(9)
            {14, 999999999, 999999999, 999999999, 9, 0}                 // Vértice 5: conecta a 0(14), 4(9)
        };
        
        graph.imprimirMatriz("Matriz de adyacencia original (GRAFO 2):", grafo2);
        
        // ========== PRUEBA 4: APLICACIÓN DEL ALGORITMO AL GRAFO 2 ==========
        System.out.println("4. PRUEBAS DEL GRAFO 2");
        System.out.println("----------------------");
        
        // Aplica Floyd-Warshall al grafo 2 específico
        long[][] copiaGrafo2 = graph.copiarMatriz(grafo2);
        String resultadoGrafo2 = graph.Floyd(copiaGrafo2);              // Ejecuta el algoritmo
        System.out.println("RESULTADO FLOYD-WARSHALL GRAFO 2:");
        System.out.println(resultadoGrafo2);                            // Muestra resultados detallados
        
        // Mensaje de finalización del análisis
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ANÁLISIS COMPLETADO");                      // Indica que todas las pruebas terminaron
        System.out.println("=".repeat(80));                             // Línea decorativa de cierre
    }
}
