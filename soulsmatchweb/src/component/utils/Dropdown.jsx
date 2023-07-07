import React, {useState} from "react";

export const Dropdown = ({description, options, onSelect, disabled}) => {
  const [isOpen, setIsOpen] = useState(false);
  const [selectedOption, setSelectedOption] = useState("");

  const handleToggle = () => {
    if (!disabled) {
      setIsOpen(!isOpen);
    }
  };

  const handleSelectOption = (option) => {
    setSelectedOption(option);
    setIsOpen(false);
    onSelect(option);
  };

  return (
    <div className="relative">
      <button
        type="button"
        className={`flex items-center justify-between w-32 px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-red-600 focus:border-red-600 ${
          disabled ? "opacity-50 cursor-not-allowed" : ""
        }`}
        onClick={handleToggle}
        disabled={disabled}
      >
        {selectedOption ? (
          <span>{options.find((opt) => opt === selectedOption)}</span>
        ) : (
          <span>{description}</span>
        )}
        <svg
          className={`w-5 h-5 ml-2 transition-transform ${
            isOpen ? "transform rotate-180" : ""
          }`}
          fill="currentColor"
          viewBox="0 0 20 20"
          aria-hidden="true"
        >
          <path
            fillRule="evenodd"
            d="M6.293 6.707a1 1 0 010-1.414l3-3a1 1 0 011.414 0l3 3a1 1 0 11-1.414 1.414L10 4.414l-2.293 2.293a1 1 0 01-1.414 0z"
            clipRule="evenodd"
          />
        </svg>
      </button>
      {isOpen && (
        <div
          className="absolute z-10 w-32 mt-2 bg-white border border-gray-300 divide-y divide-gray-200 rounded-md shadow-lg">
          {options.map((option) => (
            <button
              key={option}
              type="button"
              className="block w-full px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 focus:bg-gray-100 focus:outline-none"
              onClick={() => handleSelectOption(option)}
              disabled={disabled}
            >
              {option}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};
