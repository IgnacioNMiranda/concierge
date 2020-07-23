<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\hasMany;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Persona extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'rut', 'nombre', 'telefono', 'email', 'departamento_id'
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
     * 1-n database relation with Registro.
     * @return hasMany
     */
    public function registros()
    {
        return $this->hasMany('App\Registro');
    }

    /**
     * 1-n database relation with DepartamentoResource.
     * @return BelongsTo
     */
    public function departamento()
    {
        return $this->belongsTo('App\Departamento');
    }
}
