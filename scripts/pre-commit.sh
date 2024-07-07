#!/bin/bash
# implementation: baribari pre-push hook
# This script is based on a LGPL 3.0 licensed script.
#
# Original Script Copyright (C) 2023 Original Author
# Modifications Copyright (C) 2024 mirageoasis
#
# This script is modified under the same license, the GNU Lesser General Public License v3.0.

BASE_PATH=$(cd "$(dirname "$0")"/.. && pwd)
echo "Performing lint for changed files ..."

# Gradle을 사용하여 KTlint 검사 수행
./gradlew ktlintCheck