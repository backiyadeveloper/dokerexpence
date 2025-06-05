import { FetchReport } from "../../service/report"; 
import "./report.css"

export function Report() {
    const { data, loading, error } = FetchReport();
    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;
    return (
        <div className="viewreportdiv">
            <div className="viewreport" id="displaytable">
                <h1>Report</h1>
                <center>
                    <table id="reporttable">
                        <thead>
                            <tr>
                                <th>Category</th>
                                <th>Percentage</th>
                            </tr>
                        </thead>
                        <tbody>
                            {data && Object.entries(data).length > 0 ? (Object.entries(data).map(([category, percentage]) => (
                            <tr key={category}>
                                <td>{category}</td>
                                <td>{parseFloat(percentage).toFixed(2)}%</td> 
                            </tr>
                            ))) : (
                            <tr>
                                <td colSpan={2}>No data available</td>
                            </tr>
                            )}
                        </tbody>
                     </table>
                </center>
            </div>
        </div>
    );
}
