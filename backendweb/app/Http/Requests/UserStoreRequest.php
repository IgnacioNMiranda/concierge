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
            'name' => 'required|min:3|max:255',
            'email' => 'email:rfc,dns|required|unique:users,email',
            'password' => 'required|min:8|max:255|confirmed',
        ];
    }

    public function messages(){
        return [
            'name.required' => 'El campo nombre es obligatorio.',
            'name.min' => 'El campo nombre debe tener mínimo 3 caracteres.',
            'name.max' => 'EL campo nombre no debe tener más de 255 caracteres.',
            'email.email' => 'El campo email debe ser válido.',
            'email.required' => 'El campo email es obligatorio.',
            'email.unique' => 'Ya existe un usuario con este correo.',
            'password.required' => 'El campo contraseña es obligatorio.',
            'password.min' => 'El campo contraseña debe tener mínimo 8 caracteres.',
            'password.max' => 'EL campo contraseña no debe tener más de 255 caracteres.',
            'password.confirmed' => 'Debe confirmar la contraseña.',
            //'password.regex' => 'La contraseña debe poseer al menos 1 mayúscula, 1 minúscula, 1 número, 1 carácter especial y debe tener una longitud superior a 8 caracteres.',
        ];
    }
}
