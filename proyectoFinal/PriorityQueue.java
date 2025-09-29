package proyectoFinal; // Declaración del paquete donde está la clase

public class PriorityQueue { // Declarar la clase para cola de prioridad usando heap
    // ATRIBUTOS (variables de la clase)
    final int capacity = 100; // Capacidad máxima de la cola (sin límite específico de prioridades)
    int size; // Tamaño actual de la cola (cuántos elementos tiene)
    private UrgentTask[] heap; // Array para almacenar las tareas urgentes como heap
    private int[] priorities; // Array para almacenar las prioridades correspondientes

    // CONSTRUCTOR (método que se ejecuta al crear un objeto)
    public PriorityQueue() { // Constructor sin parámetros
        this.heap = new UrgentTask[this.capacity]; // Crear array de tareas con tamaño capacity
        this.priorities = new int[this.capacity]; // Crear array de prioridades con tamaño capacity
        this.size = 0; // Inicializar el tamaño en 0 (cola vacía)
    } // Fin del constructor

    // MÉTODOS BÁSICOS (funciones simples de la clase)
    public boolean isEmpty() { // Método para verificar si la cola está vacía
        return this.size == 0; // Retorna true si size es 0, false si tiene elementos
    } // Fin del método isEmpty

    public int getSize() { // Método para obtener el tamaño actual de la cola
        return this.size; // Retorna el número de elementos que tiene la cola
    } // Fin del método getSize

    // MÉTODOS DE HEAP (lógica de árbol binario para mantener orden)
    private int getParentIndex(int childIndex) { // Obtener índice del padre en el heap
        return (childIndex - 1) / 2; // Fórmula matemática para encontrar padre en heap
    } // Fin del método getParentIndex

    private int getLeftChildIndex(int parentIndex) { // Obtener índice del hijo izquierdo
        return 2 * parentIndex + 1; // Fórmula matemática para hijo izquierdo
    } // Fin del método getLeftChildIndex

    private int getRightChildIndex(int parentIndex) { // Obtener índice del hijo derecho
        return 2 * parentIndex + 2; // Fórmula matemática para hijo derecho
    } // Fin del método getRightChildIndex

    private boolean hasParent(int index) { // Verificar si un nodo tiene padre
        return getParentIndex(index) >= 0; // Verdadero si el índice del padre es válido
    } // Fin del método hasParent

    private boolean hasLeftChild(int index) { // Verificar si un nodo tiene hijo izquierdo
        return getLeftChildIndex(index) < size; // Verdadero si el índice está dentro del tamaño
    } // Fin del método hasLeftChild

    private boolean hasRightChild(int index) { // Verificar si un nodo tiene hijo derecho
        return getRightChildIndex(index) < size; // Verdadero si el índice está dentro del tamaño
    } // Fin del método hasRightChild

    private void swap(int indexOne, int indexTwo) { // Intercambiar dos elementos en el heap
        // Intercambiar tareas
        UrgentTask tempTask = heap[indexOne]; // Guardar temporalmente la primera tarea
        heap[indexOne] = heap[indexTwo]; // Mover segunda tarea a primera posición
        heap[indexTwo] = tempTask; // Mover primera tarea (temporal) a segunda posición
        
        // Intercambiar prioridades
        int tempPriority = priorities[indexOne]; // Guardar temporalmente la primera prioridad
        priorities[indexOne] = priorities[indexTwo]; // Mover segunda prioridad a primera posición
        priorities[indexTwo] = tempPriority; // Mover primera prioridad (temporal) a segunda posición
    } // Fin del método swap

    private void heapifyUp() { // Reordenar hacia arriba para mantener propiedad del heap (min-heap)
        int index = size - 1; // Empezar desde el último elemento insertado
        // Continuar mientras tenga padre y su prioridad sea menor (mayor prioridad)
        while (hasParent(index) && priorities[getParentIndex(index)] > priorities[index]) {
            swap(getParentIndex(index), index); // Intercambiar con el padre
            index = getParentIndex(index); // Moverse hacia arriba en el heap
        }
    } // Fin del método heapifyUp

    private void heapifyDown() { // Reordenar hacia abajo para mantener propiedad del heap
        int index = 0; // Empezar desde la raíz
        // Continuar mientras tenga al menos un hijo izquierdo
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index); // Asumir que hijo izquierdo es menor
            
            // Si tiene hijo derecho y es menor que el izquierdo
            if (hasRightChild(index) && priorities[getRightChildIndex(index)] < priorities[getLeftChildIndex(index)]) {
                smallerChildIndex = getRightChildIndex(index); // El hijo derecho es el menor
            }
            
            // Si el nodo actual ya es menor o igual que sus hijos
            if (priorities[index] < priorities[smallerChildIndex]) {
                break; // La propiedad del heap ya se cumple
            } else {
                swap(index, smallerChildIndex); // Intercambiar con el hijo menor
            }
            index = smallerChildIndex; // Moverse hacia abajo en el heap
        }
    } // Fin del método heapifyDown

    public void push(int priority, UrgentTask task) { // Método para agregar una tarea (SIN LÍMITE de prioridades)
        // Verificar si la cola está llena
        if (this.size >= this.capacity) { // Si ya se llegó al límite de capacidad
            System.out.println("LA COLA ESTA LLENA"); // Mostrar mensaje de error
            return; // Salir del método sin agregar nada
        }
        
        // IMPLEMENTACIÓN CON HEAP: Agregar al final y hacer heapify up
        heap[size] = task; // Colocar tarea en la siguiente posición disponible
        priorities[size] = priority; // Colocar prioridad (SIN RESTRICCIONES de valor)
        size++; // Incrementar tamaño después de insertar
        heapifyUp(); // Reordenar el heap hacia arriba para mantener propiedad
    } // Fin del método push

    // MÉTODOS PRINCIPALES DE COLA DE PRIORIDAD
    public void enqueue(UrgentTask task) { // Método para agregar tarea a la cola
        push(task.getPriority(), task); // Usar el método push existente
    } // Fin del método enqueue

    public UrgentTask dequeue() { // Método para obtener y remover tarea de mayor prioridad
        return pop(); // Usar el método pop existente
    } // Fin del método dequeue
            
    public UrgentTask pop() { // Método para extraer la tarea de mayor prioridad usando heap
        if (isEmpty()) { // Verificar si la cola está vacía usando el método isEmpty
            System.out.println("LA COLA ESTA VACÍA."); // Mostrar mensaje de error
            return null; // Retornar null porque no hay elementos que extraer
        }
        
        // IMPLEMENTACIÓN CON HEAP: Extraer raíz y reordenar
        UrgentTask result = heap[0]; // La raíz siempre tiene la mayor prioridad (menor número)
        
        // Mover último elemento a la raíz
        heap[0] = heap[size - 1]; // Mover última tarea a la raíz
        priorities[0] = priorities[size - 1]; // Mover última prioridad a la raíz
        
        size--; // Decrementar tamaño
        heap[size] = null; // Limpiar la última posición
        priorities[size] = 0; // Limpiar la última prioridad
        
        heapifyDown(); // Reordenar el heap hacia abajo desde la raíz
        
        return result; // Retornar la tarea extraída
    } // Fin del método pop

    public UrgentTask peek() { // Método para ver la tarea de mayor prioridad sin extraerla
        if (isEmpty()) { // Verificar si la cola está vacía usando el método isEmpty
            return null; // Retornar null si no hay elementos
        }
        return heap[0]; // Retornar la raíz (mayor prioridad) sin quitarla
    } // Fin del método peek

    public String getQueueInfo() { // Método para obtener información de la cola
        if (isEmpty()) { 
            return "La cola está vacía"; 
        }
        
        StringBuilder info = new StringBuilder(); 
        info.append("=== COLA DE PRIORIDAD ===\n"); 
        info.append("Total de tareas: ").append(size).append("\n\n"); 
        
        for (int i = 0; i < size; i++) { 
            info.append(i + 1).append(". ").append(heap[i].toString()).append("\n"); 
        }
        
        return info.toString(); 
    } // Fin del método getQueueInfo

    public UrgentTask[] getAllTasks() { // Método para obtener todas las tareas (no necesariamente ordenadas)
        if (isEmpty()) { // Verificar si la cola está vacía
            return new UrgentTask[0]; // Retornar array vacío si no hay elementos
        }
        
        UrgentTask[] result = new UrgentTask[size]; // Crear array para el resultado
        for (int i = 0; i < size; i++) { // Recorrer todos los elementos de la cola
            result[i] = heap[i]; // Copiar cada tarea al resultado (estructura de heap)
        }
        return result; // Retornar array con todas las tareas en estructura heap
    } // Fin del método getAllTasks

    public int getPriorityOfTask(UrgentTask task) { // Método para obtener la prioridad de una tarea
        for (int i = 0; i < size; i++) { // Recorrer todas las tareas en la cola
            if (heap[i] == task) { // Si encontramos la tarea que buscamos
                return priorities[i]; // Retornar su prioridad
            }
        }
        return -1; // Retornar -1 si no se encuentra la tarea
    } // Fin del método getPriorityOfTask
} // Fin de la clase PriorityQueue
