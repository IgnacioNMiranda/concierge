<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\User\UserLoginRequest;
use App\Http\Requests\User\UserRegisterRequest;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Http\Response;

/**
 * Class AuthController
 * @package App\Http\Controllers\Api
 */
class AuthController extends Controller
{
    /**
     * Register a user into the system.
     * @param UserRegisterRequest $request
     * @return Response
     */
    public function register(UserRegisterRequest $request)
    {
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
     * @param UserLoginRequest $request
     * @return Response
     */
    public function login(UserLoginRequest $request) {
        $data = $request->all();

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
     * @return Response
     */
    public function logout(Request $request)
    {
        $request->user()->token()->revoke();

        return response([
            'message' => 'Successfully logged out'
        ]);
    }

}
