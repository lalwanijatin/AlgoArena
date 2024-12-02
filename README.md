## Steps to Set Up This Project Locally

### Prerequisites

Ensure that **OpenSSL** is installed on your local machine. OpenSSL is required to generate public and private keys for JWT tokens.

### Steps

1. Clone the repository:
   ```bash
   git clone <repo-url>
   ```

2. Navigate to the resources directory:
   ```bash
   cd AlgoArena/algo-arena-be/src/main/resources
   ```

3. Generate the private key using OpenSSL:
   ```bash
   openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048
   ```

4. Generate the public key from the private key:
   ```bash
   openssl rsa -pubout -in private.pem -out public.pem
   ```

### Handling End-Of-Line Character Issues on Windows

Windows may use `CRLF` (Carriage Return and Line Feed) as the End-Of-Line (EOL) character, which can cause compatibility issues when executing files in a Linux environment. To avoid such problems, you can convert EOL characters to `LF` (Line Feed) by running the following command in the `AlgoArena` folder:

```bash
find . -type f -exec dos2unix {} ;
```

> **Note**: Ensure that the `dos2unix` utility is installed on your system before running the command.
