#ifndef ARRAY_LIST_H
#define ARRAY_LIST_H

// Definición de la estructura del ArrayList
typedef struct {
	int* datos;     
	int capacidad;  
	int tamanio;    
} ArrayList;

// Prototipos
ArrayList* arraylist_create(int capacidad_inicial);
void arraylist_add(ArrayList* lista, int elemento);
void arraylist_insert(ArrayList* lista, int elemento, int indice);
int arraylist_get(ArrayList* lista, int indice);
void arraylist_remove(ArrayList* lista, int indice);
void arraylist_destroy(ArrayList* lista);

#endif
