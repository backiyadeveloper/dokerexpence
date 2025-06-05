import "./home.css"
import {Getexpense} from "../../service/getexpenseservice"
export function Home(){
   const {
    expense,
    deletecategory,
    handleEditClick,
    isModalOpen,
    closeModal,
    editableExpense,
    handleSaveChanges,
    handleInputChange,
  message}=Getexpense()
    return(
      <>
      {expense.length>0?(
      <div className="viewdiv">
        <h1 id="username">{localStorage.getItem("username")} All Expenses</h1>
        <center>
            <table id="expenstable">
                <tr>
                    <th>category id</th>
                    <th>category name</th>
                    <th>cateory type</th>
                    <th>date</th>
                    <th>amount</th>
                    <th>Edit</th>
                    <th>delete</th>
                </tr>
                <tbody>
                    {
                      expense.map((expense)=>(
                            <tr id={`row-${expense.userId}-${expense.categoryId}`} key={`${expense.userId}-${expense.categoryId}`}>
                                <td>{expense.categoryId}</td>
                                <td>{expense.categoryName}</td>
                                <td>{expense.categoryType}</td>
                                <td>{expense.date}</td>
                                <td>{expense.amount}</td>
                                <td><button className="incomexpens1" onClick={()=>handleEditClick(expense)}>Edit</button></td>
                                <td><button className="incomexpens1" onClick={()=>deletecategory(expense.userId,expense.categoryId)}>Delete</button></td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </center>
      </div>):(<></>)}
     {isModalOpen && editableExpense && (
       <div id="editModal" className="modal">
        <div className="modal-content viewdiv">
          <span className="close" onClick={closeModal}>&times;</span>
          <h2>Edit Expense</h2>
          <form onSubmit={handleSaveChanges}>
              <label htmlFor="userId">User ID:</label>
              <input type="number" id="userId" value={editableExpense.userId} readOnly/>

              <label htmlFor="categoryId">Category ID:</label>
              <input type="number" id="categoryId" value={editableExpense.categoryId} readOnly />

              <label htmlFor="categoryName">Category Name:</label>
              <input type="text" id="categoryName" value={editableExpense.categoryName} onChange={handleInputChange}/>

              <label htmlFor="categoryType">Category Type:</label>
              <input type="text" id="categoryType" value={editableExpense.categoryType} onChange={handleInputChange}/>

              <label htmlFor="date">Date:</label>
              <input type="date" id="date" value={editableExpense.date} onChange={handleInputChange}/>

              <label htmlFor="amount">Amount:</label>
              <input type="number" id="amount" value={editableExpense.amount} onChange={handleInputChange}/>
              <button type="submit" className="incomexpens">Save Change</button>
              {message && <span id="message" >{message}</span>}
          </form>
        </div>
      </div>)}
     </>
    );
};