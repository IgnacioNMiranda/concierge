<?php

namespace App\Http\Requests\Persona;

use Illuminate\Foundation\Http\FormRequest;

class PersonaUpdateRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        /** @noinspection PhpUndefinedFieldInspection */
        return [
            'rut' => 'required|min:8|max:9|unique:personas,rut,' . $this->rut,
            'nombre' => 'required|min:3|max:255',
            'telefono' => 'max:50',
            'email' => 'email:rfc,dns|required|max:255|unique:personas,email',
        ];
    }

    /**
     * Get the validation messages that apply to the request.
     *
     * @return array
     */
    public function messages()
    {
        return [
            'rut.required' => 'El campo rut es obligatorio.',
            'rut.min' => 'El campo rut debe tener mínimo 8 caracteres.',
            'rut.max' => 'EL campo rut no debe tener más de 9 caracteres.',
            'rut.unique' => 'Este rut ya se encuentra registrado.',
            'nombre.required' => 'El campo nombre es obligatorio.',
            'nombre.min' => 'El campo nombre debe tener mínimo 3 caracteres.',
            'nombre.max' => 'EL campo nombre no debe tener más de 255 caracteres.',
            'telefono.max' => 'EL campo telefono no puede tener mas de 50 caracteres.',
            'email.email' => 'El campo email debe ser válido.',
            'email.required' => 'El campo email es obligatorio.',
            'email.max' => 'EL email no puede tener mas de 255 caracteres.',
            'email.unique' => 'Ya existe una persona con este correo.',
        ];
    }
}
