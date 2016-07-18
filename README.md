# Contractility Analyzer

The Contractility Analyzer is an open source ImageJ plugin designed to evaluate cell contractility experiments. It contains two modules.

## Spheroid Perimeter module ##
The Spheroid Perimeter module selects the largest cluster of connected pixels on a binary image sequence and returns its perimeter on each frame. 
## Hole module ##
The Hole module identifies cell-free areas (holes) as a clusters of connected pixels on a binary image sequence and returns their areas. 

# Usage
1. Compile the project (e.g [Eclipse]( http://imagejdocu.tudor.lu/doku.php?id=howto:plugins:the_imagej_eclipse_howto))
2. Copy the ContractilityAnalyzer_.jar file to the ImageJ plugin folder.
3. Open an image sequance.
  * [Spheroid sample]( http://pearl.elte.hu/ContractilityAnalyzer/spheroid.zip)
  * [Hole sample]( http://pearl.elte.hu/ContractilityAnalyzer/holes.zip)
4. Convert the grayscale images to binary images ([ImageJ > Process]( https://imagej.nih.gov/ij/docs/guide/146-29.html))
  * [Binary spheroid sample]( http://pearl.elte.hu/ContractilityAnalyzer/spheroid-bin.zip)
  * [Binary hole sample]( http://pearl.elte.hu/ContractilityAnalyzer/holes-bin.zip)
5. Run the necessary plugin.
  * Spheroid Perimeter module: *Plugins > Contractility Analyzer > Spheroid Perimeter*
  * Hole module: *Plugins > Contractility Analyzer > Hole Size*
6. Save the *Results table*

![alt tag](http://pearl.elte.hu/ContractilityAnalyzer/sample1.gif "Spheroid sample")
![alt tag](http://pearl.elte.hu/ContractilityAnalyzer/sample2.gif "Hole sample")
