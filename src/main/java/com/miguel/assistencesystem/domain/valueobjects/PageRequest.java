package com.miguel.assistencesystem.domain.valueobjects;

public record PageRequest(int page, int pageSize) {

    private static final int MAX_PAGE_SIZE = 50;

    public PageRequest {
        if (page < 0) {
            throw new IllegalArgumentException("Page must be >= 0");
        }
        if (pageSize <= 0 || pageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Invalid page size");
        }
    }

    public int offset() {
        return page * pageSize;
    }
}

