//RECURSIVIDAD
// Clase que define tareas de departamento con métodos recursivos para cálculos de tiempo y manejo de subtareas

package proyectoFinal;

import java.util.ArrayList; 
import java.util.List; 

public class DepartmentTask { 
    // ATRIBUTOS BÁSICOS (variables de la clase)
    private static int nextId = 1; // Variable estática para generar IDs únicos
    private int taskId; // ID único para cada tarea
    private String taskDescription; // Variable privada para almacenar la descripción de la tarea
    private String department; // Variable privada para almacenar el nombre del departamento responsable
    private String status; // Variable privada para almacenar el estado de la tarea (Pendiente, En Proceso, Completada)
    private int estimatedHours; // Tiempo estimado en horas para completar la tarea
    private Employee assignedEmployee; // Empleado asignado a esta tarea (puede ser null)
    private DepartmentTask parentTask; // Tarea padre (para crear jerarquías)
    private List<DepartmentTask> subtasks; // Lista de subtareas para aplicar recursión

    // CONSTRUCTOR de la clase DepartmentTask
    public DepartmentTask(String taskDescription, String department, String status, int estimatedHours) { 
        this.taskId = nextId++; // Asignar ID único y luego incrementar para la siguiente tarea
        this.taskDescription = taskDescription; // Asignar la descripción recibida al atributo taskDescription de esta instancia
        this.department = department; // Asignar el departamento recibido al atributo department de esta instancia
        this.status = status; // Asignar el estado recibido al atributo status de esta instancia
        this.estimatedHours = estimatedHours; // Asignar el tiempo estimado recibido al atributo estimatedHours
        this.assignedEmployee = null; // Inicialmente sin empleado asignado
        this.parentTask = null; // Inicialmente sin tarea padre
        this.subtasks = new ArrayList<>(); // Inicializar lista de subtareas vacía
    } 
    
    // CONSTRUCTOR SOBRECARGADO para compatibilidad con código existente
    public DepartmentTask(String taskDescription, String department, String status) { // Constructor que recibe solo descripción, departamento y estado
        this(taskDescription, department, status, 8); // Llamar al constructor principal con 8 horas por defecto
    } // Fin del constructor sobrecargado

    // MÉTODOS GETTER de la clase DepartmentTask
    public int getTaskId() { // NUEVO: Método público para obtener el ID de la tarea
        return taskId; // Retornar el valor del atributo taskId
    } // Fin del método getTaskId

    public String getTaskDescription() { // Método público para obtener la descripción de la tarea
        return taskDescription; // Retornar el valor del atributo taskDescription
    } // Fin del método getTaskDescription

    public String getDepartment() { // Método público para obtener el departamento de la tarea
        return department; // Retornar el valor del atributo department
    } // Fin del método getDepartment

    public String getStatus() { // Método público para obtener el estado de la tarea
        return status; // Retornar el valor del atributo status
    } // Fin del método getStatus
    
    public int getEstimatedHours() { // NUEVO: Método público para obtener el tiempo estimado
        return estimatedHours; // Retornar el valor del atributo estimatedHours
    } // Fin del método getEstimatedHours
    
    public Employee getAssignedEmployee() { // NUEVO: Método público para obtener el empleado asignado
        return assignedEmployee; // Retornar el empleado asignado (puede ser null)
    } // Fin del método getAssignedEmployee
    
    public DepartmentTask getParentTask() { // NUEVO: Método público para obtener la tarea padre
        return parentTask; // Retornar la tarea padre (puede ser null si es tarea raíz)
    } // Fin del método getParentTask
    
    public List<DepartmentTask> getSubtasks() { // NUEVO: Método público para obtener la lista de subtareas
        return subtasks; // Retornar la lista de subtareas
    } // Fin del método getSubtasks

    // MÉTODOS SETTER de la clase DepartmentTask
    public void setTaskDescription(String taskDescription) { // Método público para cambiar la descripción de la tarea
        this.taskDescription = taskDescription; // Asignar la nueva descripción al atributo taskDescription de esta instancia
    } // Fin del método setTaskDescription

    public void setDepartment(String department) { // Método público para cambiar el departamento de la tarea
        this.department = department; // Asignar el nuevo departamento al atributo department de esta instancia
    } // Fin del método setDepartment

    public void setStatus(String status) { // Método público para cambiar el estado de la tarea
        this.status = status; // Asignar el nuevo estado al atributo status de esta instancia
    } // Fin del método setStatus
    
    public void setEstimatedHours(int estimatedHours) { // NUEVO: Método público para cambiar el tiempo estimado
        if (estimatedHours > 0) { // Validar que el tiempo sea positivo
            this.estimatedHours = estimatedHours; // Asignar el nuevo tiempo estimado
        }
    } // Fin del método setEstimatedHours

    // MÉTODOS PARA GESTIÓN DE EMPLEADOS Y SUBTAREAS
    public void assignToEmployee(Employee employee) { // NUEVO: Método para asignar empleado a la tarea
        if (this.assignedEmployee != null) { // Si ya había un empleado asignado
            this.assignedEmployee.decrementTasks(); // Decrementar sus tareas asignadas
        }
        this.assignedEmployee = employee; // Asignar nuevo empleado
        if (employee != null) { // Si el nuevo empleado no es null
            employee.incrementTasks(); // Incrementar las tareas asignadas al empleado
        }
    } // Fin del método assignToEmployee
    
    public void addSubtask(DepartmentTask subtask) { // NUEVO: Método para agregar subtarea
        if (subtask != null && !subtasks.contains(subtask)) { // Verificar que la subtarea sea válida y no esté duplicada
            subtasks.add(subtask); // Agregar subtarea a la lista
            subtask.parentTask = this; // Establecer esta tarea como padre de la subtarea
        }
    } // Fin del método addSubtask
    
    public void removeSubtask(DepartmentTask subtask) { // NUEVO: Método para remover subtarea
        subtasks.remove(subtask); // Remover subtarea de la lista
        if (subtask != null) { // Si la subtarea existe
            subtask.parentTask = null; // Quitar referencia al padre
        }
    } // Fin del método removeSubtask
    
    // MÉTODOS RECURSIVOS PARA ESTADÍSTICAS DE TIEMPO (PUNTO 4 DEL REQUERIMIENTO)
    public int calculateTotalEstimatedTime() { // MÉTODO RECURSIVO: Calcular tiempo total estimado incluyendo subtareas
        int totalTime = this.estimatedHours; // Comenzar con el tiempo de esta tarea
        
        // Sumar recursivamente el tiempo de todas las subtareas
        for (DepartmentTask subtask : subtasks) { // Para cada subtarea en la lista
            totalTime += subtask.calculateTotalEstimatedTime(); // Llamada recursiva para sumar tiempo de subtarea
        }
        
        return totalTime; // Retornar tiempo total calculado recursivamente
    } // Fin del método calculateTotalEstimatedTime
    
    public int countAllTasks() { // MÉTODO RECURSIVO: Contar total de tareas incluyendo subtareas
        int count = 1; // Contar esta tarea (caso base)
        
        // Contar recursivamente todas las subtareas
        for (DepartmentTask subtask : subtasks) { // Para cada subtarea en la lista
            count += subtask.countAllTasks(); // Llamada recursiva para contar subtareas
        }
        
        return count; // Retornar conteo total de tareas
    } // Fin del método countAllTasks
    
    public int calculateRemainingTime() { // MÉTODO RECURSIVO: Calcular tiempo restante (solo tareas no completadas)
        int remainingTime = 0; // Inicializar tiempo restante
        
        // Si esta tarea no está completada, contar su tiempo
        if (!"Completada".equals(this.status)) { // Si el estado no es "Completada"
            remainingTime += this.estimatedHours; // Agregar tiempo de esta tarea
        }
        
        // Sumar recursivamente tiempo restante de subtareas
        for (DepartmentTask subtask : subtasks) { // Para cada subtarea
            remainingTime += subtask.calculateRemainingTime(); // Llamada recursiva
        }
        
        return remainingTime; // Retornar tiempo restante total
    } // Fin del método calculateRemainingTime
    
    public double calculateCompletionPercentage() { // MÉTODO RECURSIVO: Calcular porcentaje de completado
        int completedTasks = 0; // Contador de tareas completadas
        int totalTasks = countAllTasks(); // Total de tareas (usando método recursivo)
        
        // Verificar si esta tarea está completada
        if ("Completada".equals(this.status)) {
            completedTasks = 1; // Esta tarea está completa
        }
        
        // Contar recursivamente tareas completadas en subtareas
        for (DepartmentTask subtask : subtasks) {
            completedTasks += (int)(subtask.calculateCompletionPercentage() * subtask.countAllTasks() / 100.0);
        }
        
        return totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0.0; // Retornar porcentaje
    } // Fin del método calculateCompletionPercentage
    
    // MÉTODO PARA REPRESENTACIÓN EN CADENA (toString es un método especial de Java)
    @Override // Anotación que indica que estamos sobreescribiendo un método de la clase padre (Object)
    public String toString() { // Método público que convierte la tarea a una representación de cadena MEJORADA
        String employeeInfo = (assignedEmployee != null) ? " | Empleado: " + assignedEmployee.getName() : " | Sin asignar";
        return taskDescription + " | Depto: " + department + " | Estado: " + status + 
               " | Tiempo: " + estimatedHours + "h" + employeeInfo; // Incluir información de tiempo y empleado
    } // Fin del método toString

    // MÉTODO PARA OBTENER INFORMACIÓN COMPLETA (más detallada que toString)
    public String getFullInfo() { // Método público para obtener toda la información de la tarea en formato detallado CON RECURSIÓN
        StringBuilder info = new StringBuilder(); // Usar StringBuilder para construir información completa
        info.append("=== INFORMACIÓN DETALLADA DE TAREA DEPARTAMENTAL ===\n");
        info.append("Descripción: ").append(taskDescription).append("\n"); // Descripción de la tarea
        info.append("Departamento: ").append(department).append("\n"); // Departamento responsable
        info.append("Estado: ").append(status).append("\n"); // Estado actual
        info.append("Tiempo estimado: ").append(estimatedHours).append(" horas\n"); // Tiempo estimado
        info.append("Empleado asignado: ").append(assignedEmployee != null ? assignedEmployee.getName() : "Sin asignar").append("\n");
                
// INFORMACIÓN RECURSIVA DE ESTADÍSTICAS
        info.append("\n=== ESTADÍSTICAS RECURSIVAS ===\n"); // Agregar título de sección de estadísticas
        info.append("Tiempo total (con subtareas): ").append(calculateTotalEstimatedTime()).append(" horas\n"); // Mostrar tiempo calculado recursivamente
        info.append("Total de tareas (con subtareas): ").append(countAllTasks()).append("\n"); // Mostrar conteo total recursivo
        info.append("Tiempo restante: ").append(calculateRemainingTime()).append(" horas\n"); // Mostrar tiempo pendiente recursivo
        info.append("Progreso completado: ").append(String.format("%.1f", calculateCompletionPercentage())).append("%\n"); // Mostrar porcentaje con 1 decimal
        
        // INFORMACIÓN DE SUBTAREAS
        if (!subtasks.isEmpty()) { // Si tiene subtareas
            info.append("\n=== SUBTAREAS (").append(subtasks.size()).append(") ===\n"); // Título con cantidad de subtareas
            for (DepartmentTask subtask : subtasks) { // Recorrer cada subtarea
                info.append("  • ").append(subtask.toString()).append("\n"); // Agregar subtarea con viñeta
            }
        }
        
        return info.toString(); // Retornar información completa con estadísticas recursivas
    }
}
