# Hypernym-Database

Hyponymy and hypernymy are semantic relations between a specific term and a broader, more general term. In simpler terms, a hyponym is a subtype of a hypernym. For example, banana, grape, and melon are all hyponyms of the hypernym fruit. (Source: [Wikipedia](https://en.wikipedia.org/wiki/Hyponymy_and_hypernymy)).
<br></br>
<a href="https://github.com/AharonGross1/LexiLink/blob/main/assets/hype-img.png?raw=true">
    <img src="https://github.com/AharonGross1/LexiLink/blob/main/assets/hype-img.png?raw=true" alt="Hype Image">
</a>

## Program Functions

This program provides two main functions:

1. **Search for Occurrences of a Specific Hyponym**: Find and display instances of a specific hyponym in the database.
2. **Create a Hypernym-Hyponym List**: Generate a file listing all hypernyms and their related hyponyms from the database.

## How to Use

### Prerequisites

Ensure that you have the Java Development Kit (JDK) installed on your computer.

### Downloading the Corpus

The program requires a corpus to operate. You can download a larger corpus [here](https://drive.google.com/drive/folders/11aVnX9r-k5iy2GafZd-o5lBBgeNRuFDN?usp=sharing). After downloading, unzip the corpus. Ensure the corpus folder contains only the relevant files.

### Compiling the Program

Open the command prompt at the file location and compile the Java files with the following command:

```javac -d bin src\*.java```

## Running the Program

### Option 1: Search for Occurrences of a Specific Hyponym

To search for occurrences of a specific hyponym in the database, use the following command:

```java -cp bin Main 1 path/to/corpus keyword```
- ```1``` indicates the search option.
- ```path/to/corpus``` is the folder containing the corpus files.
- ```keyword``` is the hyponym you want to search for.


### Option 2: Create a Hypernym-Hyponym List

To create a file listing all hypernyms and their related hyponyms, use the following command:

```java -cp bin Main x path/to/corpus path/to/outputFile```
- ```x``` can be any string other than "1" to indicate the listing option.
- ```path/to/corpus``` is the folder containing the corpus files.
- ```path/to/outputFile``` is the location where the output file will be created.

## Example Commands
### Search for Hyponym "banana":
```java -cp bin Main 1 path/to/corpus banana```

### Generate Hypernym-Hyponym List:
```java -cp bin Main generate /path/to/corpus /path/to/outputFile```

## Notes
- Ensure that the corpus folder only contains files relevant to the corpus to avoid processing errors.
- The output file for the hypernym-hyponym list will be created at the specified location.
