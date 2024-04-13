set dotenv-load

admin:
  #!/bin/bash
  if ! command -v fd &> /dev/null
  then
    echo "command fd is not found"
    exit 1
  fi

  if ! command -v entr &> /dev/null
  then
    echo "command entr is not found"
    exit 1
  fi

  cd admin
  fd --full-path src/main -e java -e jsp | entr -r gradle dev

web:
  #!/bin/bash
  if ! command -v fd &> /dev/null
  then
    echo "command fd is not found"
    exit 1
  fi

  if ! command -v entr &> /dev/null
  then
    echo "command entr is not found"
    exit 1
  fi

  cd web
  fd --full-path src/main -e java -e jsp | entr -r gradle dev


db:
  #!/bin/bash
  if ! command -v usql &> /dev/null
  then
    echo "command usql is not found"
    exit 1
  fi

  usql $DB_URL

migrate argv="@":
  #!/bin/bash
  if ! command -v usql &> /dev/null
  then
    echo "command usql is not found"
    exit 1
  fi

  usql $DB_URL -f {{ argv }}
