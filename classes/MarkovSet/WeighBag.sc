/* 
	//counts are kept to allow efficient weighing
		var n;
		items = items.asCollection;
		n = items.size;
		weights = if(weights.isNil)
		{  Array.fill(n, items.size.reciprocal) }
		{ weights.normalizeSum };
	
	count { arg sum;
			counts = weights.collect { arg item; (item * sum).asInteger };
			numItems = counts.sum;
	}