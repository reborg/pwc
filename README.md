# pwc

pwc is the parallel vesion of the venerable Unix command line utility wc modified to run in parallel on multiple cores using Clojure reducers.

## What's missing

pwc doesn't support being part of a pipe at the moment, so a filename is required (instead of optional like wc).

## How to install

Please first try:

    java -version

from the command line to see on what Java version you're running (if Java is installed on your machine). Then follow the instructions below based on that.

### java command not found

Please install Java.

### jdk 1.7 on a Mac

    brew tap homebrew/reborg
    brew install pwc

### jdk  1.6 on a Mac

Download the correct [jsr166.jar|http://g.oswego.edu/dl/concurrency-interest/] to your endorsed Java Home folder (on Mac Mountain Lion, /System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home/lib/endorsed). A jsr166.jar is bundled with the github project if you happen to have jsk 1.6.0_51. 

### Not on a Mac

Other platforms are supported as a plain Java executable:
    
    curl -O pwc.jar
    java -jar pwc.jar [-clmw] [file ...]

I suggest to save pwc.jar in a known location and create an alias in your .bash_profile as:

    alias pwc='java -jar /your/absolute/path/pwc.jar'
    pwc(){ java -jar /your/absolute/path/pwc.jar $1; }

for everyday execution in your terminal.

## TODO:
* immediate compatibility of basic usage
* Homebrew plumbing
* Calculate split based on size of the input
