#  MultiChain CLI

## Test scenario

### Actors
* Admin user – can see everything, can create new users, can assign/remove permissions
* Regular user – can add requests and receive decision results
* Manager user – can create decisions for requests and view decisions

### Steps
1.	Create new ledger
2.	Create admin user
3.	Create regular users: user1, user2, user3
4.	Create managers: manager1, manager2
5.	User1 creates request1
6.	Manager1 accepts request1 (create decision for request)
7.	User2 creates request2
8.	Manager2 rejects request2 (decision with different outcome/value)

### Before start 
Install **MultiChain** on your machine. For Linux instruction below:
```
wget https://www.multichain.com/download/multichain-1.0.4.tar.gz
tar -xvzf multichain-1.0.4.tar.gz
cd multichain-1.0.4/
sudo su
mv multichaind multichain-cli multichain-util /usr/local/bin
```

## Getting started

The entire scenario described this README.md was shown in the script ```run.sh```. \
Only a few steps are described below.

### 1. Create new ledger
```
multichain-util create <name>
```
Example:
```
multichain-util create agreements
```

After execute:
```
MultiChain 1.0.4 Utilities (latest protocol 10010)

Blockchain parameter set was successfully generated.
You can edit it in /root/.multichain/agreements/params.dat before running multichaind for the first time.

To generate blockchain please run "multichaind agreements -daemon".
```

Run ledger:
```
multichaind agreements -daemon
```

```
MultiChain 1.0.4 Daemon (latest protocol 10010)

Starting up node...

Looking for genesis block...
Genesis block found

Other nodes can connect to this node using:
multichaind agreements@169.254.87.24:6453

This host has multiple IP addresses, so from some networks:
multichaind agreements@10.101.6.83:6453
multichaind agreements@169.254.13.99:6453
multichaind agreements@172.26.17.209:6453
multichaind agreements@10.0.75.1:6453
multichaind agreements@192.168.56.1:6453
multichaind agreements@169.254.191.128:6453
multichaind agreements@169.254.48.244:6453

Listening for API requests on port 6452 (local only - see rpcallowip setting)

Node ready.
```

Now user can go to interactive mode and use MultiChain API:
```
multichain-cli <name>
```
```
multichain-cli agreements
```

Write help for show all support commands.

**Now we’ll connect to this blockchain from elsewhere.**

### 2. Create admin user

Node on which the leger was created - it becomes an admin.

### 3. Create regular users: user1, user2, user3

From another node (second server):
```
multichaind agreements@IP_ADDRESS:6453 (or another address from point 2 list)
```
You should be told that the blockchain was successfully initialized, but you do not have permission to connect. You should also be shown a message containing an address in this node’s wallet.

Return to main (admin) node and add permission for new node:
```
multichain-cli agreements grant <WALLET_ADDRESS> connect,send,receive
```

Now try reconnecting again from the second server:
```
multichaind agreements@IP_ADDRESS:6453 -daemon
```

### 4. User1 creates request1 for manager1

For create messages/save any data in data storage, we must create **stream**.
On first node (admin) in interactive mode:
```
create stream agreements-stream false
```
The ```false``` means the stream can only be written to by those with explicit permissions.
Show actually permissions:
```
listpermissions agreements-stream.*
```
So for now, only the first server has the ability to write to the stream, as well as administrate. Let’s publish something to it, with **Request1**.

Payload in JSON:
```
{"requesterId": "123", "accepterId": "456", "creationTime": "07-06-2018", "requestContent": "Bardzo prosze o urlop w dniu 20-06-2018" }
```
In MultiChain 1.0 you can't store plain json in a stream. What you need to do is convert your json to hexadecimal data, and use the publish function to store it in a stream. \
[Online Converter](https://codebeautify.org/string-hex-converter) \
In the real world, this conversion should happen inside the application! \
Payload in Hex:
```
7b227265717565737465724964223a2022313233222c202261636365707465724964223a2022343536222c20226372656174696f6e54696d65223a202230372d30362d32303138222c202272657175657374436f6e74656e74223a2022426172647a6f2070726f737a65206f2075726c6f70207720646e69752032302d30362d3230313822207d
```
Let's add this message to stream:
```
publish agreements-stream request1 7b227265717565737465724964223a2022313233222c202261636365707465724964223a2022343536222c20226372656174696f6e54696d65223a202230372d30362d32303138222c202272657175657374436f6e74656e74223a2022426172647a6f2070726f737a119206f2075726c6f70207720646e69752032302d30362d3230313822207da
{"method":"publish","params":["agreements-stream","request1","<PAYLOAD>"],"id":1,"chain_name":"agreements"}

ddf6782b84f824eb118a9fd1f7bbaa708536089a1c1912ea1b9296c67b791989
```
Returned value is **txid**. Save this value.

### 5. Manager1 accepts request1

From another node I want to see items in stream.
Show all streams:
```
liststreams
```

Subscribe to selected stream
```
subscribe agreements-stream
```

Show all items:
```
liststreamitems agreements-stream

{"method":"liststreamitems","params":["agreements-stream"],"id":1,"chain_name":"agreements"}

[
    {
        "publishers" : [
            "1ESfAJ6acvucJzqgz6VLk4yjshr134dCLWWyjp"
        ],
        "key" : "request1",
        "data" : "7b227265717565737465724964223a2022313233222c202261636365707465724964223a2022343536222c20226372656174696f6e54696d65223a202230372d30362d32303138222c202272657175657374436f6e74656e74223a2022426172647a6f2070726f737a119206f2075726c6f70207720646e69752032302d30362d3230313822207da",
        "confirmations" : 11,
        "blocktime" : 1528379216,
        "txid" : "ddf6782b84f824eb118a9fd1f7bbaa708536089a1c1912ea1b9296c67b791989"
    }
]

```

If you not subscribe and want show items:
```
liststreamitems agreements-stream
{"method":"liststreamitems","params":["agreements-stream"],"id":1,"chain_name":"agreements"}

error code: -703
error message:
Not subscribed to this stream
```

I want to add new message to stream, so:
```
publish agreements-stream decision1 7b202261636365707465724964223a2022343536222c2022726571756573744964223a202264646636373832623834663832346562313138613966643166376262616137303835333630383961316331393132656131623932393663363762373931393839222c20226465636973696f6e526573756c74223a20224143434550544544222c20226465636973696f6e54696d65223a202230372d30362d3230313822207d
```

Output:
```
error code: -704
error message:
This wallet contains no addresses with permissions to write to this stream and global send permission.
```

Stream creator must add permission "publish to the stream". On admin node:
```
grant 1E2nKaU7ZBqSn6hNkq7uJz5g3VWrqx1YRkota7 agreements-stream.write

{"method":"grant","params":["1E2nKaU7ZBqSn6hNkq7uJz5g3VWrqx1YRkota7","agreements-stream.write"],"id":1,"chain_name":"agreements"}

c5a471b2eff9ed441b44dc4065e9d173924aaaf19e4bdb25e3ae41299e73886c
```

Now node two can add message to stream:
```
publish agreements-stream decision1 7b202261636365707465724964223a2022343536222c2022726571756573744964223a202264646636373832623834663832346562313138613966643166376262616137303835333630383961316331393132656131623932393663363762373931393839222c20226465636973696f6e526573756c74223a20224143434550544544222c20226465636973696f6e54696d65223a202230372d30362d3230313822207d
```

Show messages in stream:
```
liststreamitems agreements-stream
{"method":"liststreamitems","params":["agreements-stream"],"id":1,"chain_name":"agreements"}

[
    {
        "publishers" : [
            "1ESfAJ6acvucJzqgz6VLk4yjshr134dCLWWyjp"
        ],
        "key" : "request1",
        "data" : "7b227265717565737465724964223a2022313233222c202261636365707465724964223a2022343536222c20226372656174696f6e54696d65223a202230372d30362d32303138222c202272657175657374436f6e74656e74223a2022426172647a6f2070726f737a119206f2075726c6f70207720646e69752032302d30362d3230313822207da",
        "confirmations" : 30,
        "blocktime" : 1528379216,
        "txid" : "ddf6782b84f824eb118a9fd1f7bbaa708536089a1c1912ea1b9296c67b791989"
    },
    {
        "publishers" : [
            "1E2nKaU7ZBqSn6hNkq7uJz5g3VWrqx1YRkota7"
        ],
        "key" : "decision1",
        "data" : "7b202261636365707465724964223a2022343536222c2022726571756573744964223a202264646636373832623834663832346562313138613966643166376262616137303835333630383961316331393132656131623932393663363762373931393839222c20226465636973696f6e526573756c74223a20224143434550544544222c20226465636973696f6e54696d65223a202230372d30362d3230313822207d",
        "confirmations" : 11,
        "blocktime" : 1528381899,
        "txid" : "07b90d9555d18e4e6defb945a38950604652818d1e074a0500ac4a71a348b176"
    }
]
```