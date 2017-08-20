# Tirbal
 
Tirbal is a table football rating algorithm. 
Though it could be used for any score-based 2v2 team games.

It is based on : 
* a (custom) modification of the classical ELO algorithm implementation in order to take scores into account
* a (custom) mathematical regression model

 
## Installation
 
`mvn clean install`
 
## Usage
 
`java -jar tirbal-1.0-exec.jar --help`

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

See LICENCE file
