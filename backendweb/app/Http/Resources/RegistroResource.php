<?php /** @noinspection PhpUndefinedMethodInspection */

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class RegistroResource extends JsonResource
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
            'fecha' => $this->fecha,
            'parentesco' => $this->parentesco,
            'empresaEntrega' => $this->when($this->parentesco == "Empresa", $this->empresaEntrega),
            'persona' => PersonaResource::make($this->persona),
            'departamento' => DepartamentoResource::make($this->departamento),
        ];
    }
}
