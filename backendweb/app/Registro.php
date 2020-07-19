<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Registro extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'fecha', 'parentesco', 'empresaEntrega', 'persona_id', 'departamento_id'
    ];

    /**
     * 1-n database relation with Persona.
     * @return BelongsTo
     */
    public function personas()
    {
        return $this->belongsTo('App\Persona');
    }

    /**
     * 1-n database relation with Departamento.
     * Registro model has the foreign key linked to Departamento model id's.
     * @return BelongsTo
     */
    public function departamento()
    {
        return $this->belongsTo('App\Departamento');
    }
}
