# Vocab Lookup

Tool for taking an exported Quizlet vocab cards and expanding the definition using information from a publicly available source like Websters dictionary. 

## Basic Process

1. Download word set from Quizlet, or create one yourself. Export with tab separator and `\n\n` between words. 
2. Run either `MainInteractive` or `MainManual` with your file
3. For `MainInteractive` the will be found in a new file. for `MainManual` the input file is modified in-place. 

For each word

1. Looks up word with Websters dictionary (can be extended via interface)
2. Scrapes HTML for specific data such as example usage and pronunciation
3. Export vocab and metadata into a format that can be re-imported into Quizlet to create a richer card definition. 

## Example

https://quizlet.com/18795939/gre-basic-flash-cards/

Downloaded to `example/gre_500_raw.txt`

See results after running in `example/gre_500_raw.txt.completed`

Then import back into Quizlet
