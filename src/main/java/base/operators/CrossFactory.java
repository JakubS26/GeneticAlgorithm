package base.operators;

public class CrossFactory {
	public static AbstractOperator getCrossover(CrossType c) {
		
		if(c == CrossType.HX) {
			return new HalfCrossover();
		} else if(c == CrossType.OX) {
			return new OrderCrossover();
		} else if(c == CrossType.PMX) {
			return new PartMapCrossover();
		}
		
		return null;
		
	}
}
