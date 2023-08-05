import {useState} from "react";
import {useCreateProfileMutation} from "../service/profileApi";
import {City} from "country-state-city";
import {useNavigate} from "react-router-dom";

export const CreateProfilePage = () => {
  const navigate = useNavigate()

  const [description, setDescription] = useState('');
  const [photos, setPhotos] = useState([]);
  const [preferredGenders, setPreferredGenders] = useState(['']);

  const [country, setCountry] = useState(null);
  const [city, setCity] = useState([]);

  const [profile, setProfile] = useState(null);

  const [createProfile] = useCreateProfileMutation()

  const genders = ['MALE', 'FEMALE'];

  const handleCreateProfile = async (event) => {
    event.preventDefault()
    const citiesOfCountryElement = City.getCitiesOfCountry("UA")[25];
    citiesOfCountryElement && setCity(citiesOfCountryElement)
    if (!photos || !city || !preferredGenders || !description) {
      throw new Error("Error");
    }
    const formData = new FormData();
    photos.forEach((file, index) => {
      formData.append(`photos[${index}].photo`, file)
      formData.append(`photos[${index}].action`, `CREATE`)
    });

    formData.append("preferredGenders", preferredGenders);
    formData.append("description", description);


    formData.append("location.name", city.name);
    formData.append("location.latitude", parseFloat(city.latitude));
    formData.append("location.longitude", parseFloat(city.longitude));
    await createProfile(formData).then(() => navigate("/match", {replace: true}))
  }

  const handleGenderSelect = (event) => {
    const selectedGender = event.target.value;
    if (!preferredGenders.includes(selectedGender)) {
      setPreferredGenders([...preferredGenders, selectedGender]);
    }
  };

  const removeGender = (gender) => {
    setPreferredGenders(preferredGenders.filter((selectedGender) => selectedGender !== gender));
  };

  const renderImagePreviews = () => {
    return photos.map((file, index) => (
      <div className={``}>
        <img
          key={index}
          src={URL.createObjectURL(file)}
          alt={`Preview ${index}`}
          style={{width: '100px', height: '100px', objectFit: 'cover', margin: '5px'}}
        />
      </div>
    ));
  };

  const handleFileChange = (event) => {
    const selectedFiles = event.target.files;
    const fileArray = Array.from(selectedFiles);

    setPhotos(fileArray);
  };


  return (
    <>
      Create profile page
      <form>
        <input value={description} onChange={(event) => setDescription(event.target.value)}/>
        <div>
          <label>Select Genders:</label>
          <select onChange={handleGenderSelect}>
            <option value="">Select</option>
            {genders.map((gender) => (
              <option key={gender} value={gender}>
                {gender}
              </option>
            ))}
          </select>
          <div>
            Selected Genders: {preferredGenders.map((gender) => <span onClick={() => removeGender(gender)}
                                                                      key={gender}>{gender}, </span>)}
          </div>
        </div>
        {renderImagePreviews()}
        <input type="file"
               accept="image/*"
               multiple
               onChange={handleFileChange}/>
      </form>
      <button onClick={handleCreateProfile}>gogogo</button>
    </>
  )
}