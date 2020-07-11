<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Persona extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'rut', 'name', 'phone' ,'email',
    ];
}
