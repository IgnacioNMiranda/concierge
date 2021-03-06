<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class PersonaResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  Request  $request
     * @return array
     */
    public function toArray($request)
    {
        /** @noinspection PhpUndefinedFieldInspection */
        return [
            'id' => $this->id,
            'rut' => $this->rut,
            'nombre' => $this->nombre,
            'telefono' => $this->telefono,
            'email' => $this->email,
            'departamento' => $this->when($this->departamento != null, DepartamentoResource::make($this->departamento)),
        ];
    }
}
