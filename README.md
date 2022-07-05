# Message-error-correction
## _Message error correction using parity bits_



The goal of this project is to design and implement message error-correcting mechanism using parity bits.
It is a simple terminal app, no tests were implemented.


## How it works

- program reads text input from file inside "files" directory
- message is encoded 
- encoded message is written to text file inside "encoded" directory
- user can introduce bit errors manually flipping bits in text file located in "encoded" directory
- text with bit errors is read from file inside  "encoded" directory
- bit errors are corrected and original message is retreived
- decoded and corrected message is written to text file in "decoded" directory





## License

MIT


