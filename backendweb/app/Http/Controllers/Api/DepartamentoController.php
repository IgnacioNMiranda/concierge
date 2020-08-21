<?php /** @noinspection PhpUndefinedMethodInspection */

namespace App\Http\Controllers\Api;

use App\Departamento;
use App\Http\Controllers\Controller;
use App\Http\Requests\DepartamentoRequest;
use App\Http\Resources\DepartamentoResource;
use Exception;
use Illuminate\Http\Response;

class DepartamentoController extends Controller
{

    /**
     * Display a listing of the resource.
     *
     * @return Response
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
     * @return Response
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
     * @return Response
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
     * @param DepartamentoRequest $request
     * @param  Departamento  $departamento
     * @return Response
     */
    public function update(DepartamentoRequest $request, Departamento $departamento)
    {
        $data = $request->all();

        $departamento->update($data);

        return response([
            'message' => 'Updated Successfully',
            'departamento' => new DepartamentoResource($departamento),
        ], 202);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param Departamento $departamento
     * @return Response
     * @throws Exception
     */
    public function destroy(Departamento $departamento)
    {
        $departamento->delete();

        return response([
            'message' => 'Deleted successfully',
        ], 202);
    }
}
