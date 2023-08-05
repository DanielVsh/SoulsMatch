import {useState} from "react";
import {Stepper} from "./Stepper";
import useS3Image from "../../hook/useS3Image";

export const Gallery = ({photos}) => {
  const [currentPhotoIndex, setCurrentPhotoIndex] = useState(0);

  const photoData = useS3Image(photos)

  const handlePrevPhoto = () => {
    setCurrentPhotoIndex((prevIndex) =>
      prevIndex === 0 ? photos.length - 1 : prevIndex - 1
    );
  };

  const handleNextPhoto = () => {
    setCurrentPhotoIndex((prevIndex) =>
      prevIndex === photos.length - 1 ? 0 : prevIndex + 1
    );
  };

  const handlePhotoClick = (event) => {
    const halfScreenWidth = window.innerWidth / 2;
    const clickPositionX = event.clientX;
    if (clickPositionX > halfScreenWidth) {
      handleNextPhoto();
    } else {
      handlePrevPhoto();
    }
  };

  return (
    <>
      <div
        className="flex flex-col items-center justify-center"
        onClick={handlePhotoClick}
      >
        <div className="flex flex-col items-center justify-center">
          {photoData?.length === 0 ? (
            <img src="/user_img.png" alt="" className="max-w-full"/>
          ) : (
            <div className="relative">
              <img
                src={photoData[currentPhotoIndex]}
                alt=""
                className="max-h-screen max-w-sm min-w-max  shadow-lg"
              />
              <div className="absolute top-5 left-1/2 transform -translate-x-1/2">
                <Stepper step={currentPhotoIndex + 1} totalSteps={photoData?.length}/>
              </div>
              <div
                className="absolute bottom-0 left-0 w-full h-1/2 bg-gradient-to-b from-transparent to-black opacity-90 pointer-events-none"></div>
              <div
                className="absolute bottom-0 right-0 w-full h-1/2 bg-gradient-to-b from-transparent to-black opacity-90 pointer-events-none"></div>
            </div>
          )}
        </div>
      </div>
    </>
  );
};
