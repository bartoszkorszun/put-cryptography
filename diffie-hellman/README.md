# Diffie-Hellman Key Exchange in Kotlin

This project implements the Diffie-Hellman key exchange algorithm using Kotlin. It demonstrates how two parties, A and B, can securely exchange cryptographic keys over a public channel.

## Overview

The Diffie-Hellman algorithm allows two parties to generate a shared secret key that can be used for secure communication. The key exchange process involves the following steps:

1. Both parties agree on a large prime number `n` and a primitive root `g`.
2. Each party generates a private key (random number) and computes a public key.
3. The public keys are exchanged, and each party computes the shared secret key using their private key and the other party's public key.

## Project Structure

- `src/main/kotlin/DiffieHellman.kt`: Contains the main logic for the Diffie-Hellman algorithm.
- `src/main/kotlin/A.kt`: Represents participant A in the key exchange.
- `src/main/kotlin/B.kt`: Represents participant B in the key exchange.
- `src/test/kotlin/DiffieHellmanTest.kt`: Contains unit tests for the implementation.
- `build.gradle.kts`: Gradle configuration file for the project.

## Building the Project

To build the project, ensure you have Gradle installed and run the following command in the project root directory:

```
./gradlew build
```

## Running the Project

To run the key exchange simulation, execute the following command:

```
./gradlew run
```

## Example Usage

The project simulates the Diffie-Hellman key exchange between two participants, A and B. Upon execution, both participants will compute the same shared secret key, demonstrating the effectiveness of the algorithm.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.