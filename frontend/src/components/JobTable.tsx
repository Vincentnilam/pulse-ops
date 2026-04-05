import type { Job } from "../types/job";
import StatusBadge from "./StatusBadge";

export default function JobTable({ jobs }: { jobs: Job[] }) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-slate-200 text-left">
        <thead className="bg-slate-50">
          <tr>
            <th className="px-4 py-3 text-xs font-semibold uppercase tracking-wide text-slate-500">
              ID
            </th>
            <th className="px-4 py-3 text-xs font-semibold uppercase tracking-wide text-slate-500">
              Type
            </th>
            <th className="px-4 py-3 text-xs font-semibold uppercase tracking-wide text-slate-500">
              Status
            </th>
            <th className="px-4 py-3 text-xs font-semibold uppercase tracking-wide text-slate-500">
              Created At
            </th>
          </tr>
        </thead>

        <tbody className="divide-y divide-slate-200 bg-white">
          {jobs.map((job) => (
            <tr key={job.id} className="hover:bg-slate-50">
              <td className="max-w-[180px] truncate px-4 py-3 font-mono text-xs text-slate-600">
                {job.id}
              </td>
              <td className="px-4 py-3 text-sm font-medium text-slate-900">
                {job.type}
              </td>
              <td className="px-4 py-3 text-sm text-slate-700">
                <StatusBadge status={job.status} />
              </td>
              <td className="px-4 py-3 text-sm text-slate-600">
                {new Date(job.createdAt).toLocaleString()}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}