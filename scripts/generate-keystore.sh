#!/usr/bin/env bash
# Generates an Android release keystore and writes app/keystore.properties.
# Run once before your first release build.
set -euo pipefail

KEYSTORE_PATH="$(cd "$(dirname "$0")/.." && pwd)/app/release.jks"
PROPERTIES_PATH="$(cd "$(dirname "$0")/.." && pwd)/app/keystore.properties"

echo "=== Bernard/VB Android Keystore Generator ==="
echo ""
read -p "Key alias (e.g. bernardvb-release): " KEY_ALIAS
read -s -p "Keystore password (min 6 chars): " STORE_PASS; echo
read -s -p "Key password (can be same as keystore password): " KEY_PASS; echo
echo ""
read -p "Your name (for certificate): " CN
read -p "Organisation unit (e.g. Engineering): " OU
read -p "Organisation (e.g. Bernard VB Ltd): " ORG
read -p "City: " CITY
read -p "State/County: " STATE
read -p "Two-letter country code (e.g. GB): " COUNTRY

keytool -genkeypair \
  -v \
  -keystore "$KEYSTORE_PATH" \
  -alias "$KEY_ALIAS" \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -storepass "$STORE_PASS" \
  -keypass "$KEY_PASS" \
  -dname "CN=${CN}, OU=${OU}, O=${ORG}, L=${CITY}, ST=${STATE}, C=${COUNTRY}"

cat > "$PROPERTIES_PATH" <<EOF
storeFile=release.jks
storePassword=${STORE_PASS}
keyAlias=${KEY_ALIAS}
keyPassword=${KEY_PASS}
EOF

echo ""
echo "Done."
echo "  Keystore: $KEYSTORE_PATH"
echo "  Properties: $PROPERTIES_PATH"
echo ""
echo "IMPORTANT: Never commit release.jks or keystore.properties."
echo "Back up release.jks somewhere safe — you cannot re-upload to Google Play without it."
