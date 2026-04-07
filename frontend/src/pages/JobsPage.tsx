import { useEffect, useState } from "react";
import { createJob, getAllJobs } from "../api/jobs";
import type { CreateJobRequest, Job } from "../types/job";
import JobTable from "../components/JobTable";
import CreateJobForm from "../components/CreateJobForm";

export default function JobsPage() {

  const [jobs, setJobs] = useState<Job[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const handleCreateJob = async (data: CreateJobRequest) => {
    const createdJob = await createJob(data);
    setJobs((prev) => [createdJob, ...prev]);
  };

  useEffect(() => {
    // Fetch jobs from the API and set the state
    getAllJobs().then((data) => {
      setJobs(data);
    }).catch((error) => {
      console.error("Error fetching jobs:", error);
      setError("Failed to load jobs. Please try again later.");
    }).finally(() => {
      setLoading(false);
    });
  }, []);

  return (
    <div className="min-h-screen bg-slate-50 px-4 py-8">
      <div className="mx-auto max-w-6xl">
        <div className="mb-6">
          <h1 className="text-3xl font-bold tracking-tight text-slate-900">
            PulseOps Jobs
          </h1>
          <p className="mt-2 text-sm text-slate-600">
            View and track background jobs.
          </p>
        </div>

        <CreateJobForm onSubmit={handleCreateJob} />

        <div className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
          {loading ? (
            <p className="text-sm text-slate-600">Loading jobs...</p>
          ) : error ? (
            <p className="text-sm font-medium text-red-600">{error}</p>
          ) : jobs.length > 0 ? (
            <JobTable jobs={jobs} />
          ) : (
            <p className="text-sm text-slate-600">No jobs available.</p>
          )}
        </div>
      </div>
    </div>
  );
}