<?php

namespace App\Http\Requests\User;

use Illuminate\Foundation\Http\FormRequest;

class UserLoginRequest extends FormRequest
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
            'email' => 'email:rfc,dns|min:8|max:255|required',
            'password' => 'required',
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
            'email.email' => 'El campo email debe ser válido.',
            'email.min' => 'Debe ser de mas de 8 caracteres.',
            'email.max' => 'Debe ser de menos de 255 caracteres.',
            'email.required' => 'El campo email es obligatorio.',
            'password.required' => 'El campo contraseña es obligatorio.',
        ];
    }
}
