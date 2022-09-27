package poo.Math;

import java.util.Iterator;



public interface BigInt extends Comparable<BigInt>,Iterable<Integer>
{	
	default String value() //ritorna il valore del BigInt sottoforma di stringa di caratteri  //ugulale al toString
	{	StringBuilder sb=new StringBuilder(length());
		Iterator<Integer>it=iterator();
		while(it.hasNext())
		{	sb.append(it.next());
		}
		return sb.toString();
	}
	
	default int length() //ritorna il numero di cifre di questo BigInt
	{	Iterator<Integer>it=iterator();
		int ret=0;
		while(it.hasNext())
		{	it.next();
			ret++;
		}
		return ret;
		//for(Iterator<Monomio>it=iterator();it.hasNext();it.next(),ret++);
	}
	
	BigInt factory( int x );
	
	
	default BigInt incr()
	{	return this.add(factory(1));
	}
	
	default BigInt decr() //eccezione se this è zero
	{	return this.sub(factory(1));
	}
	BigInt add( BigInt a );
	
	BigInt sub( BigInt s );//ritorna un BigInt con la differenza tra this e d; atteso this>=d
	
	BigInt mul( BigInt m );
	
	BigInt div( BigInt d );//ritorna il quoziente della divisione intera tra this e d; atteso this>=d

	
	default BigInt rem( BigInt d )//ritorna il resto della divisione intera tra this e d; atteso this>=d
	{	return this.sub(this.div(d).mul(d));
	}
	
	
	default BigInt pow( int exponent ) //calcola la potenza this^exponent
	{	BigInt ret=factory(1);
		for(int i=0;i<exponent;i++)
			ret=ret.mul(this);
		return ret;
	
	}
}//BigInt