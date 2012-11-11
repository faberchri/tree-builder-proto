package clusterer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author faber
 * 
 * Is immutable object.
 *
 */
class SimpleAttribute implements Attribute {
	private final double average;
	private final double stdDev;
	private final int support;
	private final double[] leafList; //FIXME is mutable: we would need something like: List<Integer> items = Collections.unmodifiableList(Arrays.asList(0,1,2,3));
	
	public SimpleAttribute(double average) {
		this.average = average;
		this.stdDev = 0.0;
		this.support = 1;
		leafList = new double[] {average};
	}
	
	public SimpleAttribute(double average, double stdDev,
			int support, double[] leafList) {
		this.average = average;
		this.stdDev = stdDev;
		this.support = support;
		this.leafList = leafList;
	}
	
//	public SimpleAttribute(List<Attribute> attributesToCombine) {
//		int sizeOfNewLeafList = 0;
//		for (Attribute attribute : attributesToCombine) {
//			sizeOfNewLeafList += attribute.getLeafList().length;
//		}		
//		double[] tmpAr = new double[sizeOfNewLeafList];
//		int prevAttLength = 0;
//		for (Attribute attribute : attributesToCombine) {
//			System.arraycopy(attribute.getLeafList(), 0, tmpAr, prevAttLength, attribute.getLeafList().length);
//		}
//		leafList = tmpAr;
//		
//		int tmpSup = 0;
//		for (Attribute attribute : attributesToCombine) {
//			 tmpSup += attribute.getSupport();
//		}
//		this.support = tmpSup;
//		
//		double tmpAvg = 0.0;
//		for (Double avgLi : leafList) {
//			tmpAvg += avgLi;
//		}
//		this.average = tmpAvg / leafList.length;
//		
//		double tmpStD = 0.0;
//		for (Double avgLi : leafList) {
//			tmpStD += Math.pow((avgLi-this.average),2.0);
//		}
//		this.stdDev = Math.sqrt(tmpStD/(leafList.length - 1.0));
//
//	}
	
	public double getAverage() {
		return average;
	}
	
	public double getStdDev() {
		return stdDev;
	}
	
	public int getSupport() {
		return support;
	}
	
	public double[] getLeafList() {
		return leafList;
	}
	
	@Override
	public String toString() {
		return "Avg.: "
				.concat(String.valueOf(getAverage()))
				.concat(", StdDev.: ")
				.concat(String.valueOf(getStdDev()))
				.concat(", Sup.: ")
				.concat(String.valueOf(getSupport()));
	}
}
