type StatusBadgeProps = {
  status: string;
};

export default function StatusBadge({ status }: StatusBadgeProps) {
  const baseClasses =
    "inline-flex rounded-full px-2.5 py-1 text-xs font-semibold";

  const statusClasses: Record<string, string> = {
    PENDING: "bg-amber-100 text-amber-800",
    RUNNING: "bg-blue-100 text-blue-800",
    COMPLETED: "bg-emerald-100 text-emerald-800",
    FAILED: "bg-red-100 text-red-800",
  };

  return (
    <span className={`${baseClasses} ${statusClasses[status] ?? "bg-slate-100 text-slate-700"}`}>
      {status}
    </span>
  );
}