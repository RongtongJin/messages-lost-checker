# Messages-lost-checker

Check if messages are lost by the history log.

## Supported log types

- General log

Format: clientName enqueue/dequeue msgId (It can also be a body, but it must be unique)

Example:
```$xslt
producer enqueue AC1100025116578B27FD4DCB82A63626
consumerB dequeue AC1100025116578B27FD4DCB82A63626
producer enqueue AC1100025116578B27FD4DCB82A73629
consumerA dequeue AC1100025116578B27FD4DCB82A73629
producer enqueue AC1100025116578B27FD4DCB82A8362C
consumerA dequeue AC1100025116578B27FD4DCB82A8362C
producer enqueue AC1100025116578B27FD4DCB82A9362F
consumerB dequeue AC1100025116578B27FD4DCB82A9362F
……
```

- Jepsen log

Format follows the history.txt obtained from jepsen test for total-queue model

Example:
```$xslt
1	:ok	:enqueue	16
3	:ok	:dequeue	10
0	:ok	:dequeue	16
2	:invoke	:dequeue	nil
1	:invoke	:enqueue	17
3	:invoke	:enqueue	18
0	:invoke	:dequeue	nil
……
```
## Usage

### Build

```mvn clean install```

### Command Line

```
 <main class> [command] [command options]
  Commands:
    jepsen      
      Usage: jepsen [options]
        Options:
          --dir, -d
            Directory of inspection
          --file, -f
            File of inspection
          --prefix, -p
            Prefix for file filtering

    file      
      Usage: file [options]
        Options:
        * --file, -f
            File of inspection

    dir      
      Usage: dir [options]
        Options:
        * --dir, -d
            Directory of inspection
          --prefix, -p
            Prefix for file filtering

```
### Example

```
## Check general log file
java -jar target/Checker.jar file -f ~/output.txt
 
## Check all general log files in the specified directory with prefix filter
java -jar target/Checker.jar dir -d ~/test -p msglog 

## Check jepsen log file
java -jar target/Checker.jar jepsen -f history.txt 

## Check all jepsen log files in the specified directory with prefix filter
java -jar target/Checker.jar jepsen -d ~/test -p history 
```



