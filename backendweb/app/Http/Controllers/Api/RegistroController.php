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
        ], 200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param RegistroRequest $request
     * @return Response
     */
    public function store(RegistroRequest $request)
    {

        if ($request->has(['rut', 'numDept'])) {
            error_log($request->getQueryString());
            // Registro doesn't include any of its relations, just the date, parentesco and empresaEntrega
            $dept = Departamento::where('numero', $request->query('numDept'))->first();
            $persona = Persona::where('rut', $request->query('rut'))->first();
        } else {
            // Searches the apartment based on the id received on Request.
            $dept = Departamento::find($request->get('departamento_id'));

            // Searches the apartment based on the id received on Request.
            $persona = Persona::find($request->get('persona_id'));
        }
        $registro = Registro::create([
            'fecha' => $request->input('fecha'),
            'parentesco' => $request->input('parentesco'),
            'empresaEntrega' => $request->input('empresaEntrega'),
            'persona_id' => $persona->id,
            'departamento_id' => $dept->id
        ]);

        // Creates the relation 1-n with the apartment.
        $registro->departamento()->associate($dept);
        // Creates the relation n-n with the people that visits the apartment.
        $registro->persona()->associate($persona);

        $registro->save();

        return response([
            'message' => "Visit registered successfully",
            'registro' => new RegistroResource($registro),
        ], 201);
    }

    /**
     * Obtains the visits to an specific apartment.
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
        ], 200);
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
