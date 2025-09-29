package proyectoFinal; // Declaración del paquete

/**
 * Implementación de Árbol Binario de Búsqueda para gestionar empleados
 * Organiza empleados por ID para búsquedas eficientes
 */
public class EmployeeBinaryTree { // Clase que implementa árbol binario de búsqueda
    
    // CLASE INTERNA para representar nodos del árbol
    private class TreeNode { // Nodo interno del árbol binario
        Employee employee; // Empleado almacenado en este nodo
        TreeNode left; // Referencia al hijo izquierdo
        TreeNode right; // Referencia al hijo derecho
        
        TreeNode(Employee employee) { // Constructor del nodo
            this.employee = employee; // Asignar empleado
            this.left = null; // Inicializar hijo izquierdo como null
            this.right = null; // Inicializar hijo derecho como null
        }
    }
    
    // ATRIBUTOS del árbol
    private TreeNode root; // Raíz del árbol
    private int size; // Cantidad de empleados en el árbol
    
    // CONSTRUCTOR
    public EmployeeBinaryTree() {
        this.root = null; // Inicializar árbol vacío
        this.size = 0; // Inicializar tamaño en 0
    }
    
    // MÉTODO público para insertar empleado
    public void insert(Employee employee) {
        root = insertRecursive(root, employee); // Llamar método recursivo
        size++; // Incrementar tamaño
    }
    
    // MÉTODO recursivo privado para insertar
    private TreeNode insertRecursive(TreeNode node, Employee employee) {
        // Caso base: si llegamos a una posición vacía
        if (node == null) { // Si el nodo es null
            return new TreeNode(employee); // Crear nuevo nodo con el empleado
        }
        
        // Comparar IDs para decidir dónde insertar
        if (employee.getId() < node.employee.getId()) { // Si el ID es menor
            node.left = insertRecursive(node.left, employee); // Insertar en subárbol izquierdo
        } else if (employee.getId() > node.employee.getId()) { // Si el ID es mayor
            node.right = insertRecursive(node.right, employee); // Insertar en subárbol derecho
        }
        // Si los IDs son iguales, no se inserta (no duplicados)
        
        return node; // Retornar el nodo actual
    }
    
    // MÉTODO público para buscar empleado por ID
    public Employee search(int employeeId) {
        return searchRecursive(root, employeeId); // Llamar método recursivo
    }
    
    // MÉTODO recursivo privado para buscar
    private Employee searchRecursive(TreeNode node, int employeeId) {
        // Caso base: nodo vacío o ID encontrado
        if (node == null) { // Si llegamos a null
            return null; // Empleado no encontrado
        }
        if (node.employee.getId() == employeeId) { // Si encontramos el ID
            return node.employee; // Retornar el empleado encontrado
        }
        
        // Buscar recursivamente en el subárbol apropiado
        if (employeeId < node.employee.getId()) { // Si el ID buscado es menor
            return searchRecursive(node.left, employeeId); // Buscar en subárbol izquierdo
        } else { // Si el ID buscado es mayor
            return searchRecursive(node.right, employeeId); // Buscar en subárbol derecho
        }
    }
    
    // MÉTODO público para eliminar empleado
    public boolean delete(int employeeId) {
        int initialSize = size; // Guardar tamaño inicial
        root = deleteRecursive(root, employeeId); // Llamar método recursivo
        return size < initialSize; // Retornar true si se eliminó algo
    }
    
    // MÉTODO recursivo privado para eliminar
    private TreeNode deleteRecursive(TreeNode node, int employeeId) {
        // Caso base: nodo vacío
        if (node == null) { // Si llegamos a null
            return null; // No hay nada que eliminar
        }
        
        // Buscar el nodo a eliminar
        if (employeeId < node.employee.getId()) { // Si el ID es menor
            node.left = deleteRecursive(node.left, employeeId); // Buscar en subárbol izquierdo
        } else if (employeeId > node.employee.getId()) { // Si el ID es mayor
            node.right = deleteRecursive(node.right, employeeId); // Buscar en subárbol derecho
        } else { // Si encontramos el nodo a eliminar
            size--; // Decrementar tamaño
            
            // Casos de eliminación
            if (node.left == null) { // Si no tiene hijo izquierdo
                return node.right; // Retornar hijo derecho
            } else if (node.right == null) { // Si no tiene hijo derecho
                return node.left; // Retornar hijo izquierdo
            } else { // Si tiene ambos hijos
                // Encontrar el sucesor inmediato (menor del subárbol derecho)
                TreeNode successor = findMin(node.right); // Buscar mínimo en subárbol derecho
                node.employee = successor.employee; // Reemplazar empleado
                node.right = deleteRecursive(node.right, successor.employee.getId()); // Eliminar sucesor
                size++; // Compensar el decremento extra
            }
        }
        
        return node; // Retornar nodo actualizado
    }
    
    // MÉTODO auxiliar para encontrar el nodo mínimo
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) { // Mientras tenga hijo izquierdo
            node = node.left; // Moverse hacia la izquierda
        }
        return node; // Retornar el nodo más a la izquierda
    }
    
    // RECORRIDO INORDER (Izquierda - Raíz - Derecha)
    public String inorderTraversal() {
        StringBuilder result = new StringBuilder(); // Para construir el resultado
        result.append("=== RECORRIDO INORDER ===\n"); // Título
        inorderRecursive(root, result); // Llamar método recursivo
        return result.toString(); // Retornar resultado como cadena
    }
    
    // MÉTODO recursivo para recorrido inorder
    private void inorderRecursive(TreeNode node, StringBuilder result) {
        if (node != null) { // Si el nodo no es null
            inorderRecursive(node.left, result); // Recorrer subárbol izquierdo
            result.append(node.employee.toString()).append("\n"); // Procesar nodo actual
            inorderRecursive(node.right, result); // Recorrer subárbol derecho
        }
    }
    
    // RECORRIDO PREORDER (Raíz - Izquierda - Derecha)
    public String preorderTraversal() {
        StringBuilder result = new StringBuilder(); // Para construir el resultado
        result.append("=== RECORRIDO PREORDER ===\n"); // Título
        preorderRecursive(root, result); // Llamar método recursivo
        return result.toString(); // Retornar resultado como cadena
    }
    
    // MÉTODO recursivo para recorrido preorder
    private void preorderRecursive(TreeNode node, StringBuilder result) {
        if (node != null) { // Si el nodo no es null
            result.append(node.employee.toString()).append("\n"); // Procesar nodo actual
            preorderRecursive(node.left, result); // Recorrer subárbol izquierdo
            preorderRecursive(node.right, result); // Recorrer subárbol derecho
        }
    }
    
    // RECORRIDO POSTORDER (Izquierda - Derecha - Raíz)
    public String postorderTraversal() {
        StringBuilder result = new StringBuilder(); // Para construir el resultado
        result.append("=== RECORRIDO POSTORDER ===\n"); // Título
        postorderRecursive(root, result); // Llamar método recursivo
        return result.toString(); // Retornar resultado como cadena
    }
    
    // MÉTODO recursivo para recorrido postorder
    private void postorderRecursive(TreeNode node, StringBuilder result) {
        if (node != null) { // Si el nodo no es null
            postorderRecursive(node.left, result); // Recorrer subárbol izquierdo
            postorderRecursive(node.right, result); // Recorrer subárbol derecho
            result.append(node.employee.toString()).append("\n"); // Procesar nodo actual
        }
    }
    
    // MÉTODO para cargar empleados iniciales
    public void loadInitialEmployees() {
        // Árbol inicia vacío - sin empleados precargados
        // El usuario debe agregar todos los empleados manualmente
    }
    
    // MÉTODOS de utilidad
    public int getSize() { // Obtener cantidad de empleados
        return size; // Retornar tamaño actual
    }
    
    public boolean isEmpty() { // Verificar si el árbol está vacío
        return root == null; // Retornar true si no hay raíz
    }
    
    // MÉTODO para obtener estadísticas del árbol
    public String getTreeStatistics() {
        StringBuilder stats = new StringBuilder(); // Para construir estadísticas
        stats.append("=== ESTADÍSTICAS DEL ÁRBOL ===\n");
        stats.append("Total de empleados: ").append(size).append("\n");
        stats.append("Altura del árbol: ").append(getHeight(root)).append("\n");
        
        // Contar empleados por departamento
        int[] departmentCounts = new int[3]; // [IT, Ventas, Marketing]
        countByDepartment(root, departmentCounts);
        stats.append("Empleados IT: ").append(departmentCounts[0]).append("\n");
        stats.append("Empleados Ventas: ").append(departmentCounts[1]).append("\n");
        stats.append("Empleados Marketing: ").append(departmentCounts[2]).append("\n");
        
        return stats.toString();
    }
    
    // MÉTODO recursivo para calcular altura del árbol
    private int getHeight(TreeNode node) {
        if (node == null) { // Si el nodo es null
            return 0; // Altura 0
        }
        
        int leftHeight = getHeight(node.left); // Altura subárbol izquierdo
        int rightHeight = getHeight(node.right); // Altura subárbol derecho
        
        return 1 + Math.max(leftHeight, rightHeight); // Retornar 1 + máxima altura
    }
    
    // MÉTODO recursivo para contar por departamento
    private void countByDepartment(TreeNode node, int[] counts) {
        if (node != null) { // Si el nodo no es null
            // Contar según departamento
            switch (node.employee.getDepartment()) {
                case "IT": counts[0]++; break;
                case "Ventas": counts[1]++; break;
                case "Marketing": counts[2]++; break;
            }
            
            countByDepartment(node.left, counts); // Contar en subárbol izquierdo
            countByDepartment(node.right, counts); // Contar en subárbol derecho
        }
    }
}