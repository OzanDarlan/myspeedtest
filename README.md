# Speedtest
Simple Java project allowing download and upload speedtesting for broadcast connections using curl or similar in cron. The project consists of a simple servlet that allows for GET and POST requests. 

## GET
By default the GET request returns 2048 bytes of random data but this may be overriden with the `l` query parameter supplying the number of bytes required. For example using `?l=100` would return 100 bytes.

## POST
The POST request allows you to send data and measure how long it takes. This method simply reads in the bytes posted.

## Authentication
If configured the servlet may used BASIC authentication to limit who use the servlet. The username and password required are read from the `speedtest.username` and `speedtest.password` environment variables (read using `System.getenv`) making the project easily deployable to Heroku or similar. If either environment variables is not set authentication is not used.

## GET using curl
This command will write the output from the GET request to a file called temp.txt and output the time it took to connect and the time it took to do the download to sysout. The command uses BASIC authentication (username: jdoe, password: password).

	$ curl --user jdoe:password -s https://myapp.herokuapp.com -o temp.txt -w "%{time_connect};%{time_total};"
	0.171;7.913;

## POST using curl
This command will post the data from the temp.txt file to the application and output the time it took to connect and the time it took to do the download to sysout. The command uses BASIC authentication (username: jdoe, password: password).

	$ curl --user jdoe:password -X POST https://myapp.herokuapp.com -w "%{time_connect};%{time_total};" -o /dev/null --data @temp.txt
	0.104;2.120;

## Comma vs period for decimals
By default curl use period for decimals but piping the output through `sed` may change that:

	$ curl --user jdoe:password -s https://myapp.herokuapp.com -o temp.txt -w "%{time_connect};%{time_total};" | sed -e 's/\./,/g'
	0,171;7,913;

## Capture w/ timestamp?
Need to capture the output and a timestamp for each request? No problem  - simply combine with `date` e.g.:

	$ curl --user jdoe:password -s https://myapp.herokuapp.com -o temp.txt -w "%{time_connect};%{time_total};" >> capture.txt && printf `date +"%Y-%m-%dT%H:%M:%S"` >> capture.txt
