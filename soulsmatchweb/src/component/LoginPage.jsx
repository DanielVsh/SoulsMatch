import {useDispatch} from "react-redux";
import {useState} from "react";
import {setTokens} from "../service/authSlice";
import axios from "axios";
import {soulsmatch} from "../config/soulsmatch";
import {useNavigate} from "react-router-dom";

export const LoginPage = () => {

  const navigate = useNavigate()
  const dispatch = useDispatch()
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleAuthenticate = async (e, username, password) => {
    e.preventDefault();
    await axios.post(`${soulsmatch}/api/v1/auth/authenticate`, {
      username,
      password
    }).then(response => {
      dispatch(setTokens(response.data))
      setTimeout(() => {
        navigate("/match", {replace: true});
      }, 100);
    })
  }

  return (
    <>
      <div className="flex flex-1 flex-col justify-center px-6 py-6 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <img
            className="mx-auto h-8 w-auto cursor-pointer"
            src="/hearts.svg"
            alt="Your Company"
            onClick={() => navigate("/", {replace: true})}
          />
          <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
            Sign in to your account
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6">
            <div>
              <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900">
                Email address
              </label>
              <div className="mt-2">
                <input
                  id="email"
                  name="email"
                  type="email"
                  value={username}
                  onChange={(event) => setUsername(event.target.value)}
                  autoComplete="email"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Password
                </label>
                <div className="text-sm">
                  <a href="#" className="font-semibold text-red-600 hover:text-red-500">
                    Forgot password?
                  </a>
                </div>
              </div>
              <div className="mt-2">
                <input
                  id="password"
                  name="password"
                  type="password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-red-600"
                onClick={(e) => handleAuthenticate(e, username, password)}
              >
                Sign in
              </button>
            </div>

          </form>
          <div onClick={() => navigate("/registration", {replace: true})}
               className="flex justify-center pt-1 font-semibold text-red-600 hover:text-red-500 cursor-pointer w-max">
            Create an account
          </div>
        </div>
      </div>
    </>
  )
}