#!/usr/bin/env bash

set -e

## Make the GPG keys available to perform release
openssl aes-256-cbc -pass pass:${GPG_KEYRING_CIPHER_PASSWORD} -in travis/gpg/secring.gpg.enc -out travis/gpg/secring.gpg -d
openssl aes-256-cbc -pass pass:${GPG_KEYRING_CIPHER_PASSWORD} -in travis/gpg/pubring.gpg.enc -out travis/gpg/pubring.gpg -d

mvn --batch-mode --settings travis/settings.xml release:clean release:prepare release:perform

## Clean up the GPG keys
rm -v travis/gpg/secring.gpg travis/gpg/pubring.gpg
