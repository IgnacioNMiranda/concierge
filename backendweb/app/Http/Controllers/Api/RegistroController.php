<?php /** @noinspection PhpUndefinedMethodInspection */

namespace App\Http\Controllers\Api;

use App\Departamento;
use App\Http\Controllers\Controller;
use App\Http\Resources\RegistroResource;
use App\Persona;
use App\Registro;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

class RegistroController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function index()
    {
        $registros = Registro::orderBy('fecha', 'ASC')->get();

        return response([
            'message' => 'Retrieved Successfully',
            'registros' => RegistroResource::collection($registros),
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return Response
     */
    public function store(Request $request)
    {
        $datos = $request->all();

        $registro = Registro::create($datos);

        // Searches the apartment based on the id received on Request.
        $dept = Departamento::find($request->get('departamento_id'));

        // Creates the relation 1-n with the apartment.
        $registro->departamento()->associate($dept);

        // Searches the apartment based on the id received on Request.
        $dept = Persona::find($request->get('persona_id'));

        // Creates the relation n-n with the people that visits the apartment.
        $registro->persona()->associate($dept);

        $registro->save();

        return response([
            'message' => "Visit registered successfully",
            'register' => new RegistroResource($registro),
        ]);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $departamento_id
     * @return Response
     */
    public function show($departamento_id)
    {
        $dept = Departamento::where('id', $departamento_id)->get();

        $registros = $dept->registros()->get();
        return response([
            'message' => 'Retrieved Successfully',
            'registros' => RegistroResource::collection($registros),
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param  int  $id
     * @return Response
     */
    public function update(Request $request, $id)
    {
        $registro = Registro::where('id', $id)->get();

        $registro->update($request->only('parentesco', 'empresaEntrega'));

        return response([
            'message' => 'Updated Successfully',
            'registro' => new RegistroResource($registro),
        ], 202);

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function destroy($id)
    {
        Registro::destroy($id);

        return response([
            'message' => 'Visit deleted successfully',
        ], 202);
    }
}
