package proyectoFinal; // Declarar el paquete donde está la clase
import javax.swing.JOptionPane; // Importar la clase para ventanas de diálogo
import java.util.ArrayList; // Importar ArrayList para manejar listas de empleados

public class Main { // Declarar la clase principal del programa
    
    // ESTRUCTURAS DE DATOS GLOBALES - SEGÚN REQUERIMIENTOS
    private static PriorityQueue cola = new PriorityQueue(); // 1. Cola de prioridad para tareas urgentes (ESTRUCTURA PROPIA)
    private static List lista = new List(); // 2. Lista para tareas de departamentos (ESTRUCTURA PROPIA)  
    private static EmployeeBinaryTree arbolEmpleados = new EmployeeBinaryTree(); // 3. Árbol binario de empleados (ESTRUCTURA PROPIA)
    private static EmployeeHashManager hashEmpleados = new EmployeeHashManager(); // 4. HashMap de empleados (ESTRUCTURA NATIVA)
    private static TaskAssignmentHashMap sistemaAsignacion = new TaskAssignmentHashMap(); // 5. HashMap para asignación de tareas (ESTRUCTURA NATIVA)
    private static TaskDependencyGraph grafoDependencias = new TaskDependencyGraph(50); // 6. Grafo de dependencias (ESTRUCTURA PROPIA)
    
    public static void main(String[] args) { // Método principal que ejecuta el programa
        JOptionPane.showMessageDialog(null, "Nombre: Gabriel\nSistema de Gestión - Estructuras de Datos"); // Mostrar información
        
        // CARGAR DATOS INICIALES
        cargarDatosIniciales(); // Cargar estructuras con datos de ejemplo
        
        int opcion = 0; // Variable para guardar la opción elegida por el usuario
        do { // Iniciar un bucle que se repite hasta que el usuario elija salir
            String menu = "=== SISTEMA DE GESTIÓN - ESTRUCTURAS DE DATOS ===\n" +
                "=== ESTRUCTURA ===\n" +
                "1. Cola de Prioridad (Tareas Urgentes)\n" + 
                "2. Lista (Tareas por Departamento)\n" + 
                "3. Árbol Binario (Empleados)\n" + 
                "4. HashMap (Gestión de Empleados)\n" + 
                "5. HashMap (Asignación de Tareas)\n" + 
                "6. Algoritmos de Búsqueda y Ordenamiento\n" + 
                "7. Grafo de Dependencias de Tareas\n" + 
                "=== REPORTES ===\n" +
                "8. Generar Reporte Completo\n" + 
                "9. Salir\n";
            String input = JOptionPane.showInputDialog(menu + "Opción:"); // Mostrar el menú y pedir que el usuario elija una opción
            if (input == null) break; // Si el usuario cancela la ventana, salir del programa
            
            try { // Intentar convertir el texto ingresado a número
                opcion = Integer.parseInt(input); // Convertir el texto a número entero
            } catch (NumberFormatException e) { // Si no se puede convertir a número
                JOptionPane.showMessageDialog(null, "Número inválido"); // Mostrar mensaje de error
                continue; // Volver al inicio del bucle para pedir la opción otra vez
            }

            switch (opcion) { // Evaluar qué opción eligió el usuario
                case 1: // Cola de prioridad
                    menuCola(cola); // Llamar al método que maneja la cola de prioridad
                    break; // Salir del switch
                case 2: // Lista
                    menuLista(lista); // Llamar al método que maneja la lista
                    break; // Salir del switch
                case 3: // Árbol binario
                    menuArbolBinario(); // Llamar al método que maneja el árbol binario
                    break; // Salir del switch
                case 4: // HashMap empleados
                    menuHashMap(); // Llamar al método que maneja el HashMap
                    break; // Salir del switch
                case 5: // HashMap asignación
                    menuSistemaHashMapAsignacion(); // Llamar al método que maneja el HashMap de asignación
                    break; // Salir del switch
                case 6: // Algoritmos de búsqueda y ordenamiento
                    menuAlgoritmos(); // Llamar al método que maneja algoritmos
                    break; // Salir del switch
                case 7: // Grafo de dependencias
                    menuGrafoDependencias(); // Llamar al método que maneja el grafo
                    break; // Salir del switch
                case 8: // Reporte completo
                    mostrarReporteCompleto(); // Generar y mostrar reporte completo
                    break; // Salir del switch
                case 9: // Salir
                    JOptionPane.showMessageDialog(null, "¡Gracias por usar el sistema!"); // Mostrar mensaje de despedida
                    break; // Salir del switch
                default: // Si eligió cualquier otra opción
                    JOptionPane.showMessageDialog(null, "Opción no válida"); // Mostrar mensaje de error
            }
        } while (opcion != 9); // Repetir el bucle mientras la opción no sea 9 (salir)
    } // Fin del método main
    
    // MÉTODO para cargar datos iniciales en todas las estructuras
    private static void cargarDatosIniciales() {
        // Sistema inicia vacío - sin datos precargados
        // El usuario debe agregar todos los datos manualmente
    }

    // MÉTODOS PARA CADA ESTRUCTURA DE DATOS
    
    // MÉTODO para manejar la cola de prioridad
    private static void menuCola(PriorityQueue cola) {
        int opcion = 0;
        do {
            String menu = "=== COLA DE PRIORIDAD (TAREAS URGENTES) ===\n" +
                "1. Agregar tarea urgente\n" +
                "2. Procesar tarea más urgente\n" +
                "3. Ver tarea más urgente (sin procesar)\n" +
                "4. Ver todas las tareas\n" +
                "5. Volver al menú principal";
            
            String input = JOptionPane.showInputDialog(menu + "\nOpción:");
            if (input == null) break;
            
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número inválido");
                continue;
            }
            
            switch (opcion) {
                case 1: // Agregar tarea urgente
                    String desc = JOptionPane.showInputDialog("Descripción de la tarea:");
                    String prioStr = JOptionPane.showInputDialog("Prioridad (1-10):");
                    String fecha = JOptionPane.showInputDialog("Fecha de entrega (YYYY-MM-DD):");
                    
                    if (desc != null && prioStr != null && fecha != null) {
                        try {
                            int prioridad = Integer.parseInt(prioStr);
                            UrgentTask tarea = new UrgentTask(desc, prioridad, fecha);
                            cola.enqueue(tarea);
                            JOptionPane.showMessageDialog(null, "¡Tarea urgente agregada!");
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Prioridad inválida");
                        }
                    }
                    break;
                case 2: // Procesar tarea
                    UrgentTask procesada = cola.dequeue();
                    if (procesada != null) {
                        JOptionPane.showMessageDialog(null, "Tarea procesada: " + procesada.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay tareas urgentes");
                    }
                    break;
                case 3: // Ver siguiente
                    UrgentTask siguiente = cola.peek();
                    if (siguiente != null) {
                        JOptionPane.showMessageDialog(null, "Siguiente: " + siguiente.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay tareas urgentes");
                    }
                    break;
                case 4: // Ver todas
                    JOptionPane.showMessageDialog(null, cola.getQueueInfo());
                    break;
                case 5: // Volver
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        } while (opcion != 5);
    }

    // MÉTODO para manejar la lista de tareas por departamento
    private static void menuLista(List lista) {
        int opcion = 0;
        do {
            String menu = "=== LISTA (TAREAS POR DEPARTAMENTO) ===\n" +
                "1. Agregar tarea\n" +
                "2. Eliminar tarea\n" +
                "3. Buscar tarea\n" +
                "4. Ver todas las tareas\n" +
                "5. Análisis recursivo de tareas\n" +
                "6. Volver al menú principal";
            
            String input = JOptionPane.showInputDialog(menu + "\nOpción:");
            if (input == null) break;
            
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número inválido");
                continue;
            }
            
            switch (opcion) {
                case 1: // Agregar tarea
                    String desc = JOptionPane.showInputDialog("Descripción de la tarea:");
                    String dept = JOptionPane.showInputDialog("Departamento:");
                    String status = JOptionPane.showInputDialog("Estado (Pendiente/En Proceso/Completada):");
                    
                    if (desc != null && dept != null && status != null) {
                        lista.insert(desc, dept, status);
                        JOptionPane.showMessageDialog(null, "¡Tarea agregada!");
                    }
                    break;
                case 2: // Eliminar tarea
                    if (lista.getSize() > 0) {
                        String indexStr = JOptionPane.showInputDialog("Índice de la tarea a eliminar (0 a " + (lista.getSize()-1) + "):");
                        try {
                            int index = Integer.parseInt(indexStr);
                            if (lista.delete(index)) {
                                JOptionPane.showMessageDialog(null, "¡Tarea eliminada!");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Índice inválido");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay tareas para eliminar");
                    }
                    break;
                case 3: // Buscar tarea
                    String buscar = JOptionPane.showInputDialog("Descripción de la tarea a buscar:");
                    if (buscar != null && !buscar.trim().isEmpty()) {
                        DepartmentTask encontrada = lista.find(buscar.trim());
                        if (encontrada != null) {
                            JOptionPane.showMessageDialog(null, "Tarea encontrada: " + encontrada.toString());
                        } else {
                            JOptionPane.showMessageDialog(null, "Tarea no encontrada");
                        }
                    }
                    break;
                case 4: // Ver todas las tareas
                    String info = lista.getListInfo();
                    JOptionPane.showMessageDialog(null, info);
                    break;
                case 5: // Análisis recursivo
                    if (lista.getSize() > 0) {
                        StringBuilder recursiveAnalysis = new StringBuilder();
                        recursiveAnalysis.append("=== ANÁLISIS RECURSIVO DE TAREAS ===\n\n");
                        
                        // Obtener todas las tareas para analizar recursivamente
                        DepartmentTask[] todasTareas = lista.getAllTasks();
                        int tiempoTotal = 0;
                        int conteoTotal = 0;
                        int tiempoRestante = 0;
                        
                        for (DepartmentTask tarea : todasTareas) {
                            if (tarea != null) {
                                tiempoTotal += tarea.calculateTotalEstimatedTime();
                                conteoTotal += tarea.countAllTasks();
                                tiempoRestante += tarea.calculateRemainingTime();
                            }
                        }
                        
                        recursiveAnalysis.append("Tiempo total estimado: ").append(tiempoTotal).append(" horas\n");
                        recursiveAnalysis.append("Número total de tareas: ").append(conteoTotal).append("\n");
                        recursiveAnalysis.append("Tiempo restante pendiente: ").append(tiempoRestante).append(" horas\n");
                        JOptionPane.showMessageDialog(null, recursiveAnalysis.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay tareas para analizar");
                    }
                    break;
                case 6: // Volver al menú principal
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        } while (opcion != 6);
    }

    // MÉTODO para manejar el árbol binario de empleados
    private static void menuArbolBinario() {
        int opcion = 0;
        do {
            String menu = "=== ÁRBOL BINARIO (EMPLEADOS) ===\n" +
                "1. Agregar empleado\n" +
                "2. Buscar empleado por ID\n" +
                "3. Eliminar empleado\n" +
                "4. Mostrar recorrido InOrder\n" +
                "5. Mostrar recorrido PreOrder\n" +
                "6. Mostrar recorrido PostOrder\n" +
                "7. Ver estadísticas del árbol\n" +
                "8. Volver al menú principal";
            
            String input = JOptionPane.showInputDialog(menu + "\nOpción:");
            if (input == null) break;
            
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número inválido");
                continue;
            }
            
            switch (opcion) {
                case 1: // Agregar empleado
                    String idStr = JOptionPane.showInputDialog("ID del empleado:");
                    String nombre = JOptionPane.showInputDialog("Nombre del empleado:");
                    String dept = JOptionPane.showInputDialog("Departamento:");
                    String puesto = JOptionPane.showInputDialog("Puesto:");
                    String scoreStr = JOptionPane.showInputDialog("Puntuación (0-100):");
                    
                    if (idStr != null && nombre != null && dept != null && puesto != null && scoreStr != null) {
                        try {
                            int id = Integer.parseInt(idStr);
                            double score = Double.parseDouble(scoreStr);
                            Employee emp = new Employee(id, nombre, dept, puesto, score);
                            arbolEmpleados.insert(emp);
                            JOptionPane.showMessageDialog(null, "¡Empleado agregado al árbol!");
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Datos numéricos inválidos");
                        }
                    }
                    break;
                case 2: // Buscar empleado
                    String searchId = JOptionPane.showInputDialog("ID del empleado a buscar:");
                    if (searchId != null) {
                        try {
                            int id = Integer.parseInt(searchId);
                            Employee found = arbolEmpleados.search(id);
                            if (found != null) {
                                JOptionPane.showMessageDialog(null, "Empleado encontrado:\n" + found.getDetailedInfo());
                            } else {
                                JOptionPane.showMessageDialog(null, "Empleado no encontrado");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "ID inválido");
                        }
                    }
                    break;
                case 3: // Eliminar empleado
                    String deleteId = JOptionPane.showInputDialog("ID del empleado a eliminar:");
                    if (deleteId != null) {
                        try {
                            int id = Integer.parseInt(deleteId);
                            boolean eliminado = arbolEmpleados.delete(id);
                            if (eliminado) {
                                JOptionPane.showMessageDialog(null, "¡Empleado con ID " + id + " eliminado correctamente!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Empleado no encontrado");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "ID inválido");
                        }
                    }
                    break;
                case 4: // InOrder
                    JOptionPane.showMessageDialog(null, "Recorrido InOrder:\n" + arbolEmpleados.inorderTraversal());
                    break;
                case 5: // PreOrder
                    JOptionPane.showMessageDialog(null, "Recorrido PreOrder:\n" + arbolEmpleados.preorderTraversal());
                    break;
                case 6: // PostOrder
                    JOptionPane.showMessageDialog(null, "Recorrido PostOrder:\n" + arbolEmpleados.postorderTraversal());
                    break;
                case 7: // Estadísticas
                    JOptionPane.showMessageDialog(null, arbolEmpleados.getTreeStatistics());
                    break;
                case 8: // Volver
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        } while (opcion != 8);
    }

    // MÉTODO para manejar HashMap de empleados
    private static void menuHashMap() {
        int opcion = 0;
        do {
            String menu = "=== HASHMAP (GESTIÓN DE EMPLEADOS) ===\n" +
                "1. Agregar empleado\n" +
                "2. Buscar empleado por ID\n" +
                "3. Buscar empleados por departamento\n" +
                "4. Buscar empleado por nombre\n" +
                "5. Ver estadísticas por departamento\n" +
                "6. Volver al menú principal";
            
            String input = JOptionPane.showInputDialog(menu + "\nOpción:");
            if (input == null) break;
            
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número inválido");
                continue;
            }
            
            switch (opcion) {
                case 1: // Agregar empleado
                    String idStr = JOptionPane.showInputDialog("ID del empleado:");
                    String nombre = JOptionPane.showInputDialog("Nombre del empleado:");
                    String dept = JOptionPane.showInputDialog("Departamento:");
                    String puesto = JOptionPane.showInputDialog("Puesto:");
                    String scoreStr = JOptionPane.showInputDialog("Puntuación (0-100):");
                    
                    if (idStr != null && nombre != null && dept != null && puesto != null && scoreStr != null) {
                        try {
                            int id = Integer.parseInt(idStr);
                            double score = Double.parseDouble(scoreStr);
                            Employee emp = new Employee(id, nombre, dept, puesto, score);
                            hashEmpleados.addEmployee(emp);
                            JOptionPane.showMessageDialog(null, "¡Empleado agregado al HashMap!");
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Datos numéricos inválidos");
                        }
                    }
                    break;
                case 2: // Buscar por ID
                    String searchIdStr = JOptionPane.showInputDialog("ID del empleado:");
                    if (searchIdStr != null) {
                        try {
                            int id = Integer.parseInt(searchIdStr);
                            Employee emp = hashEmpleados.findById(id);
                            if (emp != null) {
                                JOptionPane.showMessageDialog(null, "Empleado encontrado:\n" + emp.getDetailedInfo());
                            } else {
                                JOptionPane.showMessageDialog(null, "Empleado no encontrado");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "ID inválido");
                        }
                    }
                    break;
                case 3: // Buscar por departamento
                    String deptSearch = JOptionPane.showInputDialog("Departamento:");
                    if (deptSearch != null) {
                        ArrayList<Employee> empleados = hashEmpleados.getEmployeesByDepartment(deptSearch);
                        StringBuilder resultado = new StringBuilder("Empleados en " + deptSearch + ":\n");
                        for (Employee emp : empleados) {
                            resultado.append("- ").append(emp.getName()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, resultado.toString());
                    }
                    break;
                case 4: // Buscar por nombre
                    String nombreSearch = JOptionPane.showInputDialog("Nombre del empleado:");
                    if (nombreSearch != null) {
                        Employee emp = hashEmpleados.findByName(nombreSearch);
                        if (emp != null) {
                            JOptionPane.showMessageDialog(null, "Empleado encontrado:\n" + emp.getDetailedInfo());
                        } else {
                            JOptionPane.showMessageDialog(null, "Empleado no encontrado");
                        }
                    }
                    break;
                case 5: // Estadísticas
                    JOptionPane.showMessageDialog(null, hashEmpleados.getDepartmentStatistics());
                    break;
                case 6: // Volver
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        } while (opcion != 6);
    }

    // MÉTODO para manejar HashMap de asignación de tareas
    private static void menuSistemaHashMapAsignacion() {
        int opcion = 0;
        do {
            String menu = "=== HASHMAP (ASIGNACIÓN DE TAREAS) ===\n" +
                "1. Agregar tarea al sistema\n" +
                "2. Asignar tarea a empleado\n" +
                "3. Buscar tareas por empleado\n" +
                "4. Buscar tareas por departamento\n" +
                "5. Ver estadísticas del sistema\n" +
                "6. Volver al menú principal";
            
            String input = JOptionPane.showInputDialog(menu + "\nOpción:");
            if (input == null) break;
            
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número inválido");
                continue;
            }
            
            switch (opcion) {
                case 1: // Agregar tarea
                    String desc = JOptionPane.showInputDialog("Descripción de la tarea:");
                    String dept = JOptionPane.showInputDialog("Departamento:");
                    String status = JOptionPane.showInputDialog("Estado:");
                    String timeStr = JOptionPane.showInputDialog("Tiempo estimado (horas):");
                    
                    if (desc != null && dept != null && status != null && timeStr != null) {
                        try {
                            int hours = Integer.parseInt(timeStr);
                            DepartmentTask tarea = new DepartmentTask(desc, dept, status, hours);
                            int id = sistemaAsignacion.addTask(tarea);
                            JOptionPane.showMessageDialog(null, "Tarea agregada con ID: " + id);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Horas inválidas");
                        }
                    }
                    break;
                case 2: // Asignar tarea a empleado
                    String taskIdStr = JOptionPane.showInputDialog("ID de la tarea a asignar:");
                    String empleadoIdStr = JOptionPane.showInputDialog("ID del empleado:");
                    
                    if (taskIdStr != null && empleadoIdStr != null) {
                        try {
                            int taskId = Integer.parseInt(taskIdStr);
                            int empleadoId = Integer.parseInt(empleadoIdStr);
                            
                            // Buscar el empleado en el HashMap
                            Employee empleado = hashEmpleados.findById(empleadoId);
                            if (empleado != null) {
                                boolean asignado = sistemaAsignacion.assignTaskToEmployee(taskId, empleado);
                                if (asignado) {
                                    JOptionPane.showMessageDialog(null, "¡Tarea asignada correctamente a " + empleado.getName() + "!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se pudo asignar la tarea");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Empleado no encontrado");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "IDs inválidos");
                        }
                    }
                    break;
                case 3: // Buscar por empleado
                    String empleado = JOptionPane.showInputDialog("Nombre del empleado:");
                    if (empleado != null) {
                        var tareas = sistemaAsignacion.findTasksByEmployee(empleado);
                        StringBuilder resultado = new StringBuilder("Tareas de " + empleado + ":\n");
                        for (DepartmentTask task : tareas) {
                            resultado.append("• ").append(task.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, resultado.toString());
                    }
                    break;
                case 4: // Buscar por departamento
                    String departamento = JOptionPane.showInputDialog("Departamento:");
                    if (departamento != null) {
                        var tareas = sistemaAsignacion.findTasksByDepartment(departamento);
                        StringBuilder resultado = new StringBuilder("Tareas del departamento " + departamento + ":\n");
                        for (DepartmentTask task : tareas) {
                            resultado.append("• ").append(task.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, resultado.toString());
                    }
                    break;
                case 5: // Ver estadísticas
                    JOptionPane.showMessageDialog(null, sistemaAsignacion.generateHashTableStatistics());
                    break;
                case 6: // Volver
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        } while (opcion != 6);
    }

    // MÉTODO para algoritmos de búsqueda y ordenamiento
    private static void menuAlgoritmos() {
        JOptionPane.showMessageDialog(null, "=== ALGORITMOS DE BÚSQUEDA Y ORDENAMIENTO ===\n\n" +
            "Los algoritmos están integrados en cada estructura:\n\n" +
            "• Cola de Prioridad: Ordenamiento por prioridad\n" +
            "• Lista: Búsqueda secuencial implementada\n" +
            "• Árbol Binario: Búsqueda binaria por naturaleza\n" +
            "• HashMap: Búsqueda O(1) por hash\n" +
            "• Grafo: Algoritmos de ordenamiento topológico\n\n" +
            "Cada estructura utiliza el algoritmo más eficiente según sus características.");
    }

    // MÉTODO para manejar grafo de dependencias
    private static void menuGrafoDependencias() {
        int opcion = 0;
        do {
            String menu = "=== GRAFO (DEPENDENCIAS DE TAREAS) ===\n" +
                "1. Agregar tarea al proyecto\n" +
                "2. Agregar dependencia\n" +
                "3. Ver todas las tareas\n" +
                "4. Ver matriz de adyacencia\n" +
                "5. Ver orden de ejecución sugerido\n" +
                "6. Ver estadísticas del proyecto\n" +
                "7. Volver al menú principal";
            
            String input = JOptionPane.showInputDialog(menu + "\nOpción:");
            if (input == null) break;
            
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número inválido");
                continue;
            }
            
            switch (opcion) {
                case 1: // Agregar tarea
                    String nombre = JOptionPane.showInputDialog("Nombre de la tarea:");
                    String tiempoStr = JOptionPane.showInputDialog("Tiempo estimado (horas):");
                    if (nombre != null && tiempoStr != null) {
                        try {
                            int tiempo = Integer.parseInt(tiempoStr);
                            DepartmentTask nuevaTarea = new DepartmentTask(nombre, "IT", "Pendiente", tiempo);
                            boolean agregada = grafoDependencias.addTask(nuevaTarea);
                            if (agregada) {
                                JOptionPane.showMessageDialog(null, "¡Tarea agregada al proyecto!\nID asignado: " + nuevaTarea.getTaskId());
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo agregar la tarea (puede que ya exista)");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Tiempo inválido");
                        }
                    }
                    break;
                case 2: // Agregar dependencia
                    // Primero mostrar IDs disponibles
                    if (grafoDependencias.getTaskCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay tareas en el proyecto. Agrega tareas primero.");
                        break;
                    }
                    
                    // Mostrar tareas disponibles
                    StringBuilder tareasDisponibles = new StringBuilder("IDs de tareas disponibles:\n");
                    for (int id : grafoDependencias.getAllTaskIds()) {
                        DepartmentTask tarea = grafoDependencias.getTask(id);
                        tareasDisponibles.append("ID ").append(id).append(": ").append(tarea.getTaskDescription()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, tareasDisponibles.toString());
                    
                    String desdeStr = JOptionPane.showInputDialog("ID de tarea prerequisito:");
                    String haciaStr = JOptionPane.showInputDialog("ID de tarea dependiente:");
                    if (desdeStr != null && haciaStr != null) {
                        try {
                            int desde = Integer.parseInt(desdeStr);
                            int hacia = Integer.parseInt(haciaStr);
                            boolean agregada = grafoDependencias.addDependency(desde, hacia);
                            if (agregada) {
                                JOptionPane.showMessageDialog(null, "¡Dependencia agregada!\n" + desde + " → " + hacia);
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo agregar la dependencia.\n" + 
                                    "Verifica que ambos IDs (" + desde + " y " + hacia + ") existan en el proyecto.");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Error: Ingrese IDs numéricos válidos");
                        }
                    }
                    break;
                case 3: // Ver todas las tareas
                    if (grafoDependencias.getTaskCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No hay tareas en el proyecto.");
                    } else {
                        StringBuilder todasTareas = new StringBuilder("=== TODAS LAS TAREAS DEL PROYECTO ===\n\n");
                        for (int id : grafoDependencias.getAllTaskIds()) {
                            DepartmentTask tarea = grafoDependencias.getTask(id);
                            todasTareas.append("ID ").append(id).append(": ")
                                      .append(tarea.getTaskDescription())
                                      .append(" (").append(tarea.getEstimatedHours()).append("h)\n");
                        }
                        JOptionPane.showMessageDialog(null, todasTareas.toString());
                    }
                    break;
                case 4: // Ver matriz
                    JOptionPane.showMessageDialog(null, grafoDependencias.getAdjacencyMatrix());
                    break;
                case 5: // Ver orden
                    ArrayList<DepartmentTask> orden = grafoDependencias.getExecutionOrder();
                    if (orden.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay tareas para ordenar.");
                    } else {
                        StringBuilder ordenStr = new StringBuilder("=== ORDEN DE EJECUCIÓN SUGERIDO ===\n\n");
                        for (int i = 0; i < orden.size(); i++) {
                            DepartmentTask tarea = orden.get(i);
                            ordenStr.append((i + 1)).append(". ID ").append(tarea.getTaskId())
                                   .append(": ").append(tarea.getTaskDescription())
                                   .append(" (").append(tarea.getEstimatedHours()).append("h)\n");
                        }
                        JOptionPane.showMessageDialog(null, ordenStr.toString());
                    }
                    break;
                case 6: // Ver estadísticas
                    JOptionPane.showMessageDialog(null, grafoDependencias.getGraphStatistics());
                    break;
                case 7: // Volver
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        } while (opcion != 7);
    }

    // MÉTODO para generar reporte completo del sistema
    private static void mostrarReporteCompleto() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE COMPLETO DEL SISTEMA ===\n\n");
        
        // Estadísticas generales
        reporte.append("=== ESTADÍSTICAS GENERALES ===\n");
        reporte.append("Tareas urgentes en cola: ").append(cola.getSize()).append("\n");
        reporte.append("Tareas por departamento: ").append(lista.getSize()).append("\n");
        reporte.append("Empleados en árbol binario: ").append(arbolEmpleados.getSize()).append("\n");
        reporte.append("Empleados en HashMap: ").append(hashEmpleados.getTotalEmployees()).append("\n");
        reporte.append("Tareas en sistema de asignación: ").append(sistemaAsignacion.getTotalTasks()).append("\n");
        reporte.append("Tareas en grafo de dependencias: ").append(grafoDependencias.getTaskCount()).append("\n\n");
        
        // Estructuras implementadas
        reporte.append("=== ESTRUCTURAS IMPLEMENTADAS ===\n");
        reporte.append("✅ Cola de Prioridad (Estructura Propia)\n");
        reporte.append("✅ Lista (Estructura Propia)\n");
        reporte.append("✅ Árbol Binario (Estructura Propia)\n");
        reporte.append("✅ HashMap Empleados (Estructura Nativa)\n");
        reporte.append("✅ HashMap Asignación (Estructura Nativa)\n");
        reporte.append("✅ Grafo con Matriz de Adyacencia (Estructura Propia)\n\n");
        
        // Funcionalidades clave
        reporte.append("=== FUNCIONALIDADES CLAVE ===\n");
        reporte.append("• Recursividad: Implementada en DepartmentTask\n");
        reporte.append("• Búsqueda y Ordenamiento: Integrados por estructura\n");
        reporte.append("• Gestión de Dependencias: Grafo con ordenamiento topológico\n");
        reporte.append("• Tiempo Estimado: Atributo agregado a tareas departamentales\n");
        reporte.append("• Acceso Rápido: HashMap para búsquedas O(1)\n");
        
        JOptionPane.showMessageDialog(null, reporte.toString());
    }
} // Fin de la clase Main