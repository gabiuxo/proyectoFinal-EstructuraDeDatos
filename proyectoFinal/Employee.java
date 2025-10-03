//RECURSIVIDAD
// Clase que define empleados con jerarquía organizacional y métodos recursivos para contar subordinados

package proyectoFinal; 

import java.util.ArrayList; 
import java.util.List; 

public class Employee { // Clase que define la estructura de un empleado
    // ATRIBUTOS del empleado
    private int id; 
    private String name; 
    private String department; 
    private String position; 
    private double score; 
    private int assignedTasks; 
    private Employee leader; 
    private List<Employee> subordinates; 
    
    // CONSTRUCTOR
    public Employee(int id, String name, String department, String position, double score) {
        this.id = id; 
        this.name = name; 
        this.department = department; 
        this.position = position; 
        this.score = score; 
        this.assignedTasks = 0; 
        this.leader = null; 
        this.subordinates = new ArrayList<>(); 
    }
    
    // MÉTODOS GETTER
    public int getId() { return id; } // Obtener ID del empleado
    public String getName() { return name; } // Obtener nombre del empleado
    public String getDepartment() { return department; } // Obtener departamento
    public String getPosition() { return position; } // Obtener posición
    public double getScore() { return score; } // Obtener puntuación
    public int getAssignedTasks() { return assignedTasks; } // Obtener cantidad de tareas
    public Employee getLeader() { return leader; } // Obtener líder directo
    public List<Employee> getSubordinates() { return subordinates; } // Obtener lista de subordinados
    
    // MÉTODOS SETTER
    public void setName(String name) { this.name = name; } // Cambiar nombre
    public void setDepartment(String department) { this.department = department; } // Cambiar departamento
    public void setPosition(String position) { this.position = position; } // Cambiar posición
    public void setScore(double score) { this.score = score; } // Cambiar puntuación
    public void setAssignedTasks(int assignedTasks) { this.assignedTasks = assignedTasks; } // Cambiar tareas asignadas
    
    // MÉTODOS PARA MANEJO DE JERARQUÍA
    public void setLeader(Employee leader) { // Asignar un líder
        this.leader = leader; // Asignar líder
        if (leader != null && !leader.getSubordinates().contains(this)) { // Si el líder existe y aún no tiene este empleado como subordinado
            leader.addSubordinate(this); // Agregar este empleado a la lista de subordinados del líder
        }
    }
    
    public void addSubordinate(Employee subordinate) { // Agregar un subordinado
        if (!subordinates.contains(subordinate)) { // Si el subordinado no está ya en la lista
            subordinates.add(subordinate); // Agregar a la lista de subordinados
            subordinate.leader = this; // Establecer este empleado como líder del subordinado
        }
    }
    
    public void removeSubordinate(Employee subordinate) { // Remover un subordinado
        subordinates.remove(subordinate); // Quitar de la lista de subordinados
        if (subordinate.getLeader() == this) { // Si este empleado es el líder del subordinado
            subordinate.leader = null; // Quitar la referencia de líder
        }
    }
    
    // MÉTODOS DE UTILIDAD
    public void incrementTasks() { // Incrementar contador de tareas asignadas
        this.assignedTasks++; // Aumentar en 1 el contador
    }
    
    public void decrementTasks() { // Decrementar contador de tareas asignadas
        if (this.assignedTasks > 0) { // Solo si tiene tareas asignadas
            this.assignedTasks--; // Disminuir en 1 el contador
        }
    }
    
    public boolean hasLeader() { // Verificar si tiene líder
        return leader != null; // Retornar true si tiene líder
    }
    
    public boolean hasSubordinates() { // Verificar si tiene subordinados
        return !subordinates.isEmpty(); // Retornar true si la lista no está vacía
    }
    
    public int getSubordinateCount() { // Obtener cantidad de subordinados
        return subordinates.size(); // Retornar tamaño de la lista
    }
    
    // MÉTODO RECURSIVO para contar todos los subordinados (directos e indirectos)
    public int getTotalSubordinatesCount() { // Contar recursivamente todos los subordinados
        int total = subordinates.size(); // Contar subordinados directos
        for (Employee subordinate : subordinates) { // Para cada subordinado directo
            total += subordinate.getTotalSubordinatesCount(); // Sumar recursivamente sus subordinados
        }
        return total; // Retornar total de subordinados en toda la jerarquía
    }
    
    // MÉTODO toString para representación como cadena
    @Override
    public String toString() {
        return String.format("Employee[ID: %d, Name: %s, Dept: %s, Position: %s, Score: %.1f, Tasks: %d]",
                id, name, department, position, score, assignedTasks); // Formatear información básica
    }
    
    // MÉTODO para obtener información detallada incluyendo jerarquía
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder(); // Crear StringBuilder para construir el texto
        info.append("=== INFORMACIÓN DEL EMPLEADO ===\n"); // Título
        info.append("ID: ").append(id).append("\n"); // Agregar ID
        info.append("Nombre: ").append(name).append("\n"); // Agregar nombre
        info.append("Departamento: ").append(department).append("\n"); // Agregar departamento
        info.append("Posición: ").append(position).append("\n"); // Agregar posición
        info.append("Puntuación: ").append(score).append("/100\n"); // Agregar puntuación
        info.append("Tareas asignadas: ").append(assignedTasks).append("\n"); // Agregar tareas
        
        // Información del líder
        if (hasLeader()) { // Si tiene líder
            info.append("Líder: ").append(leader.getName()).append(" (ID: ").append(leader.getId()).append(")\n");
        } else { // Si no tiene líder
            info.append("Líder: Sin líder asignado\n");
        }
        
        // Información de subordinados
        info.append("Subordinados directos: ").append(getSubordinateCount()).append("\n"); // Cantidad directa
        if (hasSubordinates()) { // Si tiene subordinados
            info.append("Lista de subordinados:\n");
            for (Employee sub : subordinates) { // Para cada subordinado
                info.append("  - ").append(sub.getName()).append(" (ID: ").append(sub.getId()).append(")\n");
            }
        }
        info.append("Total subordinados (incluye indirectos): ").append(getTotalSubordinatesCount()).append("\n");
        
        return info.toString(); // Retornar toda la información como cadena
    }
}