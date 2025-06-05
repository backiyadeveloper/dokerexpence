
import {ViewDate} from "../../service/viewbydate"
export const Daterange=()=>{
    const {   handleInputChange,viewbydate,data,message}=ViewDate()
    return(
        <div className="card-container ">
            <div className="v1div">
                <div className="viewdiv">
                    <h3>View By daterange</h3>
                    <form >
                        <label htmlFor="categorybx" >From date:</label>
                        <input type="date" name="startDate" onChange={handleInputChange} required></input>
                        <label htmlFor="categorybx">To date:</label>
                        <input type="date" name="endDate" onChange={handleInputChange} required></input>
                        <button className="incomexpens"  onClick={viewbydate}>Search</button>
                        {message && <span className="error">{message}</span>}
                    </form>
                   
                </div>
            </div>
            {data.length>0 ? <div className="viewdiv hidentable" id="displaytable">
                <h1>view by dates</h1>
                <center>
                    <table id="expenstable">
                        <thead>
                            <tr>
                                <th>date</th>
                                <th>amount</th>
                                <th>description</th>
                            </tr>
                        </thead>
                        <tbody id="tbody">
                        {data.map(data=>(
                            <tr>
                                <td>{data.date}</td>
                                <td>{data.amount}</td>
                                <td>{data.description}</td>
                            </tr>
                          ))
                       }
                      </tbody>
                    </table>
             </center>
        </div>: <></>}</div>
    )
}

