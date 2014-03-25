FastqToolbox
============

A collection of command line tools to modify fastq files (http://en.wikipedia.org/wiki/FASTQ_format).

All scripts are designed to be saved as jars and run from terminal using java -jar SCRIPT ARGUMENTS. Use -Xms and -Xmx arguments to dedicate more memory to java (java -Xms4000m -Xmx12000m -jar SCRIPT ARGUMENTS). 
To get script specific information, please call the scripts without any arguments. 

Contained scripts: 

1. FilterForPairs:

Description: Filters two fastq files to contain only entries with matching name. 
Rational: Some read alignmeners (GSNAP, Mapsplice) do not tolerate fastq entries only contained in one of paired end sequencing fastq files. These single entries are generated if fastq files were modified by adaptor trimming. To allow mapping this script removes fastq entries present in only one of the paired end fastq files. 
Output: Fastq files matching input names appended by "_matched".
Usage: java -jar filterForPairs /path/to/fastq1 /path/to/fastq2

