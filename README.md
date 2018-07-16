# Blockchain (private DLT) using MultiChain

Application for proposals using blockchain with MultiChain.

## Use case
Your organization wants to implement an internal system for submitting different types of applications (for example: request for new equipment). \
Each request must be approved by the manager. \
Requests should be indisputable and immutable. 

Example request and decision structure:
```
Request {userId, managerId, creationTime, requestContent}
Decision {managerId, Request, decisionResult, decisionTime}
```

**JSON**:
```
{
	"userId": "",
	"managerId": "",
	"creationTime": "",
	"requestContent": ""
}

{
	"managerId": "",
	"Request": {
            {
                "userId": "",
                "managerId": "",
                "creationTime": "",
                "requestContent": ""
            }
	},
	"decisionResult": "",
	"decisionTime": ""
}
```

# Architecture

Our solution consists of 5 services:
* `multichain-master` - multichain seed node
* `multichain-slave` - multichain 2nd node
* `multichain-explorer` - multichain 3rd node with UI for browsing blockchain
* `multichain-poc-server` - application backend (SpringBoot)
* `multichain-poc-app` - application frontend (Angular6 served by nginx)

## Deployment & running

Each service is packaged as a separate container.
To run whole system you can use:
* `docker` + `docker-compose`
* `kubernetes (minikube, Google Cloud or Azure)`

### Docker Compose

Prerequisites: local docker & docker-compose\
To run application you simply execute command:

```
run.sh
```

Services are started with tty attached to current terminal. \
To stop application just press:

```
Ctrl+C
```

### Kubernetes

To deploy application on kubernetes you need a kubernetes cluster.
It can be created easily using:
* Minikube (locally)
* Google Cloud
* Azure

#### Minikube

1. Install [Minikube](https://github.com/kubernetes/minikube)
2. Start the cluster:

```
minikube start
```

#### Google Cloud

Prerequisites: GCP Account

1. Install [GCP SDK/CLI](https://cloud.google.com/sdk/)
2. Start the cluster:

```
kubernetes-cluster-create-gcp.sh
```

#### Azure

Prerequisites: Azure Account

1. Install [Azure SDK/CLI](https://docs.microsoft.com/pl-pl/cli/azure/install-azure-cli?view=azure-cli-latest)
2. Start the cluster:

```
kubernetes-cluster-create-azure.sh
```

#### Deployment to kubernetes cluster

Prerequisites: running kubernetes cluster

To deploy application on kubernetes cluster you simple execute script:

```
kubernetes-deploy.sh
```

To undeploy application run command:

```
kubernetes-undeploy.sh
```

## Screens
TODO
<p align="center">
    <img alt="" src="" />
</p>

## DOCS

* [Getting started](https://www.multichain.com/getting-started/)
* [JAVA API](https://github.com/MultiChain/multichain)
* [MultiChain - base presentation](https://www.slideshare.net/coinspark/multichain-private-multicurrency-blockchain-platform)
* [MultiChain 2 nodes on one Windows](https://www.multichain.com/qa/9888/is-it-possible-to-create-two-nodes-on-a-single-system)

## License
This project is released under the Apache 2.0 license (see [LICENSE](LICENSE))