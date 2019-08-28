# smartshopper
Smartshopper application with docker

Local:
docker push raveendrabikkina/smartshopper:version1.0

docker build -t raveendrabikkina/smartshopper .

docker run -p 8080:8080 raveendrabikkina/smartshopper

Open new terminal and run

docker ps

docker exec -it <containerid> sh
