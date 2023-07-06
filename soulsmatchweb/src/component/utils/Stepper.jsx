import React from "react";

export const Stepper = ({step, totalSteps}) => {
  const stepsArray = Array.from({ length: totalSteps });
  return (
    <div className="flex flex-col items-center p-1">
      <div className="flex items-center space-x-2 mb-4">
        {stepsArray.map((_, s) => (
          <div key={s} className={`w-3 h-3 rounded-full ${step >= s + 1 ? 'bg-red-600' : 'bg-gray-300'}`}></div>
        ))}
      </div>
    </div>
  );
}

