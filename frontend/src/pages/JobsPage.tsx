import { useEffect, useState } from "react";
import { getAllJobs } from "../api/job";
import type { Job } from "../types/job";

export default function JobsPage() {

  const [jobs, setJobs] = useState<Job[]>([]);

  useEffect(() => {
    // Fetch jobs from the API and set the state
    getAllJobs().then((data) => {
      setJobs(data);
    }).catch((error) => {
      console.error("Error fetching jobs:", error);
    });
  }, []);

  return (
    <div>
      <h1>Jobs Page</h1>
      <p>This is where the list of jobs will be displayed.</p>
      {jobs.length > 0 ? (
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