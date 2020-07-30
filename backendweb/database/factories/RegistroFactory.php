<?php

/** @var Factory $factory */

use App\Registro;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(Registro::class, function (Faker $faker) {

    $parentescos = ['Familiar', 'Externo', 'Empresa'];
    $parentesco = $parentescos[rand(0,2)];
    if ($parentesco != "Empresa") {
        $empresaEntrega = false;
    } else {
        $empresaEntrega = $faker->boolean;
    }

    return [
        'fecha' => $faker->date(),
        'parentesco' => $parentesco,
        'empresaEntrega' => $empresaEntrega,
        'persona_id' => rand(1, 20),
        'departamento_id' => rand(1,40),
    ];
});
