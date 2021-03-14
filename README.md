# Vocab Lookup

Simple vocab utility I made for taking in Quizlet word lists and expanding the cards using information from public sources like Websters dictionary. 

## Basic Process

1. Download word set from Quizlet, or create one yourself. Export with tab separator and `\n\n` between words. 
2. Run either `MainInteractive` or `MainManual` with your file
3. For `MainInteractive` the will be found in a new file. for `MainManual` the input file is modified in-place. 

For each word

1. Looks up word with Websters dictionary or whatever is selected (right now just Websters). 
2. Parses info such as example usage and pronunciation
3. Export vocab to file with this new info included. 

## Example

https://quizlet.com/18795939/gre-basic-flash-cards/

Downloaded to `example/gre_500_raw.txt`

See results after running in `example/gre_500_raw.txt.completed`

Then import back into Quizlet. 
