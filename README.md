# Private Blockchain (private DLT) using MultiChain

Application for proposals using blockchain with MultiChain.

## Repo info

* `multichain-app` - Angular 6 client application
* `multichain-cli` - example test scenario and execute this by MultiChain CLI
* `multichain-cluster` - blockchain infrastructure
* `multichain-server` - Java & Spring Boot & MultiChain Java API

## Architecture

Our solution consists of 5 services:
* `multichain-master` - multichain seed node
* `multichain-slave` - multichain 2nd node
* `multichain-explorer` - multichain 3rd node with UI for browsing blockchain
* `multichain-server` - application backend (SpringBoot)
* `multichain-app` - application frontend (Angular6 served by nginx)

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



## Deployment & running

Each service is packaged as a separate container.
To run whole system you can use `docker` + `docker-compose`.

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

## Screens
<p align="center">
    <img alt="Desktop main version" src="https://raw.githubusercontent.com/asc-lab/blockchain-multichain/master/images/multichain_desktop_main.png" />
</p>

<p align="center">
    <img alt="Mobile menu" src="https://raw.githubusercontent.com/asc-lab/blockchain-multichain/master/images/multichain_mobile_menu.png" />
</p>

<p align="center">
    <img alt="Initial users" src="https://raw.githubusercontent.com/asc-lab/blockchain-multichain/master/images/multichain_user_list.png" />
</p>

<p align="center">
    <img alt="Request list" src="https://raw.githubusercontent.com/asc-lab/blockchain-multichain/master/images/multichain_request_list.png" />
</p>

<p align="center">
    <img alt="Decision list" src="https://raw.githubusercontent.com/asc-lab/blockchain-multichain/master/images/multichain_decision_list.png" />
</p>

## DOCS

* [Getting started](https://www.multichain.com/getting-started/)
* [JAVA API](https://github.com/MultiChain/multichain)
* [MultiChain - base presentation](https://www.slideshare.net/coinspark/multichain-private-multicurrency-blockchain-platform)
* [MultiChain 2 nodes on one Windows](https://www.multichain.com/qa/9888/is-it-possible-to-create-two-nodes-on-a-single-system)

## License
This project is released under the Apache 2.0 license (see [LICENSE](LICENSE))
