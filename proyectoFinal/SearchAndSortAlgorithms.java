package proyectoFinal;

public class SearchAndSortAlgorithms {
    
    public static Employee sequentialSearchById(Employee[] employees, int targetId) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId() == targetId) {
                return employees[i];
            }
        }
        return null;
    }
    
    public static Employee binarySearchById(Employee[] employees, int targetId) {
        int left = 0;
        int right = employees.length - 1;
        
        while (left <= right) {
            int middle = left + (right - left) / 2;
            
            if (employees[middle] != null && employees[middle].getId() == targetId) {
                return employees[middle];
            }
            
            if (employees[middle] != null && employees[middle].getId() < targetId) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        
        return null;
    }

    public static void bubbleSortEmployeesById(Employee[] employees) {
        int n = employees.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (employees[j] == null || 
                   (employees[j + 1] != null && employees[j].getId() > employees[j + 1].getId())) {
                    
                    Employee temp = employees[j];
                    employees[j] = employees[j + 1];
                    employees[j + 1] = temp;
                }
            }
        }
    }
    
    public static void selectionSortEmployeesByScore(Employee[] employees) {
        int n = employees.length;
        
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            
            for (int j = i + 1; j < n; j++) {
                if (employees[j] != null && employees[maxIndex] != null &&
                    employees[j].getScore() > employees[maxIndex].getScore()) {
                    maxIndex = j;
                }
            }
            
            if (maxIndex != i) {
                Employee temp = employees[i];
                employees[i] = employees[maxIndex];
                employees[maxIndex] = temp;
            }
        }
    }
    
    public static String demonstrateSearchAlgorithms(Employee[] employees) {
        StringBuilder demo = new StringBuilder();
        demo.append("=== ALGORITMOS DE BÚSQUEDA ===\n\n");
        
        if (employees.length > 0 && employees[0] != null) {
            Employee found = sequentialSearchById(employees, employees[0].getId());
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
        
        if (employees.length > 0) {
            demo.append("Empleados disponibles para ordenamiento.\n");
        } else {
            demo.append("No hay empleados para demostrar ordenamientos.\n");
        }
        
        return demo.toString();
    }
}
