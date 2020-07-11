<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\UserStoreRequest;

use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

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

        /** @noinspection PhpUndefinedMethodInspection */
        $user = User::create($data);

        $authToken = $user->createToken('authToken');

        return response([
            'user' => $user,
            'token' => $authToken->accessToken,
            'token_expires_at' => $authToken->token->expires_at,
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
            'email' => 'email:rfc,dns|min:8|max:255|required',
            'password' => 'required',
        ]);

        if ($validator->fails()) {
            return response([
                'error' => 'Validation error',
                'message' => $validator->errors(),
            ], 412);
        }

        if (!auth()->attempt($data)) {
            return response([
                'error' => 'Invalid credentials',
                'message' => 'Unauthorized, wrong email or password',
            ], 401);
        }

        /** @noinspection PhpUndefinedMethodInspection */
        $authToken = auth()->user()->createToken('authToken');

        return response([
            'user' => auth()->user(),
            'token' =>$authToken->accessToken,
            'token_expires_at' => $authToken->token->expires_at,
        ]);
    }

    /**
     * Logout user (revoke token).
     * @param Request $request
     * @return \Illuminate\Http\Response
     */
    public function logout(Request $request)
    {
        $request->user()->token()->revoke();

        return response([
            'message' => 'Successfully logged out'
        ]);
    }

}
