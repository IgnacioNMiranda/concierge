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
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'updated_at', 'created_at', 'id'
    ];

    /**
     * 1-n database relation with Persona.
     * @return HasMany
     */
    public function personas()
    {
        return $this->hasMany(Persona::class);
    }

    /**
     * 1-n database relation with Registro.
     * Registro model has the foreign key linked to Departamento model id's.
     * @return HasMany
     */
    public function registros()
    {
        return $this->hasMany('App\Registro');
    }
}
