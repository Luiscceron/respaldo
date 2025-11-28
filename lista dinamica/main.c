#include <stdio.h>
#include "array_list.h" 

int main() {
	printf("--- PRUEBA FINAL ---\n");
	
	// 1. Crear
	printf("1. Creando lista con capacidad 3...\n");
	ArrayList* myArrayList = arraylist_create(3);
	
	// 2. Agregar
	printf("2. Agregando elementos...\n");
	arraylist_add(myArrayList, 10);
	arraylist_add(myArrayList, 20);
	arraylist_add(myArrayList, 30);
	arraylist_add(myArrayList, 40);
	
	// 3. Insertar
	printf("3. Insertando 99...\n");
	arraylist_insert(myArrayList, 99, 1);
	
	// Verificación usando 'tamanio'
	printf("   [Estado actual]: ");
	for(int i=0; i < myArrayList->tamanio; i++) {
		printf("%d ", arraylist_get(myArrayList, i));
	}
	printf("\n");
	
	// 4. Obtener
	int val = arraylist_get(myArrayList, 2); 
	printf("4. Valor en indice 2: %d\n", val);
	
	// 5. Remover
	printf("5. Removiendo 2 elementos...\n");
	arraylist_remove(myArrayList, 0); 
	arraylist_remove(myArrayList, 0); 
	
	// Verificación final
	printf("   [Final]: ");
	for(int i=0; i < myArrayList->tamanio; i++) {
		printf("%d ", arraylist_get(myArrayList, i));
	}
	printf("\n");
	
	// 6. Destruir
	arraylist_destroy(myArrayList);
	
	// Pausa para que no se cierre
	printf("\nPresiona Enter para salir...");
	getchar(); 
	
	return 0;
}
