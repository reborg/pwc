# pwc

pwc is the parallel vesion of the venerable Unix command line utility wc modified to run in parallel on multiple cores using Clojure reducers.

## How to install

Be sure to have Java 1.7.0 installed. Then if you are on a Mac just use [Homebrew|http://brew.sh]:

    brew install pwc

other platforms are supported as a Java executable:
    
    curl -O pwc.jar
    java -jar pwc.jar [-clmw] [file ...]

I suggest to save pwc.jar in a known location and create an alias in your .bash_profile as:

    alias pwc='java -jar /your/absolute/path/pwc.jar'
    pwc(){ java -jar /your/absolute/path/pwc.jar $1; }

for everyday execution in your terminal.

## TODO:
* actual parallel word count algo
* parsing of cmd line args 
* immediate compatibility of basic usage
* Homebrew plumbing
