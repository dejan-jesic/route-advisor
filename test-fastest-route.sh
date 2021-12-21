#!/bin/sh

ORIGIN=$1
DESTINATION=$2

ORIGIN=${ORIGIN:=SRB}
DESTINATION=${DESTINATION:=ESP}

echo "Calculating fastest route from $ORIGIN to $DESTINATION..."

URL="http://localhost:8080/api/v1/routing?origin=$ORIGIN&destination=$DESTINATION"

RESPONSE=$(curl -X GET -w "%{stderr}" -s -o - "$URL" 2>&1)

echo "$RESPONSE"
