import AWS from "aws-sdk";
import {awsAccessKeyId, awsSecretAccessKey} from "../config/soulsmatch";

export const s3 = () => {
  AWS.config.update({
    accessKeyId: awsAccessKeyId,
    secretAccessKey: awsSecretAccessKey,
  })

  AWS.config.region = 'eu-north-1';

  return new AWS.S3()
}
