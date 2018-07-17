docker build -t gcr.io/IMAGE_PATH/multichain-master ./multichain-cluster/master
docker push gcr.io/IMAGE_PATH/multichain-master

docker build -t gcr.io/IMAGE_PATH/multichain-slave ./multichain-cluster/node
docker push gcr.io/IMAGE_PATH/multichain-slave

docker build -t gcr.io/IMAGE_PATH/multichain-explorer ./multichain-cluster/explorer
docker push gcr.io/IMAGE_PATH/multichain-explorer

docker build -t gcr.io/IMAGE_PATH/multichain-server ./multichain-server
docker push gcr.io/IMAGE_PATH/multichain-server

docker build -t gcr.io/IMAGE_PATH/multichain-app ./multichain-app
docker push gcr.io/IMAGE_PATH/multichain-app
