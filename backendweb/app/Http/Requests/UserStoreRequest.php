<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class UserStoreRequest extends FormRequest
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
            'name' => 'required|max:50',
            'email' => 'required|unique:users,email',
            'password' => 'required|confirmed',
        ];
    }

    public function messages(){
        return [
            'name.required' => 'El campo nombre es obligatorio.',
            'email.required' => 'El campo email es obligatorio.',
            'email.unique' => 'Ya existe un usuario con este correo.',
            'password.required' => 'El campo contraseña es obligatorio.',
            //'password.regex' => 'La contraseña debe poseer al menos 1 mayúscula, 1 minúscula, 1 número, 1 carácter especial y debe tener una longitud superior a 8 caracteres.',
        ];
    }
}
