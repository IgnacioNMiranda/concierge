<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Departamento extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'numero'
    ];

    /**
     * 1-n database relation with Persona.
     * @return HasMany
     */
    public function people()
    {
        return $this->hasMany(Persona::class);
    }
}
