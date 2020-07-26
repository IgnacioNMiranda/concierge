<?php

/** @var \Illuminate\Database\Eloquent\Factory $factory */

use App\Persona;
use Faker\Generator as Faker;

$factory->define(Persona::class, function (Faker $faker) {


    return [
        'rut' => $faker->unique()->regexify('[1-9]{1,2}\.[0-9]{3}\.[0-9]{3}-([0-9]|k)'),
        'nombre' => $faker->name,
        'telefono' => rand(00000000,99999999),
        'email' => $faker->unique()->safeEmail,
        'departamento_id' => rand(1, 40),
    ];
});
