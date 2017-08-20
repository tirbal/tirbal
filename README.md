# Tirbal
 
Tirbal is a table football rating algorithm. 
Though it could be used for any score-based 1v1 & 2v2 team game.

It is based on : 
* a (custom) variation of the classical ELO algorithm implementation
* a (custom) mathematical regression model

 
## Installation
 
`mvn clean install`
 
## Usage
 
`java -jar target/tirbal-1.0-exec.jar --help`

## Input file description

Input score file format must be .csv

It must only contains a chronological series of team vs team results records.
Each team vs team result record is described in one-line.


### Records details

Every record - 1v1 and 2v2 - must be formatted as described below:

**team1Player1,team1Player2,scoreTeam1,scoreTeam2,team2Player1,team2Player2**


Example of valid input file:

Jobi,Joba,10,1,Harry,Dawg

Harry,Dawg,1,10,Joba,Jobi

Marc,,2,10,MrSunshine,,

[...]

### Records details - Nota Bene

Note that the order of the player within a team doesn't matter.
Hence the 2 following lines are equivalent :

Jobi,Joba,10,1,Harry,Dawg

Harry,Dawg,1,10,Joba,Jobi

Also note that for 1 player team, multiple syntaxes are accepted.
Hence the 3 following lines are equivalent :

Marc,,2,10,MrSunshine,,

Marc,Marc,2,10,MrSunshine,,

MrSunshine,MrSunshine,10,2,Marc,Marc

### Record accepted
 
In order for a record to be accepted:
* match must be 1v1 or 2v2. 1v2 records will be discarded.
* all record players must have plaid in at least 2 different teams. Any record will be discarded until this condition is met.

Example: admitting a 2v2 record is being considered. This record is taken into account if and only if each single player has also played a 1v1 or another 2v2 with a different team-mate. 
 
## History
 
Version 1.0 (2017-08-19) - first version release
 
## Credits
 
Thierry BARTHEL
 
## License
 
The MIT License (MIT)

See LICENCE file
