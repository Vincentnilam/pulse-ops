export interface Job {
  id: string
  status: string
  type: string
  createdAt: string
}

export interface CreateJobRequest {
  type: string
}