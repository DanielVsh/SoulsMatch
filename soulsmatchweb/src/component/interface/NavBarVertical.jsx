import React from "react";
import {NavLink} from "react-router-dom";
import {logout} from "../../feature/logout";

const navigation = [
	{name: 'Match', href: '/match'},
	{name: 'Profile', href: '/profile'},
	{name: 'Chats', href: '/chat'},
	{name: 'Likes', href: '/like'},
]

function classNames(...classes) {
	return classes.filter(Boolean).join(' ')
}

const NavBarVertical = () => {



	return (
		<nav className=" flex top-0 left-0 flex-col h-screen w-44 bg-black bg-opacity-40 text-white"
		style={{zIndex: 999}}>

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

		<div onClick={() => logout()}>
			Logout
		</div>
		</nav>
	);
};

export default NavBarVertical;
