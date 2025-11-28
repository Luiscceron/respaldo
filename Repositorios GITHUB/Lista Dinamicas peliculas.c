#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Definí constantes para los tamaños máximos
#define MAX_TITULO 100
#define MAX_DIRECTOR 50

typedef struct {
	char titulo[MAX_TITULO];
	char director[MAX_DIRECTOR];
	int anio;
} Pelicula;

typedef struct Nodo {
	Pelicula dato;
	struct Nodo* siguiente;
} Nodo;

// Creé esta función auxiliar para reservar memoria y llenar los datos
Nodo* crearNodo(char* titulo, char* director, int anio) {
	Nodo* nuevo = (Nodo*)malloc(sizeof(Nodo));
	if (nuevo == NULL) exit(1); 
	
	// Usé strncpy para copiar de forma segura y limité el tamaño
	strncpy(nuevo->dato.titulo, titulo, MAX_TITULO);
	nuevo->dato.titulo[MAX_TITULO - 1] = '\0'; // Aseguro el final de la cadena
	
	strncpy(nuevo->dato.director, director, MAX_DIRECTOR);
	nuevo->dato.director[MAX_DIRECTOR - 1] = '\0';
	
	nuevo->dato.anio = anio;
	nuevo->siguiente = NULL;
	return nuevo;
}

// Conecto el nuevo nodo al final de la lista
void insertarAlFinal(Nodo** cabeza, char* titulo, char* director, int anio) {
	Nodo* nuevo = crearNodo(titulo, director, anio); // Llamo a la función de creación
	
	if (*cabeza == NULL) {
		*cabeza = nuevo; // Si la lista está vacía, lo asigno como cabeza
	} else {
		Nodo* temp = *cabeza;
		while (temp->siguiente != NULL) {
			temp = temp->siguiente; // Recorro hasta el último nodo
		}
		temp->siguiente = nuevo; // Enlazo el último con el nuevo
	}
}

// Recorro e imprimo toda la lista
void mostrarLista(Nodo* cabeza) {
	printf("\n--- MOVIE LIST ---\n");
	while (cabeza != NULL) {
		printf("Movie: %s | Year: %d | Director: %s\n", 
			   cabeza->dato.titulo, cabeza->dato.anio, cabeza->dato.director);
		cabeza = cabeza->siguiente;
	}
}

// Libero la memoria de toda la lista nodo por nodo
void liberarLista(Nodo* cabeza) {
	Nodo* temp;
	while (cabeza != NULL) {
		temp = cabeza;
		cabeza = cabeza->siguiente;
		free(temp);
	}
	printf("Memoria liberada.\n");
}

// Elimino el salto de línea que deja fgets
void quitarSalto(char* str) {
	size_t len = strlen(str);
	if (len > 0 && str[len-1] == '\n') str[len-1] = '\0';
}

int main() {
	Nodo* lista = NULL;
	char titulo[MAX_TITULO], director[MAX_DIRECTOR];
	int anio;
	char op;
	
	do {
		printf("\nTítulo: ");
		fgets(titulo, MAX_TITULO, stdin);
		quitarSalto(titulo);
		
		printf("Director: ");
		fgets(director, MAX_DIRECTOR, stdin);
		quitarSalto(director);
		
		printf("Año: ");
		if (scanf("%d", &anio) != 1) break; // Verifico que la entrada sea un número
		while(getchar() != '\n'); // Limpio el buffer de entrada
		
		insertarAlFinal(&lista, titulo, director, anio);
		
		printf("¿Otra? (si quieres agregar otra solo escibe si, y si ya quieres ver la cartelera escribe no): ");
		scanf("%c", &op);
		while(getchar() != '\n'); // Limpio el buffer de nuevo
		
	} while(op == 's' || op == 'S');
	
	mostrarLista(lista);
	liberarLista(lista);
	
	return 0;
}
