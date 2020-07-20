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
            'rut' => $this->rut,
            'name' => $this->nombre,
            'phone' => $this->telefono,
            'email' => $this->email,
        ];
    }
}
