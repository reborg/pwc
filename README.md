# pwc

pwc is the parallel vesion of the venerable Unix command line utility wc modified to run in parallel on multiple cores using Clojure reducers.

## How to install

Please try:

    java -version

from the command line. 

### jdk 1.7

    brew install pwc

if you're on a mac.

### jdk  1.6

Download the correct [jsr166.jar|http://g.oswego.edu/dl/concurrency-interest/] to your endorsed Java Home folder (on Mac Mountain Lion, /System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home/lib/endorsed). A jsr166.jar is bundled with the github project if you happen to have jsk 1.6.0_51. 

### Not on a Mac

Other platforms are supported as a Java executable:
    
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
