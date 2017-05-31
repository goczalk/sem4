package vector;

public class VectorsOfDifferentSizesException extends Exception{
	private int[] vectorSizes;
	
	public VectorsOfDifferentSizesException(int vS1, int vS2){
		super("Exception. Vectors of different size.");
		this.vectorSizes = new int[2];
		this.vectorSizes[0]= vS1;
		this.vectorSizes[1]= vS2;
	};
	
	public int[] getVectorSizes(){
		return this.vectorSizes;
	};
}