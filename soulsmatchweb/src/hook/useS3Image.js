import {useEffect, useState} from 'react';
import {s3} from "../feature/s3";

const useS3Image = (photos) => {
  const [photoUrls, setPhotoUrls] = useState([]);

  useEffect(() => {

    const getPhotosFromS3 = async () => {
      try {
        const fetchPhoto = async (photo) => {
          const data = await s3().getObject({Bucket: photo?.bucket, Key: photo?.key}).promise();
          return data?.Body ? URL.createObjectURL(new Blob([data.Body])) : '';
        };

        const promises = photos.map(fetchPhoto);
        const photoUrls = await Promise.all(promises);
        setPhotoUrls(photoUrls);
      } catch (error) {
        console.error('Error fetching photos from S3:', error);
      }
    };

    getPhotosFromS3();
  }, [photos]);

  return photoUrls;
};

export default useS3Image;
