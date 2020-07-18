<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Registro extends Model
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'fecha', 'departamento_id'
    ];

    /**
     * n-n database relation with Persona.
     * @return BelongsToMany
     */
    public function personas()
    {
        return $this->belongsToMany('App\Persona')->withTimestamps();
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
