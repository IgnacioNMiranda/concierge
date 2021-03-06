<?php /** @noinspection PhpUndefinedFieldInspection */

namespace App\Http\Requests;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Http\Exceptions\HttpResponseException;

class DepartamentoRequest extends FormRequest
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
        $method = $this->method();

        switch ($method){
            case "POST":
                return [
                    'numero' => 'required|unique:departamentos,numero',
                ];
            case "PATCH":
                return [
                    'numero' => 'required|unique:departamentos,numero,' . $this->departamento->id,
                ];
        }

        return [];
    }

    /**
     * Get the error messages for the defined validation rules.
     *
     * @return array
     */
    /*
    public function messages()
    {
        return [
            'numero.required' => 'El campo numero de departamento es obligatorio.',
            'numero.unique' => 'Este numero de departamento ya esta registrado.',
        ];
    }*/

    /**
     * Instead of throws a 500 error, makes a response with the error messages.
     * @param Validator $validator
     * @throws HttpResponseException
     */
    public function failedValidation(Validator $validator)
    {
        $errors = $validator->errors(); // Array of errors.

        throw new HttpResponseException(response([
            $errors
        ], 422));
    }
}
