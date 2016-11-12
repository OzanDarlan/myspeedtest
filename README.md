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

## Deploy to heroku
The following will do the following:

* Clone the git repo into a directory called `myspeedtest`
* Create an app on heroku (w/ a generated name)
* Set configuration in heroku (username and password for auth)
* Push the app to heroku (which in turn builds it using Maven)
* Open it

Please note that you must have an account on heroku before beginning and you must have the heroku CLI (https://devcenter.heroku.com/articles/heroku-command-line). The `heroku login` command logs you into heroku from the command line.

	$ git clone https://github.com/lekkimworld/speedtest.git myspeedtest
	$ cd myspeedtest
	$ heroku login
	$ heroku create
	Creating app... done, ⬢ serene-shore-36960
	https://serene-shore-36960.herokuapp.com/ | https://git.heroku.com/serene-shore-36960.git
	
	$ heroku config:set speedtest.username='foo'
	Setting speedtest.username and restarting ⬢ serene-shore-36960... done, v3
	speedtest.username: foo
	
	$ heroku config:set speedtest.password='bar'
	Setting speedtest.password and restarting ⬢ serene-shore-36960... done, v4
	speedtest.password: bar
	
	$ git push heroku master
	Counting objects: 125, done.
	Delta compression using up to 4 threads.
	Compressing objects: 100% (41/41), done.
	Writing objects: 100% (125/125), 17.84 KiB | 0 bytes/s, done.
	Total 125 (delta 31), reused 125 (delta 31)
	remote: Compressing source files... done.
	remote: Building source:
	remote:
	remote: -----> Java app detected
	...
	...
	remote: -----> Compressing...
	remote:        Done: 61.1M
	remote: -----> Launching...
	remote:        Released v5
	remote:        https://serene-shore-36960.herokuapp.com/ deployed to Heroku
	remote:
	remote: Verifying deploy.... done.
	To https://git.heroku.com/serene-shore-36960.git
	 * [new branch]      master -> master
	
	$ heroku open
	
Love heroku!!

To delete the app (named `serene-shore-36960`):

	$ heroku destroy serene-shore-36960 --confirm serene-shore-36960