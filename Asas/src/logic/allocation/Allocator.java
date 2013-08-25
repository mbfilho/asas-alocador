package logic.allocation;

import logic.dto.AllocationResult;

public interface Allocator {
	public AllocationResult allocate(boolean onlyInPreferedSlots);
}
