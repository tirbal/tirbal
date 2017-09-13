# Changelog
All notable changes to this project will be documented in this file.

## 1.1 - 2017-09-14
### Change
Changed the "games number" ponderation factor in the individual ELO estimator.
~ ln(x) 		    -> logit(x) * ln(x) 
~ logarithmic shape -> sigmoid shape (getting logarithmic at around ~50 games)

This will improve the realism of the individual ELO estimations - particulary for individual playing in a lot of various teams.

## 1.0 - 2017-08-10
### First release