//COLAS PRIORIZADAS CON HEAP (SIN LÍMITE DE PRIORIDADES)
// Implementación de cola de prioridad usando heap binario para gestionar tareas urgentes
package proyectoFinal;

public class PriorityQueue {
    // ATRIBUTOS (variables de la clase)
    final int capacity = 100; // Capacidad máxima de la cola
    int size; // Tamaño actual de la cola 
    private UrgentTask[] heap; // Array para almacenar las tareas urgentes como heap
    private int[] priorities; // Array para almacenar las prioridades correspondientes

    // CONSTRUCTOR (método que se ejecuta al crear un objeto)
    public PriorityQueue() { 
        this.heap = new UrgentTask[this.capacity]; // crea array de tareas con tamaño capacity
        this.priorities = new int[this.capacity]; // crea array de prioridades con tamaño capacity
        this.size = 0; //inicia en 0
    }

    // MÉTODOS BÁSICOS 
    public boolean isEmpty() { // Método para verificar si la cola está vacía
        return this.size == 0; // Retorna true si size es 0, false si tiene elementos
    } 

    public int getSize() { // Método para obtener el tamaño actual de la cola
        return this.size; // Retorna el número de elementos que tiene la cola
    } 

    // MÉTODOS DE HEAP (lógica de árbol binario para mantener orden)
    private int getParentIndex(int childIndex) { // Obtener índice del padre en el heap
        return (childIndex - 1) / 2; // Fórmula matemática para encontrar padre en heap
    } 

    private int getLeftChildIndex(int parentIndex) { // Obtener índice del hijo izquierdo
        return 2 * parentIndex + 1; // Fórmula matemática para hijo izquierdo
    } 

    private int getRightChildIndex(int parentIndex) { // Obtener índice del hijo derecho
        return 2 * parentIndex + 2; // Fórmula matemática para hijo derecho
    }

    private boolean hasParent(int index) { // Verificar si un nodo tiene padre
        return getParentIndex(index) >= 0; // Verdadero si el índice del padre es válido
    }

    private boolean hasLeftChild(int index) { // Verificar si un nodo tiene hijo izquierdo
        return getLeftChildIndex(index) < size; // Verdadero si el índice está dentro del tamaño
    }

    private boolean hasRightChild(int index) { // Verificar si un nodo tiene hijo derecho
        return getRightChildIndex(index) < size; // Verdadero si el índice está dentro del tamaño
    }

    private void swap(int indexOne, int indexTwo) { // Intercambiar dos elementos en el heap
        // Intercambiar tareas
        UrgentTask tempTask = heap[indexOne]; // Guardar temporalmente la primera tarea
        heap[indexOne] = heap[indexTwo]; // Mover segunda tarea a primera posición
        heap[indexTwo] = tempTask; // Mover primera tarea (temporal) a segunda posición
        
        // Intercambiar prioridades
        int tempPriority = priorities[indexOne]; // Guardar temporalmente la primera prioridad
        priorities[indexOne] = priorities[indexTwo]; // Mover segunda prioridad a primera posición
        priorities[indexTwo] = tempPriority; // Mover primera prioridad (temporal) a segunda posición
    }

    private void heapifyUp() { // Reordenar hacia arriba para mantener propiedad del heap (min-heap)
        int index = size - 1; // Empezar desde el último elemento insertado
        // Continuar mientras tenga padre y su prioridad sea menor (mayor prioridad)
        while (hasParent(index) && priorities[getParentIndex(index)] > priorities[index]) {
            swap(getParentIndex(index), index); // Intercambiar con el padre
            index = getParentIndex(index); // Moverse hacia arriba en el heap
        }
    } 

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
    }

    public void push(int priority, UrgentTask task) { // Método para agregar una tarea (SIN LÍMITE de prioridades)
        // Verificar si la cola está llena
        if (this.size >= this.capacity) { // Si ya se llegó al límite de capacidad
            System.out.println("LA COLA ESTA LLENA"); // Mostrar mensaje de error
            return; 
        }
        
        // IMPLEMENTACIÓN CON HEAP: Agregar al final y hacer heapify up
        heap[size] = task; // Colocar tarea en la siguiente posición disponible
        priorities[size] = priority; // Colocar prioridad (SIN RESTRICCIONES de valor)
        size++; 
        heapifyUp(); // Reordenar el heap hacia arriba para mantener propiedad
    }

    // MÉTODOS PRINCIPALES DE COLA DE PRIORIDAD
    public void enqueue(UrgentTask task) { // Método para agregar tarea a la cola
        push(task.getPriority(), task); // Usar el método push existente
    } 

    public UrgentTask dequeue() { // Método para obtener y remover tarea de mayor prioridad
        return pop(); // Usar el método pop existente
    } 
            
    public UrgentTask pop() { // Método para extraer la tarea de mayor prioridad usando heap
        if (isEmpty()) { // Verificar si la cola está vacía usando el método isEmpty
            System.out.println("LA COLA ESTA VACÍA."); // Mostrar mensaje de error
            return null; // no hay elementos para extraer
        }
        
        // IMPLEMENTACIÓN CON HEAP: Extraer raíz y reordenar
        UrgentTask result = heap[0]; // La raíz siempre tiene la mayor prioridad (menor número)
        
        // Mover último elemento a la raíz
        heap[0] = heap[size - 1]; // Mover última tarea a la raíz
        priorities[0] = priorities[size - 1]; // Mover última prioridad a la raíz
        
        size--; 
        heap[size] = null; // Limpiar la última posición
        priorities[size] = 0; // Limpiar la última prioridad
        
        heapifyDown(); // Reordenar el heap hacia abajo desde la raíz
        
        return result; // Retornar la tarea extraída
    } 

    public UrgentTask peek() { // Método para ver la tarea de mayor prioridad sin extraerla
        if (isEmpty()) { // Verificar si la cola está vacía usando el método isEmpty
            return null; // Retorna null si no hay elementos
        }
        return heap[0]; // Retornar la raíz (mayor prioridad) sin quitarla
    } 

    public String getQueueInfo() { // Método para obtener información de la cola
        if (isEmpty()) { 
            return "La cola está vacía"; 
        }
        
        StringBuilder info = new StringBuilder(); // Crear StringBuilder para concatenación eficiente
        info.append("=== COLA DE PRIORIDAD ===\n"); 
        info.append("Total de tareas: ").append(size).append("\n\n"); // Mostrar cantidad total de tareas
        
        for (int i = 0; i < size; i++) { // Recorrer todas las tareas en el heap
            info.append(i + 1).append(". ").append(heap[i].toString()).append("\n"); // Agregar cada tarea numerada
        }
        
        return info.toString(); // Convertir StringBuilder a String y retornar resultado completo
    } 

    public UrgentTask[] getAllTasks() { // Método para obtener todas las tareas 
        if (isEmpty()) {
            return new UrgentTask[0]; // Retorna array vacío si no hay elementos
        }
        
        UrgentTask[] result = new UrgentTask[size]; // Crear array para el resultado
        for (int i = 0; i < size; i++) { // Recorrer todos los elementos de la cola
            result[i] = heap[i]; // Copiar cada tarea al resultado 
        }
        return result; // Retornar array con todas las tareas en estructura heap
    } 

    public int getPriorityOfTask(UrgentTask task) { // Método para obtener la prioridad de una tarea
        for (int i = 0; i < size; i++) { // Recorrer todas las tareas en la cola
            if (heap[i] == task) { // Si encontramos la tarea que buscamos
                return priorities[i]; // Retornar su prioridad
            }
        }
        return -1;
    }
}
