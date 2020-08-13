<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

// Auth routes.
Route::post('/register', 'Api\AuthController@register');
Route::post('/login', 'Api\AuthController@login');
Route::get('/logout', 'Api\AuthController@logout')->middleware('auth:api');

// Api routes.
Route::middleware(['auth:api'])->group(function () {
    Route::apiResources([
        'persona' => 'Api\PersonaController',
        'registro' => 'Api\RegistroController',
        'departamento' => 'Api\DepartamentoController'
    ]);
    Route::get('/persona/buscarPersonaPorRut/{rut}', 'Api\PersonaController@buscarPersonaPorRut');
});

// When it wants to test http request use this to avoid the middleware and comment the Route:middleware above.
/*
Route::apiResources([
    'persona' => 'Api\PersonaController',
    'registro' => 'Api\RegistroController',
    'departamento' => 'Api\DepartamentoController'
]);
*/
