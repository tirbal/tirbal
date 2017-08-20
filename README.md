# Tirbal
 
Tirbal is a table football rating algorithm. 
Though it could be used for any score-based 2v2 team games.

It is based on : 
	- a (custom) modification of the classical ELO algorithm implementation in order to take scores into account
	- a (custom) mathematical regression model

 
## Installation
 
mvn clean install
 
## Usage
 
java -jar tirbal-1.0-exec.jar --help

## Input file formats accepted

input score file needs to be .csv and formatted as described below:

team1Player1,team1Player2,scoreTeam1,scoreTeam2,team2Player1,team2Player2

Ex:
Jobi,Joba,10,1,Harry,Dawg
Marc,Samantha,0,10,MrSunshine,Dawg
[...]
 
## History
 
Version 1.0 (2017-08-19) - first version release
 
## Credits
 
Thierry BARTHEL
 
## License
 
The MIT License (MIT)

Copyright (c) 2015 Chris Kibble

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
