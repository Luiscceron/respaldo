#include <stdio.h>
#include <stdlib.h>
#include "array_list.h"

ArrayList* arraylist_create(int capacidad_inicial) {
	ArrayList* lista = (ArrayList*)malloc(sizeof(ArrayList));
	if (lista == NULL) return NULL;
	
	lista->datos = (int*)malloc(capacidad_inicial * sizeof(int));
	if (lista->datos == NULL) {
		free(lista);
		return NULL;
	}
	
	lista->capacidad = capacidad_inicial;
	lista->tamanio = 0; // Usamos tamanio
	return lista;
}

void arraylist_add(ArrayList* lista, int elemento) {
	if (lista->tamanio >= lista->capacidad) {
		lista->capacidad *= 2;
		lista->datos = (int*)realloc(lista->datos, lista->capacidad * sizeof(int));
	}
	lista->datos[lista->tamanio] = elemento;
	lista->tamanio++;
}

void arraylist_insert(ArrayList* lista, int elemento, int indice) {
	if (indice < 0 || indice > lista->tamanio) return;
	
	if (lista->tamanio >= lista->capacidad) {
		lista->capacidad *= 2;
		lista->datos = (int*)realloc(lista->datos, lista->capacidad * sizeof(int));
	}
	
	for (int i = lista->tamanio; i > indice; i--) {
		lista->datos[i] = lista->datos[i - 1];
	}
	
	lista->datos[indice] = elemento;
	lista->tamanio++;
}

int arraylist_get(ArrayList* lista, int indice) {
	if (indice < 0 || indice >= lista->tamanio) {
		printf("Error: Indice fuera de rango\n");
		return -1;
	}
	return lista->datos[indice];
}

void arraylist_remove(ArrayList* lista, int indice) {
	if (indice < 0 || indice >= lista->tamanio) return;
	
	for (int i = indice; i < lista->tamanio - 1; i++) {
		lista->datos[i] = lista->datos[i + 1];
	}
	lista->tamanio--;
}

void arraylist_destroy(ArrayList* lista) {
	if (lista != NULL) {
		if (lista->datos != NULL) {
			free(lista->datos);
		}
		free(lista);
		printf("Lista destruida.\n");
	}
}
