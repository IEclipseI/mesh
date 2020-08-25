package com.example.mesh_test_task.service.impl;

import com.example.mesh_test_task.domain.ApiError;
import com.example.mesh_test_task.repository.ApiErrorRepository;
import com.example.mesh_test_task.service.ApiErrorService;
import org.springframework.stereotype.Service;

@Service
public class ApiErrorServiceImpl implements ApiErrorService {
    private final ApiErrorRepository apiErrorRepository;

    public ApiErrorServiceImpl(ApiErrorRepository apiErrorRepository) {
        this.apiErrorRepository = apiErrorRepository;
    }

    @Override
    public ApiError save(ApiError apiError) {
        return apiErrorRepository.save(apiError);
    }

    @Override
    public ApiError findLastApiError() {
        return apiErrorRepository.findTopByOrderByCreatedDesc();
    }
}
