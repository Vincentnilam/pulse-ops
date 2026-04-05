import { useEffect, useState } from "react";
import { getAllJobs } from "../api/jobs";
import type { Job } from "../types/job";

export default function JobsPage() {

  const [jobs, setJobs] = useState<Job[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

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
    <div>
      <h1>Jobs Page</h1>
      <p>This is where the list of jobs will be displayed.</p>
      {loading ? (
        <p>Loading jobs...</p>
      ) : error ? (
        <p>{error}</p>
      ) : jobs.length > 0 ? (
        <ul>
          {jobs.map((job) => (
            <li key={job.id}>{job.type}</li>
          ))}
        </ul>
      ) : (
        <p>No jobs available.</p>
      )}
    </div>
  );
}