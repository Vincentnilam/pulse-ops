import { useState } from "react";
import type { CreateJobRequest } from "../types/job";

type CreateJobFormProps = {
  onSubmit: (data: CreateJobRequest) => Promise<void> | void;
};

export default function CreateJobForm({ onSubmit }: CreateJobFormProps) {

  const [type, setType] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    
    if (!type.trim()) return;
    try {
      setSubmitting(true);
      await onSubmit({ type: type.trim() });
      setType("");
    } catch (error) {
      console.error("Error creating job:", error);
    } finally {
      setSubmitting(false);
    }
  };
  return (
     <form
      onSubmit={handleSubmit}
      className="mb-6 flex flex-col gap-3 rounded-xl border border-slate-200 bg-slate-50 p-4 sm:flex-row sm:items-end"
    >
      <div className="flex-1">
        <label
          htmlFor="job-type"
          className="mb-2 block text-sm font-medium text-slate-700"
        >
          Job type
        </label>
        <input
          id="job-type"
          type="text"
          value={type}
          onChange={(e) => setType(e.target.value)}
          placeholder="e.g. XML"
          className="w-full rounded-lg border border-slate-300 bg-white px-3 py-2 text-sm text-slate-900 outline-none transition focus:border-slate-400 focus:ring-2 focus:ring-slate-200"
        />
      </div>

      <button
        type="submit"
        disabled={submitting || !type.trim()}
        className="rounded-lg bg-slate-900 px-4 py-2 text-sm font-medium text-white transition hover:bg-slate-800 disabled:cursor-not-allowed disabled:bg-slate-400"
      >
        {submitting ? "Creating..." : "Create Job"}
      </button>
    </form>
  )
}

