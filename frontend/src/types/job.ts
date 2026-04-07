export interface Job {
  id: string
  status: string
  type: string
  createdAt: string
}

export interface CreateJobRequest {
  type: string
}

export interface JobStatusUpdateMessage {
  jobId: string
  status: string
  type: string
}