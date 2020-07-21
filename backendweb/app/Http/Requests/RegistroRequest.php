<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class RegistroRequest extends FormRequest
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
            'fecha' => 'required|before_or_equal:now',
            'parentesco' => 'required|in:Familiar,Externo,Empresa',
            'persona_id' => 'required|exists:personas,id',
            'departamento_id' => 'required|exists:departamentos,id',
        ];
    }

    /**
     * Get the error messages for the defined validation rules.
     *
     * @return array
     */
    public function messages()
    {
        return [
            'fecha.required' => 'El campo fecha es obligatorio.',
            'fecha.before_or_equal' => 'La fecha no puede ser futura.',
            'parentesco.required' => 'El campo parentesco es obligatorio.',
            'parentesco.in' => 'El campo parentesco sólo puede ser Familiar, Externo o Empresa.',
            'persona_id.required' => 'El campo persona_id es obligatorio.',
            'persona_id.exists' => 'La persona ingresada no existe.',
            'departamento_id.required' => 'El campo departamento_id es obligatorio.',
            'departamento_id.exists' => 'El departamento ingresado no existe.',
        ];
    }
}
