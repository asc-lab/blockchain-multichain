RESOURCE_GROUP=multichain-rg
CLUSTER=multichain-k8s
az group create --name ${RESOURCE_GROUP} --location westeurope
az acs create --orchestrator-type kubernetes --agent-count 1 ---agent-vm-size Standard_D2_v2 --resource-group ${RESOURCE_GROUP} --name ${CLUSTER} --generate-ssh-keys
az acs kubernetes get-credentials --resource-group=${RESOURCE_GROUP} --name=${CLUSTER}
