CHAIN=MyChain
REQ_STREAM=requests
RES_STREAM=decisions

# 1. Create ledgers

echo "Creating ledgers:"
multichain-cli $CHAIN create stream $REQ_STREAM false
multichain-cli $CHAIN create stream $RES_STREAM false
multichain-cli $CHAIN subscribe $REQ_STREAM
multichain-cli $CHAIN subscribe $RES_STREAM

# 2. Create admin user

echo
echo "Creating ADMIN:"
ADMIN_1=`multichain-cli $CHAIN getnewaddress`
multichain-cli $CHAIN grant $ADMIN_1 ${REQ_STREAM}.admin
multichain-cli $CHAIN grant $ADMIN_1 ${RES_STREAM}.admin
multichain-cli $CHAIN grant $ADMIN_1 receive
multichain-cli $CHAIN send $ADMIN_1 0


# 3. Create regular users

echo
echo "Creating USERS:"
USER_1=`multichain-cli $CHAIN getnewaddress`
multichain-cli $CHAIN grant $USER_1 send
multichain-cli $CHAIN grant $USER_1 receive
multichain-cli $CHAIN send $USER_1 0
multichain-cli $CHAIN grant $USER_1 ${REQ_STREAM}.write

USER_2=`multichain-cli $CHAIN getnewaddress`
multichain-cli $CHAIN grant $USER_2 send
multichain-cli $CHAIN grant $USER_2 receive
multichain-cli $CHAIN send $USER_2 0
multichain-cli $CHAIN grant $USER_2 ${REQ_STREAM}.write

# 4. Create managers

echo
echo "Creating MANAGERS:"
MANAGER_1=`multichain-cli $CHAIN getnewaddress`
multichain-cli $CHAIN grant $MANAGER_1 send
multichain-cli $CHAIN grant $MANAGER_1 receive
multichain-cli $CHAIN grant $MANAGER_1 ${REQ_STREAM}.write
multichain-cli $CHAIN grant $MANAGER_1 ${RES_STREAM}.write

MANAGER_2=`multichain-cli $CHAIN getnewaddress`
multichain-cli $CHAIN grant $MANAGER_2 send
multichain-cli $CHAIN grant $MANAGER_2 receive
multichain-cli $CHAIN grant $MANAGER_2 ${REQ_STREAM}.write
multichain-cli $CHAIN grant $MANAGER_2 ${RES_STREAM}.write


# 5. User1 creates request1 for manager1

echo
echo "Creating Request 1:"
REQ_1="{\"requesterId\": \"$USER_1\", \"accepterId\": \"$MANAGER_1\", \"creationTime\": \"`date --rfc-3339=seconds`\", \"requestContent\": \"Bardzo prosze o urlop w dniu 20-06-2018\"}"
REQ_1_HEX=`echo "${REQ_1}" | xxd -p | tr -d '\n'`
multichain-cli $CHAIN publishfrom $USER_1 $REQ_STREAM req1 "${REQ_1_HEX}"


# 6. Manager1 accepts request1

echo
echo "Creating Decision for Request 1:"
RES_1="{\"accepterId\": \"$MANAGER_1\", \"request\": $REQ_1, \"decisionResult\": \"ACCEPTED\", \"decisionTime\": \"`date --rfc-3339=seconds`\" }"
RES_1_HEX=`echo "${RES_1}" | xxd -p | tr -d '\n'`

multichain-cli $CHAIN publishfrom $MANAGER_1 $RES_STREAM req1 "${RES_1_HEX}"


# 7. User2 creates request2 for manager2

echo
echo "Creating Request 2:"
REQ_2="{\"requesterId\": \"$USER_1\", \"accepterId\": \"$MANAGER_1\", \"creationTime\": \"`date --rfc-3339=seconds`\", \"requestContent\": \"Bardzo prosze o urlop w dniu 25-06-2018\"}"
REQ_2_HEX=`echo "${REQ_2}" | xxd -p | tr -d '\n'`
multichain-cli $CHAIN publishfrom $USER_2 $REQ_STREAM req2 "${REQ_2_HEX}"


# 8. Manager2 rejects request2 (decision but with different outcome/value)

echo
echo "Creating decision for Request 2:"
RES_2="{\"accepterId\": \"$MANAGER_1\", \"request\": $REQ_1, \"decisionResult\": \"DECLINED\", \"decisionTime\": \"`date --rfc-3339=seconds`\" }"
RES_2_HEX=`echo "${RES_2}" | xxd -p | tr -d '\n'`

multichain-cli $CHAIN publishfrom $MANAGER_2 $RES_STREAM req2 "${RES_2_HEX}"


# Tests
# 1. Get request[id] – success when ID exists, fail when does not

echo
echo "Retrieving Request 1:"
multichain-cli $CHAIN liststreamkeyitems $REQ_STREAM req1 | jq .[0].data | xxd -r -p
echo "Retrieving Decision for Request 1:"
multichain-cli $CHAIN liststreamkeyitems $RES_STREAM req1 | jq .[0].data | xxd -r -p

# 2. Get request belonging to the other user – should FAIL (permission denied? Unable to decrypt?)
# Multichain does not provide required read permissions.
# They can be implemented at the application layer:
# https://www.multichain.com/developers/stream-confidentiality/

echo
echo "Retrieving Request 2:"
multichain-cli $CHAIN liststreamkeyitems $REQ_STREAM req2 | jq .[0].data | xxd -r -p
echo "Retrieving Decision for request 1:"
multichain-cli $CHAIN liststreamkeyitems $RES_STREAM req2 | jq .[0].data | xxd -r -p

# 3. Get all my requests (for given userID)

echo
echo "Retrieving Requests for USER_1:"
multichain-cli $CHAIN liststreampublisheritems $REQ_STREAM $USER_1

# 4. Get list of requests of user[ID]
#    Fail – if ID != myUserID
#    OK –
#    PASS – if the user is manager or admin

echo
echo "Retrieving Requests for USER_2:"
multichain-cli $CHAIN liststreampublisheritems $REQ_STREAM $USER_2

# 5. User should not have privileges to create decisions:
echo
echo "Forging Decision for Request 1 (by USER_1):"
multichain-cli $CHAIN publishfrom $USER_1 $RES_STREAM req1 "${RES_1_HEX}"

