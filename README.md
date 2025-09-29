# 📚 Proyecto Final - Estructuras de Datos

## 🎯 Descripción
Implementación completa de las 7 estructuras de datos requeridas para el proyecto final de la materia:

1. **Cola de Prioridad (Heap)** - Tareas urgentes
2. **Lista Doblemente Enlazada** - Tareas por departamento  
3. **Árbol Binario de Búsqueda** - Gestión de empleados
4. **Recursividad** - Análisis de tareas departamentales
5. **HashMap (Empleados)** - Gestión eficiente de personal
6. **HashMap (Asignación de Tareas)** - Relación empleado-tarea
7. **Grafo Dirigido** - Dependencias entre tareas

## 🚀 Características
- ✅ Interfaz gráfica completa con JOptionPane
- ✅ Sistema integrado de gestión empresarial
- ✅ Implementación de algoritmos recursivos
- ✅ Análisis de dependencias con ordenamiento topológico
- ✅ Búsqueda y ordenamiento optimizados

## 🛠️ Cómo ejecutar
```bash
cd proyectoFinal
javac *.java
java proyectoFinal.Main
```

## 📁 Estructura del Proyecto
```
proyectoFinal/
├── Main.java                    # Programa principal
├── Employee.java                # Clase Empleado
├── DepartmentTask.java          # Tarea con recursividad
├── UrgentTask.java              # Tarea urgente para cola
├── PriorityQueue.java           # Cola de prioridad (Heap)
├── List.java                    # Lista doblemente enlazada
├── EmployeeBinaryTree.java      # Árbol binario de búsqueda
├── EmployeeHashManager.java     # HashMap de empleados
├── TaskAssignmentHashMap.java   # HashMap de asignaciones
├── TaskDependencyGraph.java     # Grafo de dependencias
└── SearchAndSortAlgorithms.java # Algoritmos adicionales
```

## 📋 Funcionalidades Principales

### 🔥 Cola de Prioridad
- Agregar tareas urgentes con prioridad numérica
- Procesar tareas por orden de prioridad
- Visualización del ordenamiento heap

### 🌳 Árbol Binario
- Inserción, eliminación y búsqueda de empleados
- Recorridos: InOrder, PreOrder, PostOrder  
- Estadísticas del árbol

### 🔄 Recursividad
- `calculateTotalEstimatedTime()` - Tiempo total con subtareas
- `countAllTasks()` - Conteo recursivo de tareas
- `calculateRemainingTime()` - Tiempo pendiente

### 📊 HashMaps
- **Empleados**: Búsqueda O(1) por ID, nombre o departamento
- **Asignaciones**: Vinculación many-to-many tareas-empleados

### 🕸️ Grafos
- Modelado de dependencias entre tareas
- Detección de ciclos
- Ordenamiento topológico para secuencia de ejecución

## 🎓 Desarrollado para
**Materia**: Estructuras de Datos  
**Semana**: 7  
**Año**: 2024

## 👨‍💻 Autor
Gabriel - Proyecto Final Estructuras de Datos