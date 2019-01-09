# Multichain cluster

* **base**: base Ubuntu with the latest Multichain deamon installed
* **master**: Based on the "base" image running a master node, creates a blockchain and runs it. *Important: only for development since any node can connect, anyone can administer and the RPC interface is open to all.*
* **node**: Based on the same "base" image and connects to the master node
* **explorer**: node with the Multichain explorer installed

Based on [Docker images (master-multichain) GitHub](https://github.com/jmcewan/docker-multichain).

### Start the cluster

```
run.sh
```

### Stop the cluster

```
Ctrl+C
```

### Endpoints

* [localhost:2750](http://localhost:2750) - Explorer
* [localhost:8002](http://localhost:8002) - RPC

## Problems

If you have a problem with end of lines (CRLF), remove all containers/images, convert scripts to unix version (```dos2unix```) and run ```run.sh```.

## URLS
* [Docker images (master-multichain) Docker Registry](https://hub.docker.com/r/kunstmaan/master-multichain/)
* [Docker images (master-multichain) GitHub](https://github.com/jmcewan/docker-multichain)