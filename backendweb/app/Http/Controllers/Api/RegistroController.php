<?php /** @noinspection PhpUndefinedMethodInspection */

namespace App\Http\Controllers\Api;

use App\Departamento;
use App\Http\Controllers\Controller;
use App\Http\Requests\RegistroRequest;
use App\Http\Resources\RegistroResource;
use App\Persona;
use App\Registro;
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
     * @param RegistroRequest $request
     * @return Response
     */
    public function store(RegistroRequest $request)
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
            'registro' => new RegistroResource($registro),
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
        $dept = Departamento::find($departamento_id);

        $registros = $dept->registros()->get();
        return response([
            'message' => 'Retrieved Successfully',
            'registros' => RegistroResource::collection($registros),
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param RegistroRequest $request
     * @param  int  $id
     * @return Response
     */
    public function update(RegistroRequest $request, $id)
    {
        $registro = Registro::find($id);

        if ($registro != null) {
            $registro->update($request->only('parentesco', 'empresaEntrega'));

            return response([
                'message' => 'Updated Successfully',
                'registro' => new RegistroResource($registro),
            ], 202);
        }

        return response([
            'message' => 'Updated cancelled. It does not exist the entered register.'
        ], 404);

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
