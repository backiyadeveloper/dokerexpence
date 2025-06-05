import "./addincome.css"
import {Income} from "../../service/incomeservice"
export function Addincome(){
    const {
        handleIncomeChange,
        addincomedtata,
        errormessage}=Income()
    return(
        <div>
            <img className="expenseimg" src="../assert/bitcoin-pile-top-dolar-bills.jpg" alt=""></img>
            <div className="expesnediv">
                <div className="div1">
                    <h1 className="expesneh1">Income</h1>
                    <form id="incomeForm">
                        <div>
                            <label htmlFor="income">Enter Income</label>
                            <input type="number" name="income"  onChange={handleIncomeChange} required></input>
                            {errormessage && <span className="error">{errormessage}</span>}
                        </div>
                        <div>
                            <button className="incomexpens1" onClick={addincomedtata} type="button">Submit</button>
                        </div>
                    </form>
                    <span id="message" className="spantag"></span>
                </div>
            </div>
        </div>
    )
}

