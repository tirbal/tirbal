# Tirbal
 
Tirbal is a table football rating algorithm. 
Though it could be used for any score-based 2v2 team games.

It is based on : 
* a (custom) modification of the classical ELO algorithm implementation
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

## Record accepted
 
In order for a record to be accepted:
* match must be 1v1 or 2v2. 1v2 record will be discarded.
* all players must have plaid in at least 2 different teams. In other words, each players needs to have plaid in 2 different duo, or in 1 duo + 1 solo match. Otherwise the record will be discarded.
 
## History
 
Version 1.0 (2017-08-19) - first version release
 
## Credits
 
Thierry BARTHEL
 
## License
 
The MIT License (MIT)

See LICENCE file
