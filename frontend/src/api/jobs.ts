import type { CreateJobRequest, Job } from '../types/job';

const getAllJobs = async (): Promise<Job[]> => {
  const response = await fetch('/api/jobs');
  if (!response.ok) {
    throw new Error('Failed to fetch jobs');
  }
  return response.json();
}

const getJobById = async (id: string): Promise<Job> => {
  const response = await fetch(`/api/jobs/${id}`);
  if (!response.ok) {
    throw new Error(`Failed to fetch job with id ${id}`);
  }
  return response.json();
}

const createJob = async (jobData: CreateJobRequest): Promise<Job> => {
  const response = await fetch('/api/jobs', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(jobData),
  });
  if (!response.ok) {
    throw new Error('Failed to create job');
  }
  return response.json();
}


export { getAllJobs, getJobById, createJob };