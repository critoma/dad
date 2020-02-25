package euasepmigration;

import java.io.Serializable;

public class TaskImpl implements Serializable {
	private static final long serialVersion = 1L;
	private Task<int[], Long> t = (arrayItems) -> {
		Long sum = new Long(0L);
		
		for (int i : arrayItems) {
			sum += i;
		}
		System.out.println("TaskImpl - Sum of the array elements is:" + sum);
		return sum;
	};
	private int[] items;
	private Long theSum;
	
	public TaskImpl(int[] array) {
		this.items = array;
	}	

	public void runTask() {		
		theSum = t.executeTask(this.items);
	}

	public Long getTaskResult() {
		return this.theSum;
	}
}
