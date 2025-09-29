package proyectoFinal; // Declaración del paquete donde está la clase List

public class List { // Definir la clase List que implementa una lista estática para manejar tareas por departamento
    // ATRIBUTOS (variables de instancia de la clase)
    private final int capacity = 100; // Capacidad máxima de la lista (constante final, no se puede cambiar)
    private DepartmentTask[] data; // Array para almacenar las tareas por departamento
    private int size; // Variable para llevar el control del tamaño actual de la lista (cuántos elementos tiene)

    // CONSTRUCTOR (método que se ejecuta al crear un objeto List)
    public List() { // Constructor sin parámetros para inicializar la lista vacía
        this.data = new DepartmentTask[capacity]; // Crear array de DepartmentTask con tamaño igual a capacity
        this.size = 0; // Inicializar tamaño en 0 para indicar que la lista está vacía
    } // Fin del constructor

    // MÉTODOS (funciones de la clase List)
    public boolean isEmpty() { // Método público para verificar si la lista está vacía
        return size == 0; // Retorna true si size es 0 (sin elementos), false si tiene elementos
    } // Fin del método isEmpty

    public boolean isFull() { // Método público para verificar si la lista está llena
        return size == capacity; // Retorna true si size es igual a capacity (llena), false si hay espacio
    } // Fin del método isFull

    public int getSize() { // Método público para obtener el número actual de elementos en la lista
        return size; // Retornar el valor de la variable size
    } // Fin del método getSize

    public void insert(String taskDescription, String department, String status) { // Método para insertar una nueva tarea al final de la lista
        if (isFull()) { // Verificar si la lista ya está llena usando el método isFull
            System.out.println("La lista está llena. No se puede agregar más tareas."); // Mostrar mensaje de error en consola
            return; // Salir del método sin agregar nada
        } // Fin del if de verificación de lista llena
        
        data[size] = new DepartmentTask(taskDescription, department, status); // Crear nueva DepartmentTask y almacenarla en la posición size
        size++; // Incrementar el tamaño después de insertar (ahora hay un elemento más)
    } // Fin del método insert

    public boolean delete(int index) { // Método público para eliminar una tarea por su índice en la lista
        if (index < 0 || index >= size) { // Verificar si el índice está dentro del rango válido (0 a size-1)
            System.out.println("Índice inválido."); // Mostrar mensaje de error en consola
            return false; // Retornar false para indicar que la eliminación no fue exitosa
        } // Fin del if de verificación de índice
        
        // Mover todos los elementos posteriores una posición hacia la izquierda para llenar el hueco
        for (int i = index; i < size - 1; i++) { // Recorrer desde el índice a eliminar hasta el penúltimo elemento
            data[i] = data[i + 1]; // Mover el elemento siguiente a la posición actual (shift izquierda)
        } // Fin del for
        
        data[size - 1] = null; // Limpiar la última posición poniendo null (buena práctica para liberar memoria)
        size--; // Decrementar el tamaño después de eliminar (ahora hay un elemento menos)
        return true; // Retornar true para indicar que la eliminación fue exitosa
    } // Fin del método delete

    public DepartmentTask find(String taskDescription) { // Método público para buscar una tarea por su descripción
        for (int i = 0; i < size; i++) { // Recorrer toda la lista desde el primer elemento hasta el último
            if (data[i].getTaskDescription().equalsIgnoreCase(taskDescription)) { // Comparar descripción ignorando mayúsculas/minúsculas
                return data[i]; // Retornar la tarea encontrada (primera coincidencia)
            } // Fin del if de comparación
        } // Fin del for
        return null; // Retornar null si no se encuentra ninguna tarea con esa descripción
    } // Fin del método find

    public DepartmentTask[] findByDepartment(String department) { // Método público para buscar todas las tareas de un departamento específico
        // PRIMERA PASADA: contar cuántas tareas hay del departamento para crear array del tamaño correcto
        int count = 0; // Contador inicializado en 0 para contar tareas del departamento
        for (int i = 0; i < size; i++) { // Recorrer toda la lista para contar
            if (data[i].getDepartment().equalsIgnoreCase(department)) { // Si el departamento coincide (ignorando mayúsculas)
                count++; // Incrementar contador de tareas encontradas
            } // Fin del if de comparación de departamento
        } // Fin del for de conteo
        
        if (count == 0) { // Si no se encontraron tareas del departamento
            return new DepartmentTask[0]; // Retornar array vacío (tamaño 0)
        } // Fin del if de verificación de count
        
        // SEGUNDA PASADA: crear array del tamaño exacto y llenar con las tareas encontradas
        DepartmentTask[] result = new DepartmentTask[count]; // Crear array resultado con tamaño exacto
        int index = 0; // Índice para llenar el array resultado, inicializado en 0
        for (int i = 0; i < size; i++) { // Recorrer toda la lista nuevamente
            if (data[i].getDepartment().equalsIgnoreCase(department)) { // Si el departamento coincide
                result[index] = data[i]; // Agregar la tarea al array resultado en la posición index
                index++; // Incrementar índice del array resultado para la siguiente posición
            } // Fin del if de comparación de departamento
        } // Fin del for de llenado
        
        return result; // Retornar array con todas las tareas del departamento
    } // Fin del método findByDepartment

    public DepartmentTask[] findByStatus(String status) { // Método público para buscar todas las tareas por estado específico
        // PRIMERA PASADA: contar cuántas tareas hay con ese estado para crear array del tamaño correcto
        int count = 0; // Contador inicializado en 0 para contar tareas con el estado buscado
        for (int i = 0; i < size; i++) { // Recorrer toda la lista para contar
            if (data[i].getStatus().equalsIgnoreCase(status)) { // Si el estado coincide (ignorando mayúsculas)
                count++; // Incrementar contador de tareas encontradas
            } // Fin del if de comparación de estado
        } // Fin del for de conteo
        
        if (count == 0) { // Si no se encontraron tareas con ese estado
            return new DepartmentTask[0]; // Retornar array vacío (tamaño 0)
        } // Fin del if de verificación de count
        
        // SEGUNDA PASADA: crear array del tamaño exacto y llenar con las tareas encontradas
        DepartmentTask[] result = new DepartmentTask[count]; // Crear array resultado con tamaño exacto
        int index = 0; // Índice para llenar el array resultado, inicializado en 0
        for (int i = 0; i < size; i++) { // Recorrer toda la lista nuevamente
            if (data[i].getStatus().equalsIgnoreCase(status)) { // Si el estado coincide
                result[index] = data[i]; // Agregar la tarea al array resultado en la posición index
                index++; // Incrementar índice del array resultado para la siguiente posición
            } // Fin del if de comparación de estado
        } // Fin del for de llenado
        
        return result; // Retornar array con todas las tareas del estado buscado
    } // Fin del método findByStatus

    public DepartmentTask[] getAllTasks() { // Método público para obtener una copia de todas las tareas en la lista
        if (isEmpty()) { // Verificar si la lista está vacía usando el método isEmpty
            return new DepartmentTask[0]; // Retornar array vacío si no hay elementos
        } // Fin del if de verificación de lista vacía
        
        DepartmentTask[] result = new DepartmentTask[size]; // Crear array resultado con el tamaño actual de la lista
        for (int i = 0; i < size; i++) { // Recorrer toda la lista desde el primer elemento
            result[i] = data[i]; // Copiar cada tarea al array resultado en la misma posición
        } // Fin del for
        return result; // Retornar array con todas las tareas
    } // Fin del método getAllTasks

    public DepartmentTask getTask(int index) { // Método público para obtener una tarea específica por su índice
        if (index < 0 || index >= size) { // Verificar si el índice está dentro del rango válido (0 a size-1)
            return null; // Retornar null si el índice no es válido
        } // Fin del if de verificación de índice
        return data[index]; // Retornar la tarea en la posición especificada
    } // Fin del método getTask

    public String[] getAllDepartments() { // Método público para obtener lista de todos los departamentos únicos (sin repetir)
        if (isEmpty()) { // Verificar si la lista está vacía usando el método isEmpty
            return new String[0]; // Retornar array vacío si no hay elementos
        } // Fin del if de verificación de lista vacía
        
        String[] tempDepartments = new String[size]; // Array temporal para almacenar departamentos (tamaño máximo posible)
        int uniqueCount = 0; // Contador inicializado en 0 para llevar cuenta de departamentos únicos encontrados
        
        for (int i = 0; i < size; i++) { // Recorrer toda la lista de tareas
            String dept = data[i].getDepartment(); // Obtener el departamento de la tarea actual
            boolean isUnique = true; // Bandera inicializada en true para verificar si el departamento es único
            
            // Verificar si el departamento ya está en la lista temporal de únicos
            for (int j = 0; j < uniqueCount; j++) { // Recorrer departamentos únicos ya encontrados
                if (tempDepartments[j].equalsIgnoreCase(dept)) { // Si el departamento ya existe (ignorando mayúsculas)
                    isUnique = false; // Marcar como no único (ya está en la lista)
                    break; // Salir del bucle interno porque ya encontramos una coincidencia
                } // Fin del if de comparación
            } // Fin del for interno
            
            if (isUnique) { // Si el departamento es único (no se encontró antes)
                tempDepartments[uniqueCount] = dept; // Agregarlo a la lista temporal en la posición uniqueCount
                uniqueCount++; // Incrementar contador de departamentos únicos
            } // Fin del if de verificación de unicidad
        } // Fin del for externo
        
        // Crear array del tamaño exacto con solo los departamentos únicos (sin espacios vacíos)
        String[] result = new String[uniqueCount]; // Array resultado con tamaño exacto de únicos encontrados
        for (int i = 0; i < uniqueCount; i++) { // Recorrer departamentos únicos para copiarlos
            result[i] = tempDepartments[i]; // Copiar departamento único al array resultado
        } // Fin del for de copia
        
        return result; // Retornar array con departamentos únicos solamente
    } // Fin del método getAllDepartments

    public String getListInfo() { // Método público para obtener información completa de la lista en formato de cadena
        if (isEmpty()) { // Verificar si la lista está vacía usando el método isEmpty
            return "No hay tareas en la lista."; // Retornar mensaje informativo si está vacía
        } // Fin del if de verificación de lista vacía
        
        StringBuilder sb = new StringBuilder(); // Crear objeto StringBuilder para construir cadenas de texto de manera eficiente
        sb.append("=== LISTA DE TAREAS POR DEPARTAMENTO ===\n"); // Agregar título del reporte con salto de línea
        sb.append("Total de tareas: ").append(size).append("\n\n"); // Concatenar "Total de tareas: " con el tamaño y dos saltos de línea
        
        DepartmentTask[] tasks = getAllTasks(); // Obtener todas las tareas usando el método getAllTasks
        for (int i = 0; i < tasks.length; i++) { // Recorrer cada tarea en el array obtenido
            sb.append((i + 1)).append(". ").append(tasks[i].toString()).append("\n"); // Agregar número de tarea, punto, información y salto de línea
        } // Fin del for
        
        return sb.toString(); // Convertir StringBuilder a String y retornarlo
    } // Fin del método getListInfo
} // Fin de la clase List
