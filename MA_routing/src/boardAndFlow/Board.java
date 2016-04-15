package boardAndFlow;

public class Board {
	private int [][] mesh;
	
	/**
	 * @param ary ¶şÎ¬ÍøÂç´óĞ¡
	 */
	
	public Board(int ary) {
		super();
		mesh = new int [ary][ary];
		int cnt = 0;
		for (int i = 0; i < mesh.length; i++) {
			for (int j = 0; j < mesh[i].length; j++) {
				mesh[i][j] = cnt;
				cnt++;
			}
		}			
	}
	public void showXY() {
		for (int i = 0; i < mesh.length; i++) {
			for (int j = 0; j < mesh[i].length; j++) {
				System.out.print("("+i+","+j+")\t");
			}
			System.out.println();
		}
	}
	public void showNode() {
		for (int[] is : mesh) {
			for (int i : is) {
				System.out.print(i+"\t");
			}
			System.out.println();
		}
	}
}
