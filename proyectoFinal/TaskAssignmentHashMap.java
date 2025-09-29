package proyectoFinal; // Declaración del paquete donde está la clase TaskAssignmentHashMap

import java.util.HashMap; // Importar HashMap para estructuras de datos hash
import java.util.ArrayList; // Importar ArrayList para listas dinámicas
import java.util.List; // Importar List para interfaz de listas
import java.util.Set; // Importar Set para conjuntos

public class TaskAssignmentHashMap { // CLASE PARA IMPLEMENTAR PUNTO 5: HASH TABLES PARA ASIGNACIÓN DE TAREAS
    
    // HASH TABLES PRINCIPALES (HashMap structures para acceso O(1))
    private HashMap<Integer, DepartmentTask> taskById; // HashMap para búsqueda de tareas por ID - acceso O(1)
    private HashMap<String, List<DepartmentTask>> tasksByEmployee; // HashMap para búsqueda de tareas por empleado - acceso O(1)
    private HashMap<String, List<DepartmentTask>> tasksByDepartment; // HashMap para búsqueda de tareas por departamento - acceso O(1)
    private HashMap<String, List<DepartmentTask>> tasksByStatus; // HashMap para búsqueda de tareas por estado - acceso O(1)
    private HashMap<Integer, List<DepartmentTask>> tasksByEstimatedTime; // HashMap para búsqueda por tiempo estimado - acceso O(1)
    private HashMap<String, Employee> employeesByName; // HashMap para búsqueda rápida de empleados - acceso O(1)
    
    // CONTADORES PARA ASIGNAR IDs ÚNICOS
    private int nextTaskId; // Contador para asignar IDs únicos a las tareas
    private int totalTasks; // Contador total de tareas en el sistema
    
    // CONSTRUCTOR
    public TaskAssignmentHashMap() { // Constructor que inicializa todas las estructuras HashMap
        taskById = new HashMap<>(); // Inicializar HashMap de tareas por ID
        tasksByEmployee = new HashMap<>(); // Inicializar HashMap de tareas por empleado
        tasksByDepartment = new HashMap<>(); // Inicializar HashMap de tareas por departamento
        tasksByStatus = new HashMap<>(); // Inicializar HashMap de tareas por estado
        tasksByEstimatedTime = new HashMap<>(); // Inicializar HashMap de tareas por tiempo
        employeesByName = new HashMap<>(); // Inicializar HashMap de empleados por nombre
        nextTaskId = 1; // Comenzar IDs desde 1
        totalTasks = 0; // Inicializar contador de tareas
        
        // Inicializar listas para estados comunes
        initializeCommonCategories(); // Llamar método para preparar categorías
    } // Fin del constructor
    
    // MÉTODO PARA INICIALIZAR CATEGORÍAS COMUNES
    private void initializeCommonCategories() { // Método privado para inicializar categorías predefinidas
        // Inicializar listas para estados comunes
        tasksByStatus.put("Pendiente", new ArrayList<>()); // Lista para tareas pendientes
        tasksByStatus.put("En Proceso", new ArrayList<>()); // Lista para tareas en proceso
        tasksByStatus.put("Completada", new ArrayList<>()); // Lista para tareas completadas
        
        // Inicializar listas para tiempos comunes (rangos de horas)
        tasksByEstimatedTime.put(8, new ArrayList<>()); // Tareas de 1-8 horas (cortas)
        tasksByEstimatedTime.put(24, new ArrayList<>()); // Tareas de 9-24 horas (medianas)
        tasksByEstimatedTime.put(72, new ArrayList<>()); // Tareas de 25-72 horas (largas)
        tasksByEstimatedTime.put(999, new ArrayList<>()); // Tareas de más de 72 horas (muy largas)
    } // Fin del método initializeCommonCategories
    
    // MÉTODOS PRINCIPALES PARA AGREGAR TAREAS (HASH TABLE OPERATIONS)
    public int addTask(DepartmentTask task) { // Método para agregar tarea al sistema HashMap - O(1)
        if (task == null) { // Validar que la tarea no sea null
            System.out.println("Error: No se puede agregar una tarea null"); // Mensaje de error
            return -1; // Retornar -1 para indicar error
        }
        
        int taskId = nextTaskId++; // Asignar ID único y incrementar contador
        taskById.put(taskId, task); // Agregar tarea al HashMap principal por ID - O(1)
        totalTasks++; // Incrementar contador total
        
        // Agregar a HashMap por departamento
        String department = task.getDepartment(); // Obtener departamento de la tarea
        tasksByDepartment.computeIfAbsent(department, k -> new ArrayList<>()).add(task); // Crear lista si no existe y agregar - O(1)
        
        // Agregar a HashMap por estado
        String status = task.getStatus(); // Obtener estado de la tarea
        tasksByStatus.computeIfAbsent(status, k -> new ArrayList<>()).add(task); // Crear lista si no existe y agregar - O(1)
        
        // Agregar a HashMap por tiempo estimado (categorizado)
        int timeCategory = categorizeTime(task.getEstimatedHours()); // Categorizar tiempo
        tasksByEstimatedTime.get(timeCategory).add(task); // Agregar a categoría correspondiente - O(1)
        
        // Si la tarea tiene empleado asignado, agregarlo al HashMap por empleado
        if (task.getAssignedEmployee() != null) { // Si hay empleado asignado
            String employeeName = task.getAssignedEmployee().getName(); // Obtener nombre del empleado
            tasksByEmployee.computeIfAbsent(employeeName, k -> new ArrayList<>()).add(task); // Agregar a HashMap por empleado - O(1)
            employeesByName.put(employeeName, task.getAssignedEmployee()); // Agregar empleado al HashMap de empleados - O(1)
        }
        
        System.out.println("Tarea agregada exitosamente con ID: " + taskId); // Mensaje de confirmación
        return taskId; // Retornar ID asignado
    } // Fin del método addTask
    
    // MÉTODO AUXILIAR PARA CATEGORIZAR TIEMPO ESTIMADO
    private int categorizeTime(int hours) { // Método privado para categorizar horas en rangos
        if (hours <= 8) return 8; // Tareas cortas: 1-8 horas
        if (hours <= 24) return 24; // Tareas medianas: 9-24 horas
        if (hours <= 72) return 72; // Tareas largas: 25-72 horas
        return 999; // Tareas muy largas: más de 72 horas
    } // Fin del método categorizeTime
    
    // MÉTODOS DE BÚSQUEDA RÁPIDA O(1) - FUNCIONALIDAD PRINCIPAL DEL HASH TABLE
    public DepartmentTask findTaskById(int taskId) { // Búsqueda por ID - O(1)
        return taskById.get(taskId); // Retornar tarea directamente del HashMap - O(1)
    } // Fin del método findTaskById
    
    public List<DepartmentTask> findTasksByEmployee(String employeeName) { // Búsqueda por empleado - O(1)
        return tasksByEmployee.getOrDefault(employeeName, new ArrayList<>()); // Retornar lista o lista vacía - O(1)
    } // Fin del método findTasksByEmployee
    
    public List<DepartmentTask> findTasksByDepartment(String department) { // Búsqueda por departamento - O(1)
        return tasksByDepartment.getOrDefault(department, new ArrayList<>()); // Retornar lista o lista vacía - O(1)
    } // Fin del método findTasksByDepartment
    
    public List<DepartmentTask> findTasksByStatus(String status) { // Búsqueda por estado - O(1)
        return tasksByStatus.getOrDefault(status, new ArrayList<>()); // Retornar lista o lista vacía - O(1)
    } // Fin del método findTasksByStatus
    
    public List<DepartmentTask> findTasksByTimeRange(int maxHours) { // Búsqueda por rango de tiempo - O(1)
        int category = categorizeTime(maxHours); // Determinar categoría
        return tasksByEstimatedTime.getOrDefault(category, new ArrayList<>()); // Retornar lista de esa categoría - O(1)
    } // Fin del método findTasksByTimeRange
    
    public Employee findEmployeeByName(String name) { // Búsqueda rápida de empleado - O(1)
        return employeesByName.get(name); // Retornar empleado directamente - O(1)
    } // Fin del método findEmployeeByName
    
    // MÉTODO PARA ASIGNAR TAREA A EMPLEADO (OPERACIÓN HASH TABLE)
    public boolean assignTaskToEmployee(int taskId, Employee employee) { // Asignar tarea a empleado usando HashMap - O(1)
        DepartmentTask task = findTaskById(taskId); // Buscar tarea por ID - O(1)
        if (task == null) { // Si la tarea no existe
            System.out.println("Error: Tarea con ID " + taskId + " no encontrada"); // Mensaje de error
            return false; // Indicar falla
        }
        
        if (employee == null) { // Si el empleado es null
            System.out.println("Error: Empleado no puede ser null"); // Mensaje de error
            return false; // Indicar falla
        }
        
        // Remover tarea del empleado anterior si existía
        if (task.getAssignedEmployee() != null) { // Si ya había empleado asignado
            String oldEmployeeName = task.getAssignedEmployee().getName(); // Obtener nombre anterior
            List<DepartmentTask> oldEmployeeTasks = tasksByEmployee.get(oldEmployeeName); // Obtener lista del empleado anterior
            if (oldEmployeeTasks != null) { // Si la lista existe
                oldEmployeeTasks.remove(task); // Remover tarea de la lista anterior
            }
        }
        
        // Asignar nuevo empleado
        task.assignToEmployee(employee); // Asignar empleado a la tarea
        String employeeName = employee.getName(); // Obtener nombre del nuevo empleado
        tasksByEmployee.computeIfAbsent(employeeName, k -> new ArrayList<>()).add(task); // Agregar tarea al nuevo empleado - O(1)
        employeesByName.put(employeeName, employee); // Actualizar HashMap de empleados - O(1)
        
        System.out.println("Tarea ID " + taskId + " asignada exitosamente a " + employeeName); // Mensaje de confirmación
        return true; // Indicar éxito
    } // Fin del método assignTaskToEmployee
    
    // MÉTODO PARA ACTUALIZAR ESTADO DE TAREA (HASH TABLE UPDATE)
    public boolean updateTaskStatus(int taskId, String newStatus) { // Actualizar estado usando HashMap - O(1)
        DepartmentTask task = findTaskById(taskId); // Buscar tarea - O(1)
        if (task == null) { // Si no existe
            System.out.println("Error: Tarea con ID " + taskId + " no encontrada"); // Mensaje de error
            return false; // Indicar falla
        }
        
        // Remover de HashMap de estado anterior
        String oldStatus = task.getStatus(); // Obtener estado anterior
        List<DepartmentTask> oldStatusTasks = tasksByStatus.get(oldStatus); // Obtener lista del estado anterior
        if (oldStatusTasks != null) { // Si existe la lista
            oldStatusTasks.remove(task); // Remover de estado anterior
        }
        
        // Actualizar estado y agregar al nuevo HashMap
        task.setStatus(newStatus); // Actualizar estado de la tarea
        tasksByStatus.computeIfAbsent(newStatus, k -> new ArrayList<>()).add(task); // Agregar al nuevo estado - O(1)
        
        System.out.println("Estado de tarea ID " + taskId + " actualizado a: " + newStatus); // Mensaje de confirmación
        return true; // Indicar éxito
    } // Fin del método updateTaskStatus
    
    // MÉTODOS DE ESTADÍSTICAS USANDO HASH TABLES
    public int getTotalTasks() { // Obtener total de tareas - O(1)
        return totalTasks; // Retornar contador
    } // Fin del método getTotalTasks
    
    public int getTaskCountByStatus(String status) { // Contar tareas por estado - O(1)
        List<DepartmentTask> statusTasks = tasksByStatus.get(status); // Obtener lista del estado
        return statusTasks != null ? statusTasks.size() : 0; // Retornar tamaño o 0
    } // Fin del método getTaskCountByStatus
    
    public int getTaskCountByDepartment(String department) { // Contar tareas por departamento - O(1)
        List<DepartmentTask> deptTasks = tasksByDepartment.get(department); // Obtener lista del departamento
        return deptTasks != null ? deptTasks.size() : 0; // Retornar tamaño o 0
    } // Fin del método getTaskCountByDepartment
    
    public int getTaskCountByEmployee(String employeeName) { // Contar tareas por empleado - O(1)
        List<DepartmentTask> empTasks = tasksByEmployee.get(employeeName); // Obtener lista del empleado
        return empTasks != null ? empTasks.size() : 0; // Retornar tamaño o 0
    } // Fin del método getTaskCountByEmployee
    
    // MÉTODO PARA OBTENER TODOS LOS DEPARTAMENTOS
    public Set<String> getAllDepartments() { // Obtener todos los departamentos - O(1)
        return tasksByDepartment.keySet(); // Retornar conjunto de keys del HashMap
    } // Fin del método getAllDepartments
    
    // MÉTODO PARA OBTENER TODOS LOS EMPLEADOS
    public Set<String> getAllEmployeeNames() { // Obtener todos los nombres de empleados - O(1)
        return tasksByEmployee.keySet(); // Retornar conjunto de keys del HashMap
    } // Fin del método getAllEmployeeNames
    
    // MÉTODO PARA OBTENER TODOS LOS ESTADOS
    public Set<String> getAllStatuses() { // Obtener todos los estados - O(1)
        return tasksByStatus.keySet(); // Retornar conjunto de keys del HashMap
    } // Fin del método getAllStatuses
    
    // MÉTODO PARA REMOVER TAREA (HASH TABLE DELETION)
    public boolean removeTask(int taskId) { // Remover tarea del sistema - O(1) average
        DepartmentTask task = findTaskById(taskId); // Buscar tarea - O(1)
        if (task == null) { // Si no existe
            System.out.println("Error: Tarea con ID " + taskId + " no encontrada"); // Mensaje de error
            return false; // Indicar falla
        }
        
        // Remover de todos los HashMaps
        taskById.remove(taskId); // Remover del HashMap principal - O(1)
        
        // Remover de HashMap por departamento
        String department = task.getDepartment();
        List<DepartmentTask> deptTasks = tasksByDepartment.get(department);
        if (deptTasks != null) deptTasks.remove(task);
        
        // Remover de HashMap por estado
        String status = task.getStatus();
        List<DepartmentTask> statusTasks = tasksByStatus.get(status);
        if (statusTasks != null) statusTasks.remove(task);
        
        // Remover de HashMap por tiempo
        int timeCategory = categorizeTime(task.getEstimatedHours());
        tasksByEstimatedTime.get(timeCategory).remove(task);
        
        // Remover de HashMap por empleado si está asignada
        if (task.getAssignedEmployee() != null) {
            String employeeName = task.getAssignedEmployee().getName();
            List<DepartmentTask> empTasks = tasksByEmployee.get(employeeName);
            if (empTasks != null) {
                empTasks.remove(task);
                if (empTasks.isEmpty()) { // Si no quedan tareas para este empleado
                    tasksByEmployee.remove(employeeName); // Remover entrada del HashMap
                    employeesByName.remove(employeeName); // Remover empleado también
                }
            }
        }
        
        totalTasks--; // Decrementar contador
        System.out.println("Tarea ID " + taskId + " removida exitosamente del sistema"); // Mensaje de confirmación
        return true; // Indicar éxito
    } // Fin del método removeTask
    
    // MÉTODO PARA GENERAR REPORTE DE ESTADÍSTICAS
    public String generateHashTableStatistics() { // Generar reporte completo de estadísticas del HashMap
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DEL SISTEMA HASH TABLE ===\n");
        stats.append("Total de tareas en el sistema: ").append(totalTasks).append("\n");
        stats.append("Próximo ID disponible: ").append(nextTaskId).append("\n\n");
        
        // Estadísticas por estado
        stats.append("--- TAREAS POR ESTADO ---\n");
        for (String status : getAllStatuses()) {
            stats.append(status).append(": ").append(getTaskCountByStatus(status)).append(" tareas\n");
        }
        
        // Estadísticas por departamento
        stats.append("\n--- TAREAS POR DEPARTAMENTO ---\n");
        for (String dept : getAllDepartments()) {
            stats.append(dept).append(": ").append(getTaskCountByDepartment(dept)).append(" tareas\n");
        }
        
        // Estadísticas por empleado
        stats.append("\n--- TAREAS POR EMPLEADO ---\n");
        for (String emp : getAllEmployeeNames()) {
            stats.append(emp).append(": ").append(getTaskCountByEmployee(emp)).append(" tareas\n");
        }
        
        // Estadísticas de hash table performance
        stats.append("\n--- INFORMACIÓN TÉCNICA HASH TABLE ---\n");
        stats.append("Buckets en taskById: ").append(taskById.size()).append("\n");
        stats.append("Buckets en tasksByEmployee: ").append(tasksByEmployee.size()).append("\n");
        stats.append("Buckets en tasksByDepartment: ").append(tasksByDepartment.size()).append("\n");
        stats.append("Buckets en tasksByStatus: ").append(tasksByStatus.size()).append("\n");
        
        return stats.toString(); // Retornar reporte completo
    } // Fin del método generateHashTableStatistics
    
    // MÉTODO PARA LIMPIAR TODOS LOS DATOS
    public void clearAllData() { // Limpiar todos los HashMaps - O(1)
        taskById.clear(); // Limpiar HashMap principal
        tasksByEmployee.clear(); // Limpiar HashMap por empleado
        tasksByDepartment.clear(); // Limpiar HashMap por departamento
        tasksByStatus.clear(); // Limpiar HashMap por estado
        tasksByEstimatedTime.clear(); // Limpiar HashMap por tiempo
        employeesByName.clear(); // Limpiar HashMap de empleados
        nextTaskId = 1; // Reiniciar contador de IDs
        totalTasks = 0; // Reiniciar contador total
        initializeCommonCategories(); // Reinicializar categorías
        System.out.println("Todos los datos del sistema HashMap han sido limpiados"); // Mensaje de confirmación
    } // Fin del método clearAllData
    
} // Fin de la clase TaskAssignmentHashMap - IMPLEMENTACIÓN COMPLETA DEL PUNTO 5