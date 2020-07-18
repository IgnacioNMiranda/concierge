<?php /** @noinspection PhpUndefinedMethodInspection */

namespace App\Http\Controllers\Api;

use App\Departamento;
use App\Http\Controllers\Controller;
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
        // TODO: Missing implementation (http://conserjeria.cl/api/registro).
        $registers = Registro::orderBy('fecha', 'ASC')->get();

        //$apartment = $register->departamento()->get();

        return response([
            'registros' =>  $registers,
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
        $data = $request->all();

        $register = Registro::create($data);

        // Searches the apartment based on the id received on Request.
        $apartment = Departamento::find($request->get('departamento_id'));

        // Creates the relation 1-n with the apartment.
        $register->departamento()->associate($apartment);
        $register->save();

        // Creates the relation n-n with the people that visits the apartment.
        $register->personas()->attach($request->get('personas'));

        return response([
            'message' => "Visit registered successfully",
            'register' => $register,
        ]);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function show($id)
    {
        // TODO (http://conserjeria.cl/api/registro/{numerodecasa/departamento}).
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
        // TODO
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function destroy($id)
    {
        // TODO
    }
}
