// import React, {useState} from "react";
// import {useCreateProfileMutation} from "../service/profileApi";
//
// export const CreateProfilePage = () => {
//
//   const [description, setDescription] = useState('');
//   const [photos, setPhotos] = useState(['']);
//   const [preferredGenders, setPreferredGenders] = useState(['']);
//
//   const [createProfile] = useCreateProfileMutation()
//
//   const genders = ['MALE', 'FEMALE'];
//
//   const handleCreateProfile = async (data = {
//     description, photos, preferredGenders
//   }) => {
//     await createProfile(data)
//   }
//
//   const handleGenderSelect = (event) => {
//     const selectedGender = event.target.value;
//     if (!preferredGenders.includes(selectedGender)) {
//       setPreferredGenders([...preferredGenders, selectedGender]);
//     }
//   };
//
//   const removeGender = (gender) => {
//     setPreferredGenders(preferredGenders.filter((selectedGender) => selectedGender !== gender));
//   };
//
//   return (
//     <>
//       Create profile page
//       <form>
//         <input value={description} onChange={(event) => setDescription(event.target.value)}/>
//         <div>
//           <label>Select Genders:</label>
//           <select onChange={handleGenderSelect}>
//             <option value="">Select</option>
//             {genders.map((gender) => (
//               <option key={gender} value={gender}>
//                 {gender}
//               </option>
//             ))}
//           </select>
//           <div>
//             Selected Genders: {preferredGenders.map((gender) => <span onClick={() => removeGender(gender)}
//                                                                       key={gender}>{gender}, </span>)}
//           </div>
//         </div>
//         <input value={photos} onChange={(event) => setPhotos(event.target.value)}/>
//       </form>
//       <button onClick={() => handleCreateProfile({photos, description, preferredGenders})}>gogogo</button>
//     </>
//   )
// }