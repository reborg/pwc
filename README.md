# pwc

pwc is a parallel vesion of the venerable Unix command line utility wc modified to run on multiple cores using Clojure reducers. pwc also offers single words frequencies as optional output.

## Is pwc really that faster than wc?

pwc shines on multi-GB files. My benchmarks at the moment say that on a test 4Gb file on my laptop, wc output results in around 20" while pwc in 5". Also remember that pwc is calculating word frequencies as well!

## What's added?

* pwc calulcates word frequencies, while wc only counts lines, words and bytes.

## What's missing

* pwc doesn't support being part of a pipe, so a filename is required (instead of optional like wc).
* pwc accepts multiple file inputs at once

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
* more compatibility with wc flags, at the moment none is implemented
* should not throw out of mem for tests on big files
* Homebrew plumbing for quick and easy install (still WIP)
* Add ordering to the partial reduce-f split if that improves overall perfs
* Calculate split based on size of the input
