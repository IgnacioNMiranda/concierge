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
        'rut', 'nombre', 'telefono' ,'email', 'departamento_id'
    ];

    /**
     * n-n database relation with Registro.
     * @return BelongsToMany
     */
    public function registros()
    {
        return $this->belongsToMany('App\Registro')->withTimestamps();
    }

    /**
     * 1-n database relation with Departamento.
     * @return BelongsTo
     */
    public function departamento()
    {
        return $this->belongsTo('App\Departamento');
    }
}
