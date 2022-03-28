# message-error-correction
Message error correction using parity bits

This is a university project.
The goal of this project is to design and implement message error-correcting mechanism using parity bits. 

Since It is a simple terminal app, no unit tests were developed.

Program reads provided input txt file inside "files" directory, encodes the message and writes it into corresponding txt file inside "encoded" directory.

We can than open txt file with encoded message (message is represented by bits) and introduce errors (Up to 2 bit errors for each 16 bits).
Program will decode incorrect message, correct bit errors and save the output inside "decoded" directory.
Corresponding files inside "files" and "decoded" directories are supposed to contain the same message.

Example:
1) files/test.txt          contains  "bbbaaa"
2) encode message and save in  encoded/test.txt
3) encoded/test.txt        contains  "011000100100001101100010010000110110001001000011011000010111111101100001011111110110000101111111"
4) introduce error - flip two first bits:
5) encoded/test.txt        contains  "101000100100001101100010010000110110001001000011011000010111111101100001011111110110000101111111"
6) decode message containing errors
7) decoded/test.txt        contains  "bbbaaa"
