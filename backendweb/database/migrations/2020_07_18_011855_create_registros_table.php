<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateRegistrosTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('registros', function (Blueprint $table) {
            $table->id();

            $table->date('fecha');
            $table->enum('parentesco',['Familiar','Externo', 'Empresa'])->default('Externo');
            $table->boolean('empresaEntrega')->nullable();

            $table->unsignedBigInteger('persona_id');
            $table->unsignedBigInteger('departamento_id');

            $table->foreign('persona_id')->references('id')->on('personas')
                ->onDelete('cascade')
                ->onUpdate('cascade');

            $table->foreign('departamento_id')->references('id')->on('departamentos')
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
        Schema::dropIfExists('registros');
    }
}
