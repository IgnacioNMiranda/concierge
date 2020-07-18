<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePersonaRegistroTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('persona_registro', function (Blueprint $table) {
            $table->id();

            $table->enum('parentesco',['Familiar','Externo', 'Empresa'])->default('Externo');
            $table->boolean('empresaEntrega')->nullable();

            $table->bigInteger('persona_id')->unsigned();
            $table->bigInteger('registro_id')->unsigned();

            $table->foreign('persona_id')->references('id')->on('personas')
                ->onDelete('cascade')
                ->onUpdate('cascade');

            $table->foreign('registro_id')->references('id')->on('registros')
                ->onDelete('cascade')
                ->onUpdate('cascade');

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('persona_registro');
    }
}
