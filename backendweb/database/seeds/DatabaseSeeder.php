<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        // $this->call(UserSeeder::class);
        $this->call(DepartamentosTableSeeder::class);
        $this->call(PersonasTableSeeder::class);
        $this->call(RegistrosTableSeeder::class);
    }
}
