<?php

namespace App\Http\Requests;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;

class UserRequest extends FormRequest
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
        $path = substr($this->path(), 4);

        switch ($path) {
            case "register":
                return [
                    'name' => 'required|min:3|max:255',
                    'email' => 'email:rfc,dns|required|unique:users,email',
                    'password' => 'required|min:8|max:255|confirmed',
                ];
            case "login":
                return [
                    'email' => 'email:rfc,dns|min:8|max:255|required',
                    'password' => 'required',
                ];
        }

        return [];
    }

    /**
     * Get the validation messages that apply to the request.
     *
     * @return array
     */
    public function messages()
    {
        return [
            'name.required' => 'El campo nombre es obligatorio.',
            'name.min' => 'El campo nombre debe tener minimo 3 caracteres.',
            'name.max' => 'EL campo nombre no debe tener mas de 255 caracteres.',
            'email.email' => 'El campo email debe ser valido.',
            'email.required' => 'El campo email es obligatorio.',
            'email.unique' => 'Ya existe un usuario con este correo.',
            'password.required' => 'La contrasenia es obligatoria.',
            'password.min' => 'La contrasenia debe tener minimo 8 caracteres.',
            'password.max' => 'La contrasenia no debe tener mas de 255 caracteres.',
            'password.confirmed' => 'Debe confirmar la contrasenia.',
        ];
    }

    public function failedValidation(Validator $validator)
    {
        $errors = $validator->errors(); // Array of errors.

        $json = $errors->toJson();

        return response([
            'message' => 'validation error',
           'validation_errors' => $json
        ]);
    }
}
