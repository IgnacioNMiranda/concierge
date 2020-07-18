<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Persona extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'rut', 'nombre', 'telefono' ,'email',
    ];

    /**
     * n-n database relation with Registro.
     * @return BelongsToMany
     */
    public function registers()
    {
        return $this->belongsToMany('App\Registro');
    }

    /**
     * 1-n database relation with Departamento.
     * @return BelongsTo
     */
    public function apartment()
    {
        return $this->belongsTo('App\Departamento');
    }
}
