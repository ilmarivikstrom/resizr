# resizr_slave
Add REDIS_SERVER_ADDRESS to your environment variables.
# add redis
docker build -f Dockerfile -t resizr_slave .
docker run --name resizr_slave --link redis-for-resizr:redis -p 8090:8090 --env="REDIS_SERVER_ADDRESS=redis" -d resizr_slave

#pushing the docker image to Google Cloud Container registry 
docker tag resizr eu.gcr.io/woven-woodland-218112/resizr_slave
docker push resizr eu.gcr.io/woven-woodland-218112/resizr_slave
#creating a Compute Engine (Google Cloud VM) using the published docker container
gcloud compute instances create-with-container resizr_slave_1 --container-image eu.gcr.io/woven-woodland-218112/resizr_slave