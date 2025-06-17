package com.project.jobms.job;

import com.project.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAllJobs();

    void createJob(Job job);

    JobDTO getJobById(Long id);

    Boolean deleteJob(Long id);

    Boolean updateJob(Long id, Job updatedJob);
}
