//TABLAS HASHMAPS
// Sistema de gestión de empleados usando HashMap para búsquedas rápidas por ID, departamento y nombre

package proyectoFinal; // Declaración del paquete

import java.util.HashMap; // Para implementar tablas hash
import java.util.ArrayList; // Para listas dinámicas
import java.util.Map; // Para interfaces de mapas

/**
 * Sistema de gestión de empleados usando HashMap para búsquedas rápidas
 * Permite organizar empleados por departamento y realizar búsquedas O(1)
 */
public class EmployeeHashManager {
    
    // ATRIBUTOS principales
    private HashMap<Integer, Employee> employeeById; // Mapa ID -> Empleado
    private HashMap<String, ArrayList<Employee>> employeesByDepartment; // Mapa Departamento -> Lista de empleados
    private HashMap<String, Employee> employeeByName; // Mapa Nombre -> Empleado
    
    // CONSTRUCTOR
    public EmployeeHashManager() {
        employeeById = new HashMap<>(); // Inicializar mapa por ID
        employeesByDepartment = new HashMap<>(); // Inicializar mapa por departamento
        employeeByName = new HashMap<>(); // Inicializar mapa por nombre
        
        // Inicializar listas por departamento
        employeesByDepartment.put("IT", new ArrayList<>());
        employeesByDepartment.put("Ventas", new ArrayList<>());
        employeesByDepartment.put("Marketing", new ArrayList<>());
        employeesByDepartment.put("RRHH", new ArrayList<>());
        employeesByDepartment.put("Finanzas", new ArrayList<>());
    }
    
    // MÉTODO para agregar empleado al sistema
    public boolean addEmployee(Employee employee) {
        if (employee == null || employeeById.containsKey(employee.getId())) {
            return false; // No agregar si es null o ID ya existe
        }
        
        // Agregar a mapa por ID
        employeeById.put(employee.getId(), employee);
        
        // Agregar a mapa por departamento
        String department = employee.getDepartment();
        if (!employeesByDepartment.containsKey(department)) {
            employeesByDepartment.put(department, new ArrayList<>());
        }
        employeesByDepartment.get(department).add(employee);
        
        // Agregar a mapa por nombre
        employeeByName.put(employee.getName().toLowerCase(), employee);
        
        return true; // Empleado agregado exitosamente
    }
    
    // MÉTODO para buscar empleado por ID - O(1)
    public Employee findById(int id) {
        return employeeById.get(id); // Búsqueda directa en HashMap
    }
    
    // MÉTODO para buscar empleado por nombre - O(1)
    public Employee findByName(String name) {
        if (name == null) return null;
        return employeeByName.get(name.toLowerCase()); // Búsqueda insensible a mayúsculas
    }
    
    // MÉTODO para obtener empleados por departamento - O(1)
    public ArrayList<Employee> getEmployeesByDepartment(String department) {
        return employeesByDepartment.get(department);
    }
    
    // MÉTODO para eliminar empleado
    public boolean removeEmployee(int id) {
        Employee employee = employeeById.get(id);
        if (employee == null) {
            return false; // Empleado no encontrado
        }
        
        // Remover de todos los mapas
        employeeById.remove(id);
        employeesByDepartment.get(employee.getDepartment()).remove(employee);
        employeeByName.remove(employee.getName().toLowerCase());
        
        return true; // Empleado eliminado exitosamente
    }
    
    // MÉTODO para actualizar información de empleado
    public boolean updateEmployee(int id, String newName, String newDepartment, 
                                  String newPosition, double newPerformance) {
        Employee employee = employeeById.get(id);
        if (employee == null) {
            return false; // Empleado no encontrado
        }
        
        // Remover de mapas actuales (excepto ID)
        String oldDepartment = employee.getDepartment();
        String oldName = employee.getName();
        
        employeesByDepartment.get(oldDepartment).remove(employee);
        employeeByName.remove(oldName.toLowerCase());
        
        // Actualizar datos del empleado
        employee.setName(newName);
        employee.setDepartment(newDepartment);
        employee.setPosition(newPosition);
        employee.setScore(newPerformance);
        
        // Agregar a nuevos mapas
        if (!employeesByDepartment.containsKey(newDepartment)) {
            employeesByDepartment.put(newDepartment, new ArrayList<>());
        }
        employeesByDepartment.get(newDepartment).add(employee);
        employeeByName.put(newName.toLowerCase(), employee);
        
        return true; // Actualización exitosa
    }
    
    // MÉTODO para obtener estadísticas por departamento
    public String getDepartmentStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS POR DEPARTAMENTO ===\n");
        
        for (Map.Entry<String, ArrayList<Employee>> entry : employeesByDepartment.entrySet()) {
            String department = entry.getKey();
            ArrayList<Employee> employees = entry.getValue();
            
            if (!employees.isEmpty()) {
                stats.append("\n--- ").append(department).append(" ---\n");
                stats.append("Total empleados: ").append(employees.size()).append("\n");
                
                // Calcular promedio de desempeño
                double totalPerformance = 0;
                for (Employee emp : employees) {
                    totalPerformance += emp.getScore();
                }
                double avgPerformance = totalPerformance / employees.size();
                stats.append("Desempeño promedio: ").append(String.format("%.1f", avgPerformance)).append("\n");
                
                // Listar empleados
                for (Employee emp : employees) {
                    stats.append("  • ").append(emp.getName()).append(" (ID: ")
                          .append(emp.getId()).append(", Score: ")
                          .append(emp.getScore()).append(")\n");
                }
            }
        }
        
        return stats.toString();
    }
    
    // MÉTODO para buscar empleados por rango de desempeño
    public ArrayList<Employee> findByPerformanceRange(double minScore, double maxScore) {
        ArrayList<Employee> result = new ArrayList<>();
        
        for (Employee employee : employeeById.values()) {
            double score = employee.getScore();
            if (score >= minScore && score <= maxScore) {
                result.add(employee);
            }
        }
        
        return result;
    }
    
    // MÉTODO para obtener top performers por departamento
    public String getTopPerformersByDepartment() {
        StringBuilder result = new StringBuilder();
        result.append("=== TOP PERFORMERS POR DEPARTAMENTO ===\n");
        
        for (Map.Entry<String, ArrayList<Employee>> entry : employeesByDepartment.entrySet()) {
            String department = entry.getKey();
            ArrayList<Employee> employees = entry.getValue();
            
            if (!employees.isEmpty()) {
                // Encontrar el empleado con mayor desempeño
                Employee topPerformer = employees.get(0);
                for (Employee emp : employees) {
                    if (emp.getScore() > topPerformer.getScore()) {
                        topPerformer = emp;
                    }
                }
                
                result.append(department).append(": ")
                      .append(topPerformer.getName())
                      .append(" (Score: ").append(topPerformer.getScore()).append(")\n");
            }
        }
        
        return result.toString();
    }
    
    // MÉTODO para cargar empleados iniciales con más diversidad
    public void loadInitialEmployees() {
        // HashMap inicia vacío - sin empleados precargados
        // El usuario debe agregar todos los empleados manualmente
    }
    
    // MÉTODOS de utilidad
    public int getTotalEmployees() {
        return employeeById.size();
    }
    
    public boolean isEmpty() {
        return employeeById.isEmpty();
    }
    
    // MÉTODO para obtener todos los empleados
    public ArrayList<Employee> getAllEmployees() {
        return new ArrayList<>(employeeById.values());
    }
    
    // MÉTODO para obtener lista de departamentos
    public ArrayList<String> getDepartments() {
        ArrayList<String> departments = new ArrayList<>();
        for (String dept : employeesByDepartment.keySet()) {
            if (!employeesByDepartment.get(dept).isEmpty()) {
                departments.add(dept);
            }
        }
        return departments;
    }
    
    // MÉTODO para búsqueda avanzada
    public ArrayList<Employee> advancedSearch(String department, double minPerformance, String positionContains) {
        ArrayList<Employee> results = new ArrayList<>();
        
        ArrayList<Employee> deptEmployees = employeesByDepartment.get(department);
        if (deptEmployees != null) {
            for (Employee emp : deptEmployees) {
                boolean matchesPerformance = emp.getScore() >= minPerformance;
                boolean matchesPosition = positionContains == null || 
                                          emp.getPosition().toLowerCase().contains(positionContains.toLowerCase());
                
                if (matchesPerformance && matchesPosition) {
                    results.add(emp);
                }
            }
        }
        
        return results;
    }
    
    // MÉTODO para generar reporte completo del HashMap
    public String generateCompleteReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== REPORTE COMPLETO DEL SISTEMA HASH ===\n");
        report.append("Total de empleados: ").append(getTotalEmployees()).append("\n");
        report.append("Total de departamentos activos: ").append(getDepartments().size()).append("\n\n");
        
        report.append(getDepartmentStatistics()).append("\n");
        report.append(getTopPerformersByDepartment()).append("\n");
        
        // Estadísticas de rendimiento del HashMap
        report.append("=== ESTADÍSTICAS DE RENDIMIENTO ===\n");
        report.append("Capacidad inicial HashMap ID: ").append(employeeById.size()).append("\n");
        report.append("Load Factor aproximado: ").append(employeeById.size() / 16.0).append("\n");
        report.append("Colisiones estimadas: Mínimas (HashMap optimizado)\n");
        
        return report.toString();
    }
}