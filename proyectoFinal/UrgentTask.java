//COLAS PRIORIZADAS CON HEAP (SIN LÍMITE DE PRIORIDADES)
// Clase que representa tareas urgentes con prioridad para usar en cola de prioridad
package proyectoFinal; 

public class UrgentTask {
    // ATRIBUTOS 
    private String description; 
    private int priority; 
    private String dueDate; 
    private String department; 

    // CONSTRUCTOR COMPLETO
    public UrgentTask(String description, int priority, String dueDate) { 
        this.description = description; 
        this.priority = priority; 
        this.dueDate = dueDate; 
        this.department = "General"; // Departamento por defecto
    } 

    // CONSTRUCTOR CON DEPARTAMENTO
    public UrgentTask(String description, int priority, String dueDate, String department) { 
        this.description = description; 
        this.priority = priority; 
        this.dueDate = dueDate; 
        this.department = department; 
    } 

    // CONSTRUCTOR SIMPLE (para compatibilidad)
    public UrgentTask(String description) { 
        this.description = description; 
        this.priority = 5; // Prioridad media por defecto
        this.dueDate = "Sin fecha"; 
        this.department = "General"; 
    } 

    // MÉTODOS GETTER
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public String getDueDate() { return dueDate; }
    public String getDepartment() { return department; }
    public String getTaskDescription() { return description; } // Para compatibilidad

    // MÉTODOS SETTER
    public void setDescription(String description) { this.description = description; }
    public void setPriority(int priority) { 
        if (priority >= 1 && priority <= 10) {
            this.priority = priority; 
        }
    }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setDepartment(String department) { this.department = department; }

    // MÉTODO toString
    @Override
    public String toString() { 
        return description + " [Prioridad: " + priority + ", Fecha: " + dueDate + ", Depto: " + department + "]"; 
    } 
} // Fin de la clase UrgentTask
