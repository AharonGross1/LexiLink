# Hypernym-Database
Hyponymy and hypernymy are semantic relations between a term belonging in a set that is defined by another term and the latter. In other words, the relationship of a subtype (hyponym) and the supertype (also called umbrella term, blanket term, or hypernym). The semantic field of the hyponym is included within that of the hypernym. For example, pigeon, crow, and eagle are all hyponyms of bird, their hypernym. (source - [wikipedia](https://en.wikipedia.org/wiki/Hyponymy_and_hypernymy)).

### This program provides 2 functions:

1) Search for occurences of a specific hyponymy in the database and print them to the console.
2) Create a file listing all of the different Hypernymy and the Hyponymy related to them in the database.

Note - the corpus provided here is comprised of a single file. You can download a bigger corpus [here](https://drive.google.com/drive/folders/11aVnX9r-k5iy2GafZd-o5lBBgeNRuFDN?usp=sharing).

### How to use:

Download the files and unzip the corpus. Please make sure that the corpus' folder contains only files that belong to the corpus.

Open the cmd at the file location and enter the command ```javac Main.java HypernymDatabase.java Search.java Sort.java Hypernym.java Hyponym.java```.

For option 1 (as explained previously), enter the command ```java Main 1 ...path\corpus keyword``` with ```1``` indicating option 1 ```...Path\corpus``` being the folder containing the corpus' files and ```keyword``` being the Hyponym to search for in the database.

For option 2 (as explained previously), enter the command ```java Main x ...path\corpus ...path\fileName``` with ```x``` being any string other than "1", ```...Path\corpus``` the folder containing the corpus' files and ```...path\fileName``` the location for the hypernymy-hyponymy database output.

