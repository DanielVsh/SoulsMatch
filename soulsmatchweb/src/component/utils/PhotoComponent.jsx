import useS3Image from "../../hook/useS3Image";

export const PhotoComponent = ({photos, className}) => {

  const photoUrls = useS3Image(photos)

  return (
    <>
      {photoUrls.map(photoUrl => (
        <img src={photoUrl} alt={""} className={`${className}`}/>
      ))}
    </>
  )
}