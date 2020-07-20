# Modelo de Dominio

@startuml
object Departamento {
  numero
}

object Persona {
    rut
    nombre
    telefono
    email
}

object Registro {
    fecha
    parentesco
    empresaEntrega
}

note top of Registro : Las visitas pueden hacerlas\nfamiliares, externos o empresas.\nLas empresas pueden ser de \nentrega/encomiendas.

Persona "1" --> "*" Departamento : pertenece
Registro "*" --> "1" Persona : se registra 
Registro "*" --> "1" Departamento : se visita
@enduml
