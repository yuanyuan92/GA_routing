package boardAndFlow;

import java.util.ArrayList;
import java.util.List;

public class XYRouting {

	public static List<Integer> getRouteNode(int src, int dst, int ary) {
		// TODO Auto-generated method stub
		int srcX = src % ary;
		int srcY = src / ary;
		int dstX = dst % ary;
		int dstY = dst / ary;
		//		int manhattanDistance = Math.abs(deltaX) + Math.abs(deltaY);
		List<Integer> node = new ArrayList<>();
		//input source node
		int curX = srcX;
		int curY = srcY;
		node.add(xyToNode(curX, curY, ary));
		while (curX!=dstX) {
			if (curX<dstX) {
				curX++;
			}else {
				curX--;
			}
			node.add(xyToNode(curX, curY, ary));
		}
		while (curY!=dstY) {
			if (curY<dstY) {
				curY++;
			}else {
				curY--;
			}
			node.add(xyToNode(curX, curY, ary));
		}			
		return node;
	}
	private static int xyToNode(int x,int y,int ary) {
		return ary * y + x;
	}
}
