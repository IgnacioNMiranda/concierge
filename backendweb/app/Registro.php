<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Registro extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'fecha'
    ];

    public function roles()
    {
        return $this->belongsToMany('App\Persona');
    }
}
