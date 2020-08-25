package com.example.mesh_test_task.service;

import com.example.mesh_test_task.domain.ApiError;

public interface ApiErrorService {
    ApiError save(ApiError apiError);

    ApiError findLastApiError();
}
