#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "Health Check >>> running......"
echo "Health check >>> IDLE_PORT: $IDLE_PORT"
echo "Health check >>> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
      echo "Health check >>> Successfully find real"
      switch_proxy
      break
  else
      echo "Health check >>> process isn't running now, or we can't get any response"
      echo "Health check >>> ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "Health check >>> checking failed...... "
    echo "Health check >>> stop deploying"
    exit 1
  fi

  echo "Health check >>> can't connect, retry connecting......"
  sleep 10
done