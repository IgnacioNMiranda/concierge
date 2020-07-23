# Diagrama de Clases

@startuml
class Departamento {
    -numero: int
    +personas(): Collection<Persona>
    +registro(): Registro
}

class Persona {
    -rut: String
    -nombre: String
    -telefono: String
    -email: String
    +registros(): Collection<Registro>
    +departamento(): Departamento
}

class Registro {
    -fecha: Date
    -parentesco: Enum
    -empresaEntrega: bool
    +persona(): Persona
    +departamento(): Departamento
}

note top of Registro : Las visitas pueden hacerlas\nfamiliares, externos o empresas.\nLas empresas pueden ser de \nentrega/encomiendas.

Persona "1" --> "*" Departamento : pertenece
Registro "*" --> "1" Departamento : se visita
Registro "*" --> "1" Persona : se registra
@enduml
