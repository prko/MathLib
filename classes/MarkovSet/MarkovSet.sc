	/*  
	is associated with a number of possible transition probabilities to another 
	element of the set. 
	(that contain objects and their probabilities). 
	By parsing in a stream the Set 'learns' what element can possibly follow another.
		
	
	
	put { arg prevkey, nextkeys, weights, precision=0.01;
			var bag;
			bag = this.nodeClass.with(nextkeys, weights, precision);
			dict.put(prevkey, bag);
	}
	
	putAll { arg args;
		args.do({ arg item;
			var key, next, weights;
			#key, next, weights = item;
			this.put(key, next.postln, weights)
		});
	}
			var val, stream;
	        		val = stream.next;
				if(val.isNil) { ^this } 
	        	};
	
			var stream, prev, val;
		  		stream = pattern.asStream;
	        	 	val = prev = stream.next(inval);
	        	 	inval = val.yield;
	       		val = stream.next(inval);
	        	 	inval = val.yield;
	        	 	repeats.do {
	        	 		if((prev.notNil) and: {val.notNil})
	       			{
	       				this.read(prev, val)  
	       			};
	        			prev = val;
	        			
	        			val = stream.next(inval);
	        			inval = val.yield;
	        		}
	        		^inval
	        		
	}
	
	asStream { ^Routine.new { arg inval; this.embedInStream(inval) } }
	embedInStream { arg inval, repeats=inf; 
                	^inval
			isRTF = path.endsWith(".rtf");
			protect {
			if(isRTF){
				source = Document.open(path);
				stream = CollStream.new;
				if(source.isNil) { Error("wrong path" ++ path).throw };
				stream << source.text;
				stream.pos = 0;
				this.parseIO(stream, delimiter, blockSize, completionFunc, inf);
			} {
				source = File(path, "r");
				this.parseIO(source, delimiter, blockSize, completionFunc, inf);
			};
				source.close;
			};
	
	parseIO { arg iostream, delimiter=$ , blockSize=0, completionFunc, maxLength=32;
				var pat;
				
				pat = if(blockSize == 0) {
						Proutine.new {
							var item;
							while {
								item = iostream.readUpTo(delimiter);
								item.notNil and: { item != "" }
							} {
								(item ++ delimiter).asSymbol.yield;
							}
						}
					}{
						Proutine.new {
								var item;
								while {
									item = iostream.nextN(blockSize);
									item.notNil and: { item != "" }
								} {
									item.asSymbol.yield;
								}
							}
						};
				this.parse(pat, maxLength);
				completionFunc.value;
	}
	

	
	

	
	registerAsNext { arg obj;
		^this.register(obj)
	}
			// generate the key to look for. might be generated from an array of objects
			prevKey = this.register(prevObj);
			nextKey = this.registerAsNext(nextObj);
			
	
	put { arg prevObj, nextObj, weights;
			var bag, key, nextKey;
			key = this.register(prevObj);
			nextKey = this.registerAsNext(nextObj);
			nextKey.asCompileString.debug;
			bag = this.nodeClass.with(nextKey, weights);
			if(updateSeeds, { seeds.add(key) });
			dict.put(key, bag);
	}
	
	

//nth order markov set
	
	*fill { arg n, stream, order=1;
	       ^this.new(nil, order).parse(stream, n);
	}
	
		^if(obj.isSequenceableCollection)
		{ obj.collect({ arg item; this.register(item) }).debug }
		{ this.register(obj) }
	}
	embedSpyInStream { arg pattern, repeats=inf, inval;
			var stream, item, prev, prevbuffer, buffer, val;
		  		stream = pattern.asStream;
	        	 	prevbuffer = buffer = Array.fill(order, { inval = stream.next(inval) });
	        		repeats.do {
	        			val = stream.next(inval);
	        			if(val.notNil) {
	        				this.read(buffer, val);
	        				prevbuffer = buffer;
	        				buffer = buffer.copy;
	        				prev = buffer.removeAt(0);
	        				buffer.add(val);
	        			};
	        			inval = val.yield;
	        		
	       		};
	}
	
                		nil.alwaysYield;
                	^inval