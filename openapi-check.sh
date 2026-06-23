#!/bin/bash

if [ ! -f target/openapi-temp/openapi.json ]; then
    echo "ERROR: target/openapi-temp/openapi.json does not exist."
    echo "Please run your build script first to generate the temporary OpenAPI file."
    exit 1
fi

if diff -w docs/openapi.json target/openapi-temp/openapi.json > /dev/null; then
    echo "OpenAPI documentation is up to date."
    exit 0
else
    echo "ERROR: OpenAPI documentation is outdated!"
    exit 1
fi