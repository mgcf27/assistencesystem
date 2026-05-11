package com.miguel.assistencesystem.application.dto.response;

import java.util.List;

public record PageResponse<T>(
		List<T> items,
		int currentPage,
		int pageSize,
		long totalItems,
		int totalPages
		) {}
