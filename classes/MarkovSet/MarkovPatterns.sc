
		^markovset.embedInStream(inval, repeats) // responsibility stays with markov set
		var item, bag, inStream, index; 
                			{
	var <>markovset, <>repeats;
	
	*new { arg markovset, pattern, repeats = inf;
			^super.newCopyArgs(pattern, markovset, repeats)
		
	}
	embedInStream { arg inval;
		^markovset.embedSpyInStream(pattern, repeats, inval) // spy responsibility stays with set
	}
}
