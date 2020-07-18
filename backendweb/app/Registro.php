<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

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

    /**
     * 1-n database relation with Persona.
     * @return BelongsToMany
     */
    public function people()
    {
        return $this->belongsToMany('App\Persona');
    }
}
