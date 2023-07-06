import {useEffect, useState} from "react";
import {useDislikeProfileMutation, useGetNextProfileQuery, useLikeProfileMutation} from "../service/profileApi";
import {useDispatch, useSelector} from "react-redux";
import {clearProfileReducer, setProfileNumber} from "../service/profileSlice";

export const MatchPage = () => {
  const dispatch = useDispatch()

  const profileNumberRedux = useSelector(state => state.profileReducer.match.profileNumber)

  const [profile, setProfile] = useState(null);

  const {data: getProfiles, isLoading, isFetching, refetch} = useGetNextProfileQuery()
  const [dislikeProfile] = useDislikeProfileMutation()
  const [likeProfile] = useLikeProfileMutation()

  useEffect(() => {
    if (getProfiles) {
      setProfile(getProfiles[profileNumberRedux])
    }
  }, [getProfiles, profileNumberRedux])

  useEffect(() => {
    if (profileNumberRedux >= 10) {
      dispatch(clearProfileReducer());
      refetch()
    }
  }, [profileNumberRedux, dispatch, getProfiles, refetch]);

  const handleDislike = (id) => {
    dislikeProfile(id).then(() => {
      dispatch(setProfileNumber(profileNumberRedux + 1))
    })
  }

  const handleLike = (id) => {
    likeProfile(id).then(() => {
      dispatch(setProfileNumber(profileNumberRedux + 1))
    })
  }

  if (isLoading || !profile || isFetching) {
    return "Loading..."
  }

  return (
    <>
      <div>
        <img src={"https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fimages6.fanpop.com%2Fimage%2Fphotos%2F33200000%2FSpongebob-spongebob-squarepants-33210742-2254-2451.jpg&f=1&nofb=1&ipt=8f28f26b5c167a258b631044c52b21025ea304f42a2f8c22f2f27c1ffb89b2a9&ipo=images"}
             alt={"acc"}
             style={{width: "300px", height: "300px"}}/>
        {profile?.soul?.firstName} {profile?.soul?.lastName}
      </div>
      <div>
        {profile?.description}
      </div>
      {profile?.soul?.location?.city?.name} {profile?.soul?.location?.country?.name}
      <button onClick={() => handleLike(profile?.id)}>Like</button>
      <button onClick={() => handleDislike(profile?.id)}>Dislike</button>
      {profileNumberRedux}
    </>
  )
}