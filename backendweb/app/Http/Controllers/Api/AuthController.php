<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\UserStoreRequest;

use App\User;
use Illuminate\Http\Request;

/**
 * Class AuthController
 * @package App\Http\Controllers\Api
 */
class AuthController extends Controller
{
    /**
     * Register a user into the system.
     * @param UserStoreRequest $request
     * @return \Illuminate\Http\Response
     */
    public function register(UserStoreRequest $request)
    {
        /*$validator = new EmailValidator();
        $result = $validator->isValid($request->email, new RFCValidation());
        if(!$result){
            return back()->withErrors(['Formato de email inválido.']);
        }*/

        $data = $request->all();

        $data['password'] = bcrypt($request->password);

        $user = User::create($data);

        $accessToken = $user->createToken('authToken')->accessToken;

        return response([
            'user' => $user,
            'accessToken' => $accessToken,
        ]);
    }

    /**
     * Do the login, returning the access token.
     * @param Request $request
     * @return \Illuminate\Http\Response
     */
    public function login(Request $request) {
        $data = $request->all();

        $validator = Validator::make($data, [
            'email' => 'email|required',
            'password' => 'required',
        ]);

        if ($validator->fails()) {
            return response([
                'error' => $validator->error(),
                'message' => 'validation error',
            ]);
        }

        if (!auth()->attempt($data)) {
            return response([
                'message' => 'Invalid credentials',
            ]);
        }

        $accessToken = auth()->user()->createToken('authToken')->accessToken;

        return response([
            'user' => auth()->user(),
            'access_token' =>$accessToken,
        ]);
    }

}
