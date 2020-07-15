<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\Persona\PersonaStoreRequest;
use App\Http\Requests\Persona\PersonaUpdateRequest;
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
     * @param PersonaStoreRequest $request
     * @return Response
     */
    public function store(PersonaStoreRequest $request)
    {
        $data = $request->all();

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
     * @param  PersonaUpdateRequest  $request
     * @param Persona $persona
     * @return Response
     */
    public function update(PersonaUpdateRequest $request, Persona $persona)
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
