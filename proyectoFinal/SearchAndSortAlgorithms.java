//ORDENAMIENTO Y BÚSQUEDA
// Algoritmos clásicos de búsqueda (secuencial, binaria) y ordenamiento (burbuja, selección) para empleados

package proyectoFinal; 

public class SearchAndSortAlgorithms {
    
    // ALGORITMOS DE BÚSQUEDA

    public static Employee sequentialSearchById(Employee[] employees, int targetId) {
        for (int i = 0; i < employees.length; i++) { // Recorrer el array
            if (employees[i] != null && employees[i].getId() == targetId) { // Si encontramos el ID
                return employees[i]; // Retornar el empleado
            }
        }
        return null; // No se encontró
    }
    
    public static Employee binarySearchById(Employee[] employees, int targetId) {
        int left = 0; // Inicio del array
        int right = employees.length - 1; // Final del array
        
        while (left <= right) { // Mientras haya elementos que revisar
            int middle = left + (right - left) / 2; // Encontrar la mitad
            
            if (employees[middle] != null && employees[middle].getId() == targetId) { // Si es el que buscamos
                return employees[middle]; // Lo encontramos
            }
            
            if (employees[middle] != null && employees[middle].getId() < targetId) { // Si es menor
                left = middle + 1; // Buscar en la derecha
            } else { // Si es mayor
                right = middle - 1; // Buscar en la izquierda
            }
        }
        
        return null; // No se encontró
    }

    // ALGORITMOS DE ORDENAMIENTO
    
    public static void bubbleSortEmployeesById(Employee[] employees) {
        int n = employees.length; // Tamaño del array
        
        for (int i = 0; i < n - 1; i++) { // Para cada pasada
            for (int j = 0; j < n - i - 1; j++) { // Comparar elementos adyacentes
                if (employees[j] == null || 
                   (employees[j + 1] != null && employees[j].getId() > employees[j + 1].getId())) {
                    
                    // Intercambiar
                    Employee temp = employees[j]; 
                    employees[j] = employees[j + 1]; 
                    employees[j + 1] = temp; 
                }
            }
        }
    }
    
    public static void selectionSortEmployeesByScore(Employee[] employees) {
        int n = employees.length; // Tamaño del array
        
        for (int i = 0; i < n - 1; i++) { // Para cada posición
            int maxIndex = i; // Asumir que i es el máximo
            
            for (int j = i + 1; j < n; j++) { // Buscar el máximo en el resto
                if (employees[j] != null && employees[maxIndex] != null &&
                    employees[j].getScore() > employees[maxIndex].getScore()) {
                    maxIndex = j; // Nuevo máximo encontrado
                }
            }
            
            if (maxIndex != i) { // Si hay un máximo diferente
                Employee temp = employees[i]; // Intercambiar
                employees[i] = employees[maxIndex]; 
                employees[maxIndex] = temp; 
            }
        }
    }
    
    // MÉTODOS DE DEMOSTRACIÓN
    
    public static String demonstrateSearchAlgorithms(Employee[] employees) {
        StringBuilder demo = new StringBuilder(); 
        demo.append("=== ALGORITMOS DE BÚSQUEDA ===\n\n"); 
        
        if (employees.length > 0 && employees[0] != null) { // Si hay empleados
            Employee found = sequentialSearchById(employees, employees[0].getId()); // Buscar el primero
            demo.append("Búsqueda secuencial: "); 
            demo.append(found != null ? found.getName() : "No encontrado").append("\n"); 
        } else { 
            demo.append("No hay empleados para demostrar búsquedas.\n"); 
        }
        
        return demo.toString(); 
    }
    
    public static String demonstrateSortingAlgorithms(Employee[] employees) {
        StringBuilder demo = new StringBuilder(); 
        demo.append("=== ALGORITMOS DE ORDENAMIENTO ===\n\n"); 
        
        if (employees.length > 0) { // Si hay empleados
            demo.append("Empleados disponibles para ordenamiento.\n"); 
        } else { 
            demo.append("No hay empleados para demostrar ordenamientos.\n"); 
        }
        
        return demo.toString(); 
    }
}
