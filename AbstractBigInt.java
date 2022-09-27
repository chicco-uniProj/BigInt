package poo.Math;

import java.util.Iterator;



public abstract class AbstractBigInt implements BigInt
{

		@Override
		public String toString()
		{	StringBuilder sb=new StringBuilder(length());
			Iterator<Integer>it=iterator();
			while(it.hasNext())
			{	sb.append(it.next());
			}
			return sb.toString();
		}
		
		@Override
		public boolean equals(Object o)
		{	if(!(o instanceof BigInt))
				return false;
			if(o==this)
				return true;
			BigInt b=(BigInt)o;
			if(b.length()!=this.length())
				return false;
			Iterator<Integer>it=iterator();
			Iterator<Integer>it2=b.iterator();
			while(it.hasNext())
			{	int a=it.next();
				int c=it2.next();
				if(a!=c)
					return false;
			}
			return true;
		}
		
		@Override
		
		public int hashCode() {
			return toString().hashCode();
		}//hashCode
		
		@Override
		public int compareTo(BigInt o) {
			
			if(this.length()>o.length())
				return 1;
			if(this.length()<o.length())
				return -1;
			
			Iterator<Integer>it=iterator();
			Iterator<Integer>it2=o.iterator();
			while(it.hasNext())
			{	int primo=it.next();
				int secondo=it2.next();
				if(primo>secondo)
					return 1;
				if(primo<secondo)
					return -1;
				continue;
			}
			return 0;
		}
		
}
