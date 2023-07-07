import {useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {setTokens} from "../service/authSlice";
import axios from "axios";
import {soulsmatch} from "../config/soulsmatch";
import {Dropdown} from "./utils/Dropdown";
import {useNavigate} from "react-router-dom";
import {Stepper} from "./utils/Stepper";

export const RegistrationPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [birthYear, setBirthYear] = useState('');
  const [birthMonth, setBirthMonth] = useState('');
  const [birthDay, setBirthDay] = useState('');
  const [gender, setGender] = useState('');

  const [step, setStep] = useState(0);
  const [error, setError] = useState('');

  const navigate = useNavigate()
  const dispatch = useDispatch()

  const genders = [
    {value: "MALE", label: "Male"},
    {value: "FEMALE", label: "Female"},
  ];

  let birthDate;
  const handleRegistration = async (e, data = {
    username,
    password,
    firstName,
    lastName,
    birthDate,
    gender
  }) => {
    e.preventDefault()
    await axios.post(`${soulsmatch}/api/v1/auth/register`, data).then(response => {
      dispatch(setTokens(response?.data))
      setTimeout(() => {
        navigate("/profile/create", {replace: true});
      }, 100);
    })
  }

  const getYears = (startYear, endYear) => {
    const years = [];
    for (let year = startYear; year <= endYear; year++) {
      years.push(year);
    }
    return years.sort().reverse();
  };

  const getMonths = () => {
    const months = [];
    for (let month = 1; month <= 12; month++) {
      months.push(month.toString().padStart(2, "0"));
    }
    return months;
  };

  const getDays = (year, month) => {
    const daysInMonth = new Date(year, month, 0).getDate();
    const days = [];
    for (let day = 1; day <= daysInMonth; day++) {
      days.push(day.toString().padStart(2, "0"));
    }
    return days;
  };

  const getErrorMessage = (
    error && (
      <div className="fixed top-4 right-4 m-4 p-2 bg-red-600 text-white rounded shadow">{error}</div>
    ))

  const handleNextStep = (event) => {
    event.preventDefault()
    if (step === 0 && (
      !username ||
      !password ||
      !/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(username) ||
      !/(?=.*\d)(?=.*[A-Z]).{8,}/.test(password))) {
      setError('Invalid email or password');
      return;
    }
    if (step === 1 && (!firstName || !lastName)) {
      setError('First name and last name are required');
      return;
    }
    if (step === 2 && (!birthYear || !birthMonth || !birthDay)) {
      setError('Please select a valid birth date');
      return;
    }
    if (step === 3 && (!gender)) {
      setError('Please select a gender');
      return;
    }
    setError('')
    setStep(step + 1)
  }

  useEffect(() => {
    setBirthDay('')
  }, [birthYear, birthMonth])

  return (
    <>
      {getErrorMessage}
      <div className="flex flex-1 flex-col justify-center px-6 py-6 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <img
            className="mx-auto h-8 w-auto cursor-pointer"
            src="/hearts.svg"
            alt="Your Company"
            onClick={() => navigate("/", {replace: true})}
          />
          <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
            Create new account
          </h2>
        </div>
        <Stepper step={step} totalSteps={4}/>
        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6">
            {step === 0 && (
              <>
                <div>
                  <label htmlFor="username" className="block text-sm font-medium leading-6 text-gray-900">
                    Email address <span className="text-gray-400">(Required)</span>
                  </label>
                  <div className="mt-2">
                    <input
                      id="username"
                      name="username"
                      type="email"
                      value={username}
                      onChange={(event) => setUsername(event.target.value)}
                      autoComplete="username"
                      required
                      pattern={"^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"}
                      className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                    />
                  </div>
                </div>

                <div>
                  <div className="flex items-center justify-between">
                    <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                      Password <span className="text-gray-400">(Required)</span>
                    </label>
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
                      pattern={"(?=.*d)(?=.*[A-Z]).{8,}"}
                      aria-describedby="password-hint"
                      className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                    />
                    <div id="password-hint" className="text-sm text-gray-500">
                      Password must contain at least one digit, one uppercase letter, and be at least 8 characters long.
                    </div>
                  </div>
                </div>

                <div>
                  <button
                    className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                    onClick={(event) => handleNextStep(event)}
                  >
                    Next
                  </button>
                </div>
              </>
            )}
            {step === 1 && (
              <>
                <div>
                  <label htmlFor="firstName" className="block text-sm font-medium leading-6 text-gray-900">
                    First Name <span className="text-gray-400">(Required)</span>
                  </label>
                  <div className="mt-2">
                    <input
                      id="firstName"
                      name="firstName"
                      type="text"
                      value={firstName}
                      onChange={(event) => setFirstName(event.target.value)}
                      autoComplete="firstName"
                      required
                      className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                    />
                  </div>
                </div>

                <div>
                  <label htmlFor="lastName" className="block text-sm font-medium leading-6 text-gray-900">
                    Last Name <span className="text-gray-400">(Required)</span>
                  </label>
                  <div className="mt-2">
                    <input
                      id="lastName"
                      name="lastName"
                      type="text"
                      value={lastName}
                      onChange={(event) => setLastName(event.target.value)}
                      autoComplete="lastName"
                      required
                      className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                    />
                  </div>
                </div>

                <div className="flex justify-between space-x-4">
                  <div className="flex-grow">
                    <button
                      className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                      onClick={() => setStep(step - 1)}
                    >
                      Back
                    </button>
                  </div>
                  <div className="flex-grow">
                    <button
                      className="flex w-full justify-center  rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                      onClick={(event) => handleNextStep(event)}
                    >
                      Next
                    </button>
                  </div>
                </div>
              </>
            )}
            {step === 2 && (
              <>
                <span
                  className={`text-3xl font-bold text-gray-900`}>Date of Birth: {birthDay ? birthDay : "dd"} {birthMonth ? birthMonth : "MM"} {birthYear ? birthYear : "yyyy"}</span>
                <div className="flex justify-center space-x-4">
                  <Dropdown description={"Year"}
                            options={getYears(new Date().getFullYear() - 18 - 70, new Date().getFullYear() - 18)}
                            onSelect={setBirthYear}
                            disabled={false}/>
                  <Dropdown description={"Month"}
                            options={getMonths()}
                            onSelect={setBirthMonth}
                            disabled={false}/>
                  <Dropdown description={"Day"}
                            options={getDays(birthYear, birthMonth)}
                            onSelect={setBirthDay}
                            disabled={!birthYear || !birthMonth}/>
                </div>

                <div className="flex justify-between space-x-4">
                  <div className="flex-grow">
                    <button
                      className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                      onClick={() => setStep(step - 1)}
                    >
                      Back
                    </button>
                  </div>
                  <div className="flex-grow">
                    <button
                      className="flex w-full justify-center  rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                      onClick={(event) => handleNextStep(event)}
                    >
                      Next
                    </button>
                  </div>
                </div>
              </>
            )}
            {step === 3 && (
              <>
                <div className="flex justify-between space-x-4">
                  {genders.map(g => (
                    <div key={g.value}
                         className={`cursor-pointer px-4 py-1.5 text-sm font-bold rounded-md border border-solid border-gray-500 hover:shadow-md ${g.value === gender && `ring-2 border-red-600 ring-red-600`}`}
                         onClick={() => setGender(g.value)}
                    >
                      {g.label}
                    </div>
                  ))}
                </div>

                <div className="flex justify-between space-x-4">
                  <div className="flex-grow">
                    <button
                      className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                      onClick={() => setStep(step - 1)}
                    >
                      Back
                    </button>
                  </div>
                  <div className="flex-grow">
                    <button
                      className="flex w-full justify-center  rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                      onClick={(event) => handleNextStep(event)}
                    >
                      Next
                    </button>
                  </div>
                </div>
              </>
            )}
            {step === 4 && (
              <div className="flex justify-between space-x-4">
                <div className="flex-grow">
                  <button
                    className="flex w-full justify-center rounded-md bg-black px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                    onClick={() => setStep(step - 1)}
                  >
                    Back
                  </button>
                </div>
                <div className="flex-grow">
                  <button
                    type="submit"
                    className="flex w-full justify-center rounded-md bg-red-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-600 hover:shadow-2xl transition hover:shadow-red-700  focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                    onClick={(e) => handleRegistration(e, {
                      username,
                      password,
                      firstName,
                      lastName,
                      birthDate: `${birthDay}-${birthMonth}-${birthYear}`,
                      gender
                    })}
                  >
                    Register
                  </button>
                </div>
              </div>
            )}
          </form>
        </div>
      </div>
    </>
  )
}