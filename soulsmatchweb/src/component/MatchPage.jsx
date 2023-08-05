import {useEffect, useState} from "react";
import {useDislikeProfileMutation, useGetNextProfileQuery, useLikeProfileMutation} from "../service/profileApi";
import {useDispatch, useSelector} from "react-redux";
import {clearProfileReducer, setProfileNumber} from "../service/profileSlice";
import {Gallery} from "./utils/Gallery";

export const MatchPage = () => {
  const dispatch = useDispatch()

  const profileNumberRedux = useSelector(state => state.profileReducer.match.profileNumber)

  const [profile, setProfile] = useState(null);
  const [profiles, setProfiles] = useState([]);

  const {data: getProfiles, isLoading, isFetching, refetch, isError} = useGetNextProfileQuery()

  const [dislikeProfile] = useDislikeProfileMutation()
  const [likeProfile] = useLikeProfileMutation()

  useEffect(() => {
    if (getProfiles) {
      setProfiles(getProfiles?.content)
    }
  }, [getProfiles])

  useEffect(() => {
    if (profiles) {
      setProfile(profiles[profileNumberRedux])
    }
  }, [profileNumberRedux, profiles])

  useEffect(() => {
    if (profileNumberRedux >= profiles?.length) {
      dispatch(clearProfileReducer());
      refetch()
    }
  }, [profileNumberRedux]);

  const handleDislike = async () => {
    await dislikeProfile(profile.id).then((response) => {
      dispatch(setProfileNumber(profileNumberRedux + 1))
    })
  }

  const handleLike = async () => {
    await likeProfile(profile.id).then((response) => {
      dispatch(setProfileNumber(profileNumberRedux + 1))
    })
  }

  if (isError) {
    return "Something went wrong..."
  }

  if (isLoading || !profile || isFetching) {
    return "Loading..."
  }

  return (
    <>

      <div>
        <div
          className="fixed left-0 w-screen h-screen bg-gradient-to-tr from-red-700 via-black to-transparent"
          style={{zIndex: -2}}
        />
        <div
          className="fixed right-0 w-screen h-screen bg-gradient-to-bl from-red-700 via-black to-transparent"
          style={{zIndex: -2}}
        />
      </div>

      <div className={`flex flex-1 flex-col justify-center`}>
        <div className={`sm:mx-auto sm:w-full sm:max-w-sm`}>
          <div className={`absolute left-1/3`}>
            <Gallery photos={profile?.photos}/>
            <div className={`absolute p-2 rounded-md bottom-20 ml-4`}>
              <p className={`text-center font-bold text-3xl capitalize text-white`}>
                {profile?.soul?.firstName} {profile?.soul?.age}
              </p>
              <p className={`text-white font-bold`}>
                {profile?.location?.name}
              </p>
            </div>
            <div className={`absolute bottom-10 flex justify-between space-x-9 lg:px-8`}>
              <div className={"bg-white"} onClick={handleLike}>Like button</div>
              <div className={"bg-white"} onClick={handleDislike}>Dislike button</div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}