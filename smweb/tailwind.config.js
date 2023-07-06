/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      boxShadow: {
        transition: '0.3s ease-in-out',
      },
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
}

