import React from "react";
import "./addexpense.css";
import useAddExpense from "../../service/expenseservice";

 const Addexpense: React.FC = () =>{
  const {
    addexpenses,
    handleInputChange,
    expensedata,
    message,
    expensemessage,
    descriptionmessage }=useAddExpense()
    return (
    <>
    <div>
      <img src="" alt="" />
      <div className="card-container ">
        <div className="div1">
          <h1 className="expenseh1">Expenses and Categories</h1>
          <form id="expenseForm" onSubmit={addexpenses}>
            <div>
              <label htmlFor="categoryid">Enter a Category ID</label>
              <input type="number" name="categoryid" value={expensedata.categoryid} min="1" onChange={ handleInputChange} />
            </div>
            <div>
              <label htmlFor="Expensename">Enter an Expense Name</label>
              <input type="text" name="Expensename" value={expensedata.Expensename} onChange={ handleInputChange} />
              {expensemessage && <span id="message" >{expensemessage}</span>}
            </div>
            <div>
              <label htmlFor="description">Enter a Description</label>
              <input type="text" name="description" value={expensedata.description} onChange={ handleInputChange} />
            </div>
            {descriptionmessage && <span id="message" >{descriptionmessage}</span>}
            <div>
              <label htmlFor="date">Enter a Date</label>
              <input type="date" name="date" value={expensedata.date} onChange={handleInputChange} />
            </div>
            <div>
              <label htmlFor="amount">Enter an Amount</label>
              <input type="number" name="amount" min="1" value={expensedata.amount} onChange={ handleInputChange} />
            </div>
            <div>
              <button className="incomexpens" type="submit"> Add Expense </button>
            </div>
          </form>
          {message && <span id="message" >{message}</span>}
        </div>
      </div>
    </div>
    </>
  );
}
export default Addexpense;
