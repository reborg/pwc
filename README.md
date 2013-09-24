# pwc

pwc is a parallel vesion of the venerable Unix command line utility wc modified to run on multiple cores using Clojure reducers.

## Is pwc really that faster than wc?

PWC is not yet faster than wc even removing the JVM startup time. But I'm still Working on it and there are more optimisations to apply. I'm confident it will be faster at some point. At the moment:

    time pwc ~/tmp/3.1G-huge-file.txt (41s)
    time wc  ~/tmp/3.1G-huge-file.txt (19s)

## Additional features

pwc also calulcates word frequencies ordered by most frequent words:

    echo "Very nice counting. Really very nice" > temp.txt
    pwc -f temp.txt
    1 6 36 temp.txt
    (["nice" 2] ["very" 1] ["Really" 1] ["counting" 1] ["Very" 1])

## What's missing

* pwc doesn't support being part of a pipe, so a filename is required (instead of optional like wc).
* pwc does not accept multiple file inputs at once
* pwc only implemented flags are "f" and "t" and "h" for help
* sligtly different way to handle word tokenization resulting in slightly different results

## How to install

You need Java installed. Please try:

    java -version

from the command line to see on what Java version you're running. Install Java following the instructions from the Oracle web site if you don't have it. Then follow the instructions below based on your version/OS.

### jdk 1.7 on a Mac

    brew tap reborg/taps
    brew install pwc

### jdk 1.6 on a Mac

Download the correct [jsr166.jar](http://g.oswego.edu/dl/concurrency-interest/) to your endorsed Java Home folder (on Mac Mountain Lion, /System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home/lib/endorsed). A jsr166.jar is bundled with the github project if you happen to have jsk 1.6.0_51. 

### Not on a Mac

Other platforms are supported executing pwc as a plain Java executable:
    
    curl -O https://github.com/reborg/pwc/archive/0.2.2.tar.gz
    tar xvfz 0.2.2.tar.gz
    cd 0.2.2
    java -jar target/pwc.jar [-ft] file

I suggest to save pwc.jar in a known location and create an alias in your .bash_profile as:

    alias pwc='java -jar /your/absolute/path/pwc.jar'
    pwc(){ java -jar /your/absolute/path/pwc.jar $1; }

for everyday execution in your terminal.

## Thanks

* https://github.com/thebusby/iota for allowing pwc to easily stream files into workers
* the entire Clojure ecosystem :)

## TODO:
* more compatibility with wc flags
* Add ordering to the partial reduce-f split if that improves overall perfs
* Calculate split size based on size of the input
* dependency on the jsr166.jar could be added as part of homebrew installation
