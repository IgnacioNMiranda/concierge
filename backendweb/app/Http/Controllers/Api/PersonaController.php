<?php

namespace App\Http\Controllers\Api;

use App\Departamento;
use App\Http\Controllers\Controller;
use App\Http\Requests\PersonaRequest;
use App\Http\Resources\PersonaResource;
use App\Persona;
use Illuminate\Http\Response;

class PersonaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
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
     * @param PersonaRequest $request
     * @return Response
     * @noinspection PhpUndefinedMethodInspection
     * @noinspection PhpUndefinedFieldInspection
     */
    public function store(PersonaRequest $request)
    {
        $persona = null;

        if ($request->has('numeroDepartamento')) {
            $numeroDepartamento = $request->numeroDepartamento;
            $departamento = Departamento::where('numero', $numeroDepartamento)->firstOrFail();

            $persona = Persona::create([
                'rut' => $request->input('rut'),
                'nombre' => $request->input('nombre'),
                'telefono' => $request->input('telefono'),
                'email' => $request->input('email'),
                'departamento_id' => $departamento->id
            ]);
        } else {
            $data = $request->all();
            $persona = Persona::create($data);
        }

        return response([
            'message' => 'Created Successfully',
            'persona' => new PersonaResource($persona),
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param Persona $persona
     * @return Response
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
     * @param PersonaRequest $request
     * @param Persona $persona
     * @return Response
     */
    public function update(PersonaRequest $request, Persona $persona)
    {
        $persona->update($request->only('telefono', 'email', 'departamento_id'));

        return response([
            'message' => 'Updated Successfully',
            'persona' => new PersonaResource($persona),
        ], 202);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param Persona $persona
     * @return Response
     * @throws \Exception
     */
    public function destroy(Persona $persona)
    {
        $persona->delete();

        return response([
            'message' => 'Deleted Successfully',
        ], 202);
    }
}
