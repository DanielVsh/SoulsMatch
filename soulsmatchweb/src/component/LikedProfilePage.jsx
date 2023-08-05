import React, {useEffect, useState} from "react";
import {useGetLikedProfilesQuery} from "../service/profileApi";
import {PhotoComponent} from "./utils/PhotoComponent";

export const LikedProfilePage = () => {
  const [profiles, setProfiles] = useState([])


  const {data: likedProfilePageable, isLoading} = useGetLikedProfilesQuery()

  useEffect(() => {
    if (likedProfilePageable) {
      setProfiles(likedProfilePageable?.content)
    }
  }, [likedProfilePageable]);

  if (isLoading) {
    return "Loading..."
  }

  return (
    <>
      <div className="flex flex-row overflow-x-auto">
        {profiles.map(profile => (
          <div key={profile.id} className="bg-red-700 shadow-lg rounded-lg m-2 flex-shrink-0 hover:bg-red-600">
            <div className="p-4">
              <div className="rounded-full w-32 h-32 overflow-hidden mx-auto">
                <PhotoComponent photos={[profile?.photos[0]]} className="w-full h-full object-cover" />
              </div>
              <p className="mt-2 text-center font-bold text-xl">
                {profile?.soul?.firstName} {profile?.soul?.age}
              </p>
            </div>
          </div>
        ))}
      </div>
    </>
  )
}