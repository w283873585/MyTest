package com.thread.testFoundation;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 *	线程安全委托
 *	此案例将线程安全委托给locations
 *	还可以委托给多个相互独立的线程安全类，即相互之间没有不变性约束
 */
public class TestThreadSecurityDelegate {
	public static class Point{
		public final int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static class DelagatingVehicleTracker {
		private final ConcurrentMap<String, Point> locations;
		private final Map<String, Point> unmodifiableMap;
		
		public DelagatingVehicleTracker(Map<String, Point> points) {
			locations = new ConcurrentHashMap<String, Point>(points);
			unmodifiableMap = Collections.unmodifiableMap(locations);
		}
		
		public Map<String, Point> getLocations() {
			return unmodifiableMap;
		}
		
		public Point getLocation(String id) {
			return locations.get(id);
		}
		
		public void setLocation(String id, int x, int y) {
			if (locations.replace(id, new Point(x, y)) == null) {
				throw new IllegalArgumentException("invaild vehicle name: " + id);
			}
		}
	}
	
	public static void main(String[] args) {
	}
}