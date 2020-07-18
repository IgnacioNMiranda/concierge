# Diagrama de Clases

@startuml
class Departamento {
    -numDepto: int
    +methods(type): type
}

class Persona {
    -rut: String
    -nombre: String
    -telefono: String
    -email: String
    +methods(type): type
}

class Registro {
    -fecha: Date
    +methods(type): type
}

class PersonaRegistro {
    -parentesco: Enumerator
    -empresaEntrega: bool
    +methods(type): type
}

Persona "1" --> "*" Departamento
Departamento "1" --> "*" Registro
Persona "*" --> "*" PersonaRegistro
Registro "*" --> "*" PersonaRegistro
@enduml
