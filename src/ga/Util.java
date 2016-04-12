package ga;

public class Util {
	// great common division
    public static long gcd(long m, long n) {  
        if (m < n) {// ��֤m>n,��m<n,�������ݽ���  
            long temp = m;  
            m = n;  
            n = temp;  
        }  
        if (m % n == 0) {// ������Ϊ0,�������Լ��  
            return n;  
        } else { // ����,���еݹ�,��n����m,�������n  
            return gcd(n, m % n);  
        }  
    }
    
    // least common multiple  
    public static long lcm(long m, long n) {  
        return m * n / gcd(m, n);  
    }

	
	
	public static boolean getPosibility( int scale , int idx) {
		// individual is good enough ? the better individual has more possibility to crossover
		double propablity = ((double)scale - idx) / scale;
		if (Math.random() < propablity) {
			return true;
		}else {
			return false;
		}		
	}
}
