<?php

namespace App\Http\Controllers\API;

use App\Departamento;
use App\Http\Controllers\Controller;
use App\Http\Requests\DepartamentoRequest;
use App\Http\Resources\DepartamentoResource;
use Illuminate\Http\Request;

class DepartamentoController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response ([
            'message' => 'Retrieved Successfully',
            'departamentos' => DepartamentoResource::collection(Departamento::all()),
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  DepartamentoRequest $request
     * @return \Illuminate\Http\Response
     */
    public function store(DepartamentoRequest $request)
    {
        $data = $request->all();

        /** @noinspection PhpUndefinedMethodInspection */
        $departamento = Departamento::create($data);

        return response([
            'message' => 'Created Successfully',
            'departamento' => new DepartamentoResource($departamento),
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  Departamento  $departamento
     * @return \Illuminate\Http\Response
     */
    public function show(Departamento $departamento)
    {
        return response([
            'message' => 'Retrieved Successfully',
            'departamento' => new DepartamentoResource($departamento),
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Departamento  $departamento
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Departamento $departamento)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Departamento  $departamento
     * @return \Illuminate\Http\Response
     */
    public function destroy(Departamento $departamento)
    {
        //
    }
}
