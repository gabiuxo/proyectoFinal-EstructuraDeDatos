package proyectoFinal; // Declaración del paquete

import java.util.*; // Importar utilidades de colecciones

/**
 * Implementación de Grafo Dirigido para gestionar dependencias de tareas
 * Utiliza matriz de adyacencia para representar relaciones entre tareas
 */
public class TaskDependencyGraph { // Clase que implementa grafo dirigido
    
    // ATRIBUTOS del grafo
    private int maxVertices; // Cantidad máxima de vértices (tareas)
    private int[][] adjacencyMatrix; // Matriz de adyacencia para representar conexiones
    private HashMap<Integer, DepartmentTask> taskMap; // Mapa ID de tarea -> objeto DepartmentTask
    private HashMap<Integer, Integer> taskIdToIndex; // Mapa ID de tarea -> índice en matriz
    private ArrayList<Integer> taskIds; // Lista ordenada de IDs de tareas
    private int vertexCount; // Contador actual de vértices
    
    // CONSTRUCTOR
    public TaskDependencyGraph(int maxVertices) {
        this.maxVertices = maxVertices; // Establecer tamaño máximo
        this.adjacencyMatrix = new int[maxVertices][maxVertices]; // Crear matriz de adyacencia
        this.taskMap = new HashMap<>(); // Inicializar mapa de tareas
        this.taskIdToIndex = new HashMap<>(); // Inicializar mapa de índices
        this.taskIds = new ArrayList<>(); // Inicializar lista de IDs
        this.vertexCount = 0; // Inicializar contador
        
        // Inicializar matriz con ceros (sin conexiones)
        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                adjacencyMatrix[i][j] = 0; // Sin dependencia inicialmente
            }
        }
    }
    
    // MÉTODO para agregar tarea al grafo
    public boolean addTask(DepartmentTask task) {
        if (task == null || vertexCount >= maxVertices || taskMap.containsKey(task.getTaskId())) {
            return false; // No agregar si es inválido, lleno o ya existe
        }
        
        int taskId = task.getTaskId(); // Obtener ID de la tarea
        taskMap.put(taskId, task); // Agregar al mapa de tareas
        taskIdToIndex.put(taskId, vertexCount); // Mapear ID a índice
        taskIds.add(taskId); // Agregar a lista de IDs
        vertexCount++; // Incrementar contador de vértices
        
        return true; // Tarea agregada exitosamente
    }
    
    // MÉTODO para agregar dependencia entre tareas
    // taskA debe completarse antes que taskB (taskA -> taskB)
    public boolean addDependency(int taskIdA, int taskIdB) {
        if (!taskMap.containsKey(taskIdA) || !taskMap.containsKey(taskIdB)) {
            return false; // Una o ambas tareas no existen
        }
        
        int indexA = taskIdToIndex.get(taskIdA); // Obtener índice de tarea A
        int indexB = taskIdToIndex.get(taskIdB); // Obtener índice de tarea B
        
        adjacencyMatrix[indexA][indexB] = 1; // Establecer dependencia A -> B
        return true; // Dependencia agregada exitosamente
    }
    
    // MÉTODO para remover dependencia entre tareas
    public boolean removeDependency(int taskIdA, int taskIdB) {
        if (!taskMap.containsKey(taskIdA) || !taskMap.containsKey(taskIdB)) {
            return false; // Una o ambas tareas no existen
        }
        
        int indexA = taskIdToIndex.get(taskIdA); // Obtener índice de tarea A
        int indexB = taskIdToIndex.get(taskIdB); // Obtener índice de tarea B
        
        adjacencyMatrix[indexA][indexB] = 0; // Remover dependencia A -> B
        return true; // Dependencia removida exitosamente
    }
    
    // MÉTODO para verificar si existe dependencia directa
    public boolean hasDependency(int taskIdA, int taskIdB) {
        if (!taskMap.containsKey(taskIdA) || !taskMap.containsKey(taskIdB)) {
            return false; // Una o ambas tareas no existen
        }
        
        int indexA = taskIdToIndex.get(taskIdA); // Obtener índice de tarea A
        int indexB = taskIdToIndex.get(taskIdB); // Obtener índice de tarea B
        
        return adjacencyMatrix[indexA][indexB] == 1; // Retornar si existe dependencia
    }
    
    // MÉTODO para obtener dependencias directas de una tarea (tareas que dependen de esta)
    public ArrayList<DepartmentTask> getDirectDependents(int taskId) {
        ArrayList<DepartmentTask> dependents = new ArrayList<>(); // Lista de dependientes
        
        if (!taskMap.containsKey(taskId)) {
            return dependents; // Retornar lista vacía si la tarea no existe
        }
        
        int taskIndex = taskIdToIndex.get(taskId); // Obtener índice de la tarea
        
        // Recorrer la fila correspondiente en la matriz
        for (int j = 0; j < vertexCount; j++) {
            if (adjacencyMatrix[taskIndex][j] == 1) { // Si hay dependencia
                int dependentTaskId = taskIds.get(j); // Obtener ID de la tarea dependiente
                dependents.add(taskMap.get(dependentTaskId)); // Agregar tarea dependiente
            }
        }
        
        return dependents; // Retornar lista de dependientes
    }
    
    // MÉTODO para obtener prerequisitos directos de una tarea (tareas de las que depende)
    public ArrayList<DepartmentTask> getDirectPrerequisites(int taskId) {
        ArrayList<DepartmentTask> prerequisites = new ArrayList<>(); // Lista de prerequisitos
        
        if (!taskMap.containsKey(taskId)) {
            return prerequisites; // Retornar lista vacía si la tarea no existe
        }
        
        int taskIndex = taskIdToIndex.get(taskId); // Obtener índice de la tarea
        
        // Recorrer la columna correspondiente en la matriz
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[i][taskIndex] == 1) { // Si hay dependencia hacia esta tarea
                int prerequisiteTaskId = taskIds.get(i); // Obtener ID del prerequisito
                prerequisites.add(taskMap.get(prerequisiteTaskId)); // Agregar prerequisito
            }
        }
        
        return prerequisites; // Retornar lista de prerequisitos
    }
    
    // ALGORITMO DE ORDENAMIENTO TOPOLÓGICO para encontrar orden de ejecución
    public ArrayList<DepartmentTask> getExecutionOrder() {
        ArrayList<DepartmentTask> executionOrder = new ArrayList<>(); // Orden de ejecución
        boolean[] visited = new boolean[vertexCount]; // Array de visitados
        ArrayList<Integer> stack = new ArrayList<>(); // Lista como pila para ordenamiento topológico
        
        // Aplicar DFS a todos los vértices no visitados
        for (int i = 0; i < vertexCount; i++) {
            if (!visited[i]) {
                topologicalSortDFS(i, visited, stack); // DFS desde vértice i
            }
        }
        
        // Extraer elementos de la "pila" para obtener orden topológico (orden inverso)
        for (int i = stack.size() - 1; i >= 0; i--) {
            int taskIndex = stack.get(i); // Obtener siguiente tarea en orden
            int taskId = taskIds.get(taskIndex); // Obtener ID de la tarea
            executionOrder.add(taskMap.get(taskId)); // Agregar tarea al orden de ejecución
        }
        
        return executionOrder; // Retornar orden de ejecución
    }
    
    // MÉTODO auxiliar recursivo para DFS en ordenamiento topológico
    private void topologicalSortDFS(int vertex, boolean[] visited, ArrayList<Integer> stack) {
        visited[vertex] = true; // Marcar vértice como visitado
        
        // Recorrer todos los vértices adyacentes (dependientes)
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) { // Si hay conexión y no visitado
                topologicalSortDFS(i, visited, stack); // Recursión DFS
            }
        }
        
        stack.add(vertex); // Agregar vértice a la pila al terminar procesamiento
    }
    
    // MÉTODO para detectar ciclos en el grafo (dependencias circulares)
    public boolean hasCycles() {
        int[] color = new int[vertexCount]; // 0: blanco (no visitado), 1: gris (procesando), 2: negro (terminado)
        
        // Verificar ciclos desde cada vértice no visitado
        for (int i = 0; i < vertexCount; i++) {
            if (color[i] == 0 && hasCyclesDFS(i, color)) { // Si encuentra ciclo
                return true; // Hay ciclo
            }
        }
        
        return false; // No hay ciclos
    }
    
    // MÉTODO auxiliar recursivo para detección de ciclos usando DFS
    private boolean hasCyclesDFS(int vertex, int[] color) {
        color[vertex] = 1; // Marcar como procesando (gris)
        
        // Recorrer todos los vértices adyacentes
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertex][i] == 1) { // Si hay conexión
                if (color[i] == 1) { // Si el adyacente está procesando (gris)
                    return true; // Ciclo detectado
                }
                if (color[i] == 0 && hasCyclesDFS(i, color)) { // Si no visitado y encuentra ciclo
                    return true; // Ciclo detectado recursivamente
                }
            }
        }
        
        color[vertex] = 2; // Marcar como terminado (negro)
        return false; // No hay ciclo desde este vértice
    }
    
    // MÉTODO para calcular camino crítico (tareas con mayor tiempo acumulado)
    public ArrayList<DepartmentTask> getCriticalPath() {
        ArrayList<DepartmentTask> criticalPath = new ArrayList<>(); // Camino crítico
        
        if (hasCycles()) {
            return criticalPath; // No se puede calcular si hay ciclos
        }
        
        // Obtener orden topológico
        ArrayList<DepartmentTask> topologicalOrder = getExecutionOrder();
        
        // Calcular tiempos más tempranos
        HashMap<Integer, Integer> earliestTime = new HashMap<>(); // Tiempo más temprano para cada tarea
        
        // Inicializar tiempos más tempranos
        for (DepartmentTask task : topologicalOrder) {
            earliestTime.put(task.getTaskId(), 0); // Inicializar en 0
        }
        
        // Calcular tiempos más tempranos usando orden topológico
        for (DepartmentTask task : topologicalOrder) {
            int taskId = task.getTaskId();
            int currentEarliestTime = earliestTime.get(taskId);
            
            // Actualizar tiempos de tareas dependientes
            ArrayList<DepartmentTask> dependents = getDirectDependents(taskId);
            for (DepartmentTask dependent : dependents) {
                int dependentId = dependent.getTaskId();
                int newTime = currentEarliestTime + task.getEstimatedHours();
                
                if (newTime > earliestTime.get(dependentId)) {
                    earliestTime.put(dependentId, newTime); // Actualizar tiempo más temprano
                }
            }
        }
        
        // Encontrar el tiempo total del proyecto (máximo tiempo más temprano + duración de tarea)
        int maxProjectTime = 0;
        DepartmentTask lastTask = null;
        
        for (DepartmentTask task : topologicalOrder) {
            int taskEndTime = earliestTime.get(task.getTaskId()) + task.getEstimatedHours();
            if (taskEndTime > maxProjectTime) {
                maxProjectTime = taskEndTime;
                lastTask = task;
            }
        }
        
        // Construir camino crítico trabajando hacia atrás desde la última tarea
        if (lastTask != null) {
            constructCriticalPath(lastTask, earliestTime, criticalPath, maxProjectTime);
        }
        
        Collections.reverse(criticalPath); // Invertir para obtener orden correcto
        return criticalPath; // Retornar camino crítico
    }
    
    // MÉTODO auxiliar para construir camino crítico recursivamente
    private void constructCriticalPath(DepartmentTask task, HashMap<Integer, Integer> earliestTime,
                                       ArrayList<DepartmentTask> path, int targetEndTime) {
        path.add(task); // Agregar tarea al camino
        
        int taskStartTime = targetEndTime - task.getEstimatedHours(); // Calcular tiempo de inicio
        
        // Buscar prerequisito que determine el tiempo crítico
        ArrayList<DepartmentTask> prerequisites = getDirectPrerequisites(task.getTaskId());
        for (DepartmentTask prerequisite : prerequisites) {
            int prerequisiteEndTime = earliestTime.get(prerequisite.getTaskId()) + prerequisite.getEstimatedHours();
            if (prerequisiteEndTime == taskStartTime) { // Si es el prerequisito crítico
                constructCriticalPath(prerequisite, earliestTime, path, prerequisiteEndTime); // Recursión
                break; // Solo seguir un camino crítico
            }
        }
    }
    
    // MÉTODO para cargar grafo con tareas y dependencias iniciales
    public void loadSampleTaskGraph() {
        // Grafo inicia vacío - sin tareas precargadas
        // El usuario debe agregar todas las tareas y dependencias manualmente
    }
    
    // MÉTODO para obtener estadísticas del grafo
    public String getGraphStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DEL GRAFO DE DEPENDENCIAS ===\n");
        stats.append("Total de tareas: ").append(vertexCount).append("\n");
        stats.append("Capacidad máxima: ").append(maxVertices).append("\n");
        
        // Contar total de dependencias
        int totalDependencies = 0;
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    totalDependencies++;
                }
            }
        }
        stats.append("Total de dependencias: ").append(totalDependencies).append("\n");
        
        // Verificar si hay ciclos
        stats.append("Ciclos detectados: ").append(hasCycles() ? "SÍ" : "NO").append("\n");
        
        // Calcular grado de entrada y salida promedio
        double avgInDegree = 0, avgOutDegree = 0;
        for (int i = 0; i < vertexCount; i++) {
            int inDegree = 0, outDegree = 0;
            
            for (int j = 0; j < vertexCount; j++) {
                if (adjacencyMatrix[j][i] == 1) inDegree++;
                if (adjacencyMatrix[i][j] == 1) outDegree++;
            }
            
            avgInDegree += inDegree;
            avgOutDegree += outDegree;
        }
        
        avgInDegree /= vertexCount;
        avgOutDegree /= vertexCount;
        
        stats.append("Grado de entrada promedio: ").append(String.format("%.1f", avgInDegree)).append("\n");
        stats.append("Grado de salida promedio: ").append(String.format("%.1f", avgOutDegree)).append("\n");
        
        return stats.toString();
    }
    
    // MÉTODO para mostrar matriz de adyacencia
    public String displayAdjacencyMatrix() {
        StringBuilder matrix = new StringBuilder();
        matrix.append("=== MATRIZ DE ADYACENCIA ===\n");
        
        // Encabezado con IDs de tareas
        matrix.append("     ");
        for (int i = 0; i < vertexCount; i++) {
            matrix.append(String.format("%4d", taskIds.get(i)));
        }
        matrix.append("\n");
        
        // Filas de la matriz
        for (int i = 0; i < vertexCount; i++) {
            matrix.append(String.format("%4d ", taskIds.get(i))); // ID de tarea como etiqueta de fila
            for (int j = 0; j < vertexCount; j++) {
                matrix.append(String.format("%4d", adjacencyMatrix[i][j])); // Valor de adyacencia
            }
            matrix.append("\n");
        }
        
        return matrix.toString();
    }
    
    // MÉTODOS de utilidad
    public int getVertexCount() { return vertexCount; }
    public int getTaskCount() { return vertexCount; } // Alias para getVertexCount()
    public boolean isEmpty() { return vertexCount == 0; }
    public DepartmentTask getTask(int taskId) { return taskMap.get(taskId); }
    public ArrayList<Integer> getAllTaskIds() { return new ArrayList<>(taskIds); }
    
    // MÉTODO para obtener matriz de adyacencia como String
    public String getAdjacencyMatrix() {
        return displayAdjacencyMatrix();
    }
    
    // MÉTODO para obtener estadísticas del proyecto
    public String getProjectStatistics() {
        return getGraphStatistics();
    }
}