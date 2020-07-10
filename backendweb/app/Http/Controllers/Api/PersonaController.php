<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\PersonaResource;
use App\Persona;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class PersonaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response([
            'message' => 'Retrieved Successfully',
            'personas' => PersonaResource::collection(Persona::all()),
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data = $request->all();

        $validator = Validator::make($data, [
            'rut' => 'required|max:50',
            'name' => 'required|max:255',
            'phone' => 'max:50',
            'email' => 'email:rfc,dns|required|max:255',
        ]);

        if ($validator->fails()) {
            return response([
                'message' => 'Validation Error',
                'error' => $validator->errors(),
            ], 412);
        }

        /** @noinspection PhpUndefinedMethodInspection */
        $persona = Persona::create($data);

        return response([
            'message' => 'Created Successfully',
            'persona' => new PersonaResource($persona),
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Persona  $persona
     * @return \Illuminate\Http\Response
     */
    public function show(Persona $persona)
    {
        return response([
            'message' => 'Retrieved Successfully',
            'persona' => new PersonaResource($persona),
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Persona  $persona
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Persona $persona)
    {
        // TODO: validate, only phone and email can be updated
        $persona->update($request->all());

        return response([
            'message' => 'Updated Successfully',
            'persona' => new PersonaResource($persona),
        ], 202);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Persona  $persona
     * @return \Illuminate\Http\Response
     */
    public function destroy(Persona $persona)
    {
        // TODO: Exception
        $persona->delete();

        return response([
            'message' => 'Deleted Successfully',
        ], 202);
    }
}
