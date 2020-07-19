<?php

namespace App\Http\Requests\Persona;

use Illuminate\Foundation\Http\FormRequest;

class PersonaStoreRequest extends FormRequest
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
        return [
            'rut' => 'required|cl_rut|min:8|max:9|unique:personas,rut',
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
            'rut.cl_rut' => 'El rut ingresado no es valido.',
            'rut.min' => 'El campo rut debe tener mínimo 8 caracteres.',
            'rut.max' => 'EL campo rut no debe tener más de 9 caracteres.',
            'rut.unique' => 'Este rut ya se encuentra registrado.',
            'nombre.required' => 'El campo nombre es obligatorio.',
            'nombre.min' => 'El campo nombre debe tener mínimo 3 caracteres.',
            'nombre.max' => 'EL campo nombre no debe tener más de 255 caracteres.',
            'telefono.max' => 'EL campo telefono no puede tener mas de 50 caracteres.',
            'email.email' => 'El campo email debe ser válido.',
            'email.required' => 'El campo email es obligatorio.',
            'email.unique' => 'Ya existe una persona con este correo.',
        ];
    }

    /**
     * Prepare the data for validation.
     * This overrides the method from ValidatesWhenResolvedTrait.
     * It executes before the rules validation.
     * source: https://sampo.co.uk/blog/manipulating-request-data-before-performing-validation-in-laravel.
     * @return void
     */
    protected function prepareForValidation()
    {
        $this->merge([
            'rut' => strtolower($this->rut),
        ]);
    }
}
