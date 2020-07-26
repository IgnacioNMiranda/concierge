<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Registro;
use Faker\Generator as Faker;

$factory->define(Registro::class, function (Faker $faker) {

    $parentescos = ['Familiar', 'Externo'];

    return [
        'fecha' => $faker->date(),
        'parentesco' => $parentescos[rand(0,1)],
        'empresaEntrega' => $faker->text,
        'persona_id' => $faker->unique()->numberBetween(1, 20),
        'departamento_id' => $faker->unique()->numberBetween(1,40),
    ];
});
