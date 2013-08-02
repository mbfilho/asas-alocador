package allocation;

public interface Allocator {
	public AllocationResult allocate(boolean onlyInPreferedSlots);
}
