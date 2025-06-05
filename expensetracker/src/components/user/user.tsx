import { Displayuser } from "../../service/user";
import "./user.css";

export function User() {
  const { userdetails, isopen, isModelOpen, isclosed, edituser, handleInputChange, handlesave } = Displayuser();
  return (
    <>
      {userdetails.length > 0 ? (
        <div id="cardContainer" className="card-container">
          {userdetails.map((user, index) => (
            <div className="card" key={index}>
              <h3>User Details</h3>
              <p><strong>User Name:</strong> {localStorage.getItem("username")}</p>
              <p><strong>Income:</strong> {user.income}</p>
              <button className="incomexpens" onClick={() => isModelOpen(user)}>Edit</button>
            </div>
           ))}
        </div>
      ) : (
        <p>Loading user details...</p>
      )}

      {isopen && edituser &&(
        <div id="editModal" className="modal">
          <div className="modal-content viewdiv">
            <span className="close" onClick={isclosed}>&times;</span>
            <h3>Edit User</h3>
            <form id="editForm" onSubmit={handlesave}>
              <label htmlFor="editUserId">User ID:</label>
              <input type="text" value={edituser.userId || ""} id="editUserId" readOnly />
              <label htmlFor="editIncome">Income:</label>
              <input type="number" id="editIncome" onChange={handleInputChange} required/>
              <button type="submit">Save</button>
            </form>
          </div>
        </div>
      )}
    </>
  );
}

