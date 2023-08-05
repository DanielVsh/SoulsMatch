import {Disclosure} from '@headlessui/react'
import {Bars3Icon, XMarkIcon} from '@heroicons/react/24/outline'
import {NavLink, useNavigate} from "react-router-dom";
import {logout} from "../feature/logout";

const navigation = [
  {name: 'Match', href: '/match'},
  {name: 'Profile', href: '/profile'},
  {name: 'Chats', href: '/chat'},
  {name: 'Likes', href: '/likes'},
]

function classNames(...classes) {
  return classes.filter(Boolean).join(' ')
}

export const NavBar = () => {

  const navigate = useNavigate()

  return (
    <div className={"w-full bg-white shadow-lg top"} style={{zIndex: 999}}>
      <Disclosure className="shadow-lg fixed top-0 w-full">
        {({open}) => (
          <>
            <div className="mx-0.5 px-2 sm:px-6 lg:px-8">
              <div className="relative flex h-16 items-center justify-between">
                <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
                  {/* Mobile menu button*/}
                  <Disclosure.Button
                    className="inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                    <span className="sr-only">Open main menu</span>
                    {open ? (
                      <XMarkIcon className="block h-6 w-6" aria-hidden="true"/>
                    ) : (
                      <Bars3Icon className="block h-6 w-6" aria-hidden="true"/>
                    )}
                  </Disclosure.Button>
                </div>
                <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
                  <div className="flex flex-shrink-0 items-center cursor-pointer"
                       onClick={() => navigate("/", {replace: true})}>
                    <img
                      className="block h-8 w-auto lg:hidden"
                      src="/hearts.svg"
                      alt="Your Company"
                    />
                    <img
                      className="hidden h-8 w-auto lg:block"
                      src="/hearts.svg"
                      alt="Your Company"
                    />
                  </div>
                  <div className="hidden sm:ml-6 sm:block">
                    <div className="flex space-x-4">
                      {navigation.map((item) => (
                        <div key={item.name}>
                          <NavLink to={item.href}
                                   className={props => classNames(
                                     props.isActive ? 'bg-gray-900 text-white' : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                                     'rounded-md px-3 py-2 text-sm font-medium'
                                   )}
                          >{item.name}</NavLink>
                        </div>
                      ))}
                      <div onClick={() => logout()}>Logout</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <Disclosure.Panel className="sm:hidden">
              <div className="space-y-1 px-2 pb-3 pt-2">
                {navigation.map((item) => (
                  <div key={item.name}>
                    <NavLink to={item.href}
                             className={props => classNames(
                               props.isActive ? 'bg-gray-900 text-white' : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                               'rounded-md px-3 py-2 text-sm font-medium'
                             )}

                    >{item.name}</NavLink>
                  </div>
                ))}
              </div>
            </Disclosure.Panel>
          </>
        )}
      </Disclosure>
    </div>
  )
}
