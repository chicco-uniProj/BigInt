package poo.Math;



import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;




public class BigIntLL extends AbstractBigInt{
	private LinkedList<Integer>bigList=new LinkedList<Integer>();
	
	
	
	
	public BigIntLL(String s)
	{	this.bigList=new LinkedList<>(this.converti(s));
		eliminaZeri();
	}
	
	public BigIntLL(int x)
	{	this.bigList=new LinkedList<>(this.converti(x));
		eliminaZeri();
	}
	
	
	public BigIntLL(BigIntLL b)
	{	this.bigList=new LinkedList<>(b.bigList);
		eliminaZeri();
	}
	
	public BigIntLL(LinkedList<Integer>big)
	{	this.bigList=new LinkedList<>(big);
		eliminaZeri();
	}
	
	private void eliminaZeri()
	{	if(this.length()==1)
			return;
		Iterator<Integer>it=this.bigList.iterator();
		boolean eZero=true;
		while(it.hasNext() && eZero)
		{	int numero=it.next();
			if(numero==0 && it.hasNext())
				it.remove();
			else
				eZero=false;
		}
	}
	
	private LinkedList<Integer> converti(int num)
	{	return converti(String.valueOf(num));
	
	}
	private LinkedList<Integer> converti(String s)
	{	LinkedList<Integer>ret=new LinkedList<Integer>();
		char[] b=s.toCharArray();
		for(char c:b)
		{	if(!Character.isDigit(c))
				throw new IllegalArgumentException("La stringa contiene caratteri non numerici");
			int charact=c-'0';
			ret.addLast(charact);
		}
		return ret;
	}
	
	public LinkedList<Integer>getBigList()
	{	return new LinkedList<>(bigList);
	}
	
	@Override
	public String value() {
		return toString();
	}
	
	@Override
	public int length() {
		return bigList.size();
	}

	@Override
	public BigInt factory(int x) {
		return new BigIntLL(x);
	}
	
	

	@Override
	public BigInt add(BigInt a) {
		if(a.equals(factory(0)))
			return new BigIntLL(this);
		if(this.equals(factory(0)))
			return a;
		int riporto=0;
		
		BigIntLL copiaDiA=new BigIntLL(a.value());
		
		StringBuilder ret=new StringBuilder(length());
		
		ListIterator<Integer>it=null;
		ListIterator<Integer>it2=null;
		
		if(this.compareTo(a)>0)
		{	it=bigList.listIterator(bigList.size());
			it2=copiaDiA.getBigList().listIterator(copiaDiA.getBigList().size());
		}
		else
		{	it=copiaDiA.getBigList().listIterator(copiaDiA.getBigList().size());
			it2=bigList.listIterator(bigList.size());
		}
		
		
		while(it.hasPrevious())
		{	int primo=it.previous();
			int secondo=0;
			if(it2.hasPrevious())
				secondo=it2.previous();
			int somma=primo+secondo+riporto;
		
			if(somma<10)
			{	ret.append(somma);
				riporto=0;
			}
			
			if(somma>9)
			{	ret.append((""+somma).charAt(1));
				riporto=1;
			}	
		}
		if(riporto!=0)
			ret.append(riporto);
		
		ret.reverse();	
		return new BigIntLL(ret.toString());
		
	}

	@Override
	public BigInt sub(BigInt s) {
		if(s.compareTo(this)>0)
			throw new ArithmeticException("Numero negativo non consentito");
		if(s.equals(factory(0)))
			return new BigIntLL(this);
		if(this.equals(s))
			return new BigIntLL(0);
		StringBuilder ret=new StringBuilder(length());
		
		BigIntLL copiaDiS=new BigIntLL(s.value());
		ListIterator<Integer>it=bigList.listIterator(bigList.size());
		ListIterator<Integer>it2=copiaDiS.getBigList().listIterator(copiaDiS.getBigList().size());
		
	
		int prestito=0;
		while(it.hasPrevious())
		{	int primo=it.previous();
			int secondo=0;
			if(it2.hasPrevious())
				secondo=it2.previous();
			int differenza=(primo+prestito)-secondo;
			
			
			if(differenza>=0)
			{	ret.append(differenza);
				prestito=0;
			}
			if(differenza<0)
			{	prestito=-1;
				ret.append(differenza+10);
			}
		}
		
		
		ret.reverse();
		return new BigIntLL(ret.toString());
		
	}

	@Override
	public BigInt mul(BigInt m) {
		if(m.equals(factory(0)))
			return new BigIntLL(0);
		if(m.equals(factory(1)))
			return new BigIntLL(this);
		BigIntLL copiaDiM=new BigIntLL(m.value()); 
		
		List<Integer>grande=new LinkedList<Integer>();
		List<Integer>piccola=new LinkedList<Integer>();
		
		if(this.compareTo(m)<0)
		{	grande=copiaDiM.getBigList();//m
			piccola=getBigList();//this
		}
		else
		{	grande=getBigList();	//this
			piccola=copiaDiM.getBigList();//m
		}
		
		Collections.reverse(grande);
		Collections.reverse(piccola);
		
		
		BigInt ret=new BigIntLL(0);	
		int nZeri=0;
		int riporto=0;
		for(int moltiplicatore : piccola)
		{	StringBuilder sb=new StringBuilder();
			for(int j=0;j<nZeri;j++)
				sb.append(""+0);
			for(int moltiplicando : grande)
			{	int prodotto=(moltiplicando*moltiplicatore)+riporto;
				if(prodotto>9)
				{	String prod =""+prodotto;
					sb.append(prod.charAt(1));
					riporto=Integer.parseInt(prod.substring(0,1));
				}
				if(prodotto<10)
				{	sb.append(prodotto);
					riporto=0;
				}	
			}
			nZeri++;
			if(riporto!=0 )
				sb.append(riporto);
			
			String bo=sb.reverse().toString();
			BigInt daAgg=new BigIntLL(bo);
			ret=ret.add(daAgg);
		}	
		return ret;
	}
	@Override
	public BigInt div(BigInt d) {
		
		if(this.equals(factory(0)))
			return factory(0);
		if(d.equals(factory(0)))
			throw new IllegalArgumentException("ERRORE denominatore uguale a zero");
		if(d.compareTo(this)>0)
			throw new IllegalArgumentException("Divisore piu grande del Dividendo");
		if(this.equals(d))
			return new BigIntLL(1);
		if(d.equals(factory(1)))
			return new BigIntLL(this);
		Iterator<Integer>dividendo=bigList.listIterator();
		
		StringBuilder sb=new StringBuilder(d.length());   //per prendere il numero da abbassare 
		for(int i=0;i<d.length();i++)
		{	int n=dividendo.next();
			sb.append(n);
		}
		
		BigInt passo=new BigIntLL(sb.toString());  //il numero che "abbasso"
		if(d.compareTo(passo)>0)
		{	sb.append(dividendo.next());
			passo=new BigIntLL(sb.toString());
		}
		
		BigInt quoziente=new BigIntLL(1);
		BigInt daSottrarre=null;
		for(;;)											//al massimo 9 iterazini
		{	daSottrarre=quoziente.mul(d);
			if(daSottrarre.compareTo(passo)>0)
			{	quoziente=quoziente.decr();
				break;
			}
			quoziente=quoziente.incr();
		}
		daSottrarre=daSottrarre.sub(d); //questo perche il numero da sottrarre calcolato nel for ha fatto un'iterazione in piu
		StringBuilder risultato=new StringBuilder(quoziente.toString());		
		while(dividendo.hasNext())
		{	StringBuilder nuovoDiv=new StringBuilder(passo.sub(daSottrarre).toString());
			nuovoDiv.append(dividendo.next());
	
			BigInt nuovoDividendo=new BigIntLL(nuovoDiv.toString());

			BigInt nuovoQuoz=trovaQuozionte(d, nuovoDividendo);			
			risultato.append(nuovoQuoz.toString());
			daSottrarre=nuovoQuoz.mul(d);
			
			passo=nuovoDividendo;
			
		}
		return new BigIntLL(risultato.toString());
	}
	private BigInt trovaQuozionte(BigInt d, BigInt numeroCheAbbasso) {
		BigInt ret=new BigIntLL(1);
		for(;;)											//al massimo 9 iterazini
		{	if(ret.mul(d).compareTo(numeroCheAbbasso)>0)
			{	ret=ret.decr();
				break;
			}
			ret=ret.incr();
		}
		return ret;
	}




	@Override
	public Iterator<Integer> iterator() {
		return bigList.iterator();
	}
	
	public static void main(String...args)
	{	BigInt a=new BigIntLL("2");
		System.out.println("Il risultato della classe mia BigInt è: "+a.pow(128));
		
		BigInteger b=new BigInteger("2");
		System.out.println("Mentre da BigInteger di java è:         "+b.pow(128));

		
	}

	
}
