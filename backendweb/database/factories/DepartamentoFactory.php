<?php

/** @var Factory $factory */

use App\Departamento;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(Departamento::class, function (Faker $faker) {

    return [
        'numero' => $faker->unique()->numberBetween(1,40),
    ];
});
